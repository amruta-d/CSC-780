package com.termproject.familyprotector;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GeofenceTransitionsIntentService extends IntentService {
    protected static final String TAG = "GeofenceTransitionsIS";
    UserLocalStore userLocalStore;
    User storedUser;
    String userName, childNameStr, sysTime, geofenceIdToPutInArrayList;
    ArrayList<String> triggeringGeofencesNamesList;
    ArrayList<String> triggeringGeofenceIdList;
    Calendar c;
    GeofenceAlertObj geofenceAlertObj;


    public GeofenceTransitionsIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        Context context = getApplicationContext();
        userLocalStore = new UserLocalStore(context);
        storedUser = userLocalStore.getLoggedInUser();
        userName = storedUser.getUsername();
        childNameStr = userLocalStore.getChildForThisPhone();
        triggeringGeofencesNamesList = new ArrayList<String>();
        triggeringGeofenceIdList = new ArrayList<String>();
        super.onCreate();
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        Log.v("Intent", "inside intent");

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            getGeofenceTransitionDetails(this,geofenceTransition,triggeringGeofences);

//            String geofenceTransitionDetails = getGeofenceTransitionDetails(
//                    this,
//                    geofenceTransition,
//                    triggeringGeofences
//            );
//
//            // Send notification and log the transition details.
//            Log.v("Intent", geofenceTransitionDetails);
//            saveAlertToParse(geofenceTransitionDetails);
//
//            sendParsePush(geofenceTransitionDetails);
////            sendNotification(geofenceTransitionDetails);
//            Log.i(TAG, geofenceTransitionDetails);
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type, geofenceTransition));
        }
    }

    private void getGeofenceTransitionDetails(Context context, int geofenceTransition, List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
//        ArrayList<String> triggeringGeofencesNamesList = new ArrayList<String>();
        for (Geofence geofence : triggeringGeofences) {
            String id = geofence.getRequestId();
            Log.v("id",id);
            performParseCheck(id, geofenceTransitionString);
//            triggeringGeofencesNamesList.add(geofence.getRequestId());
        }
//        String triggeringGeofencesNamesString = TextUtils.join(", ", triggeringGeofencesNamesList);
//        return geofenceTransitionString + " " + triggeringGeofencesNamesString;
    }



    /**
     * Posts a notification in the notification bar when a transition is detected.
     * If the user clicks the notification, control goes to the MainActivity.
     */
    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), GeofencesActivity.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(GeofencesActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_icon_plus)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_icon_plus))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText(getString(R.string.geofence_transition_notification_text))
                .setContentIntent(notificationPendingIntent);

        builder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        mNotificationManager.notify(0, builder.build());

    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }


    private void saveAlertToParse(String geofenceTransitionDetails, String geofenceTransitionId) {

        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofenceIdList);



        ParseObject childAlerts = new ParseObject("ChildAlerts");
        childAlerts.put("userName", userName);
        childAlerts.put("childName", childNameStr);
        childAlerts.put("alert", geofenceTransitionDetails);
        childAlerts.put("ruleIdStr", geofenceTransitionId);
        childAlerts.saveInBackground();
//        Log.v("Intent", "saved to parse");
    }

    private void sendParsePush(String geofenceTransitionDetails) {
        // Find devices associated with these users
        ParseQuery pushQuery = ParseInstallation.getQuery();
//        Log.v("Intent", "parent:" + userName);
        pushQuery.whereEqualTo("email", "parent:" + userName);

        // Send push notification to query
        ParsePush push = new ParsePush();
        push.setQuery(pushQuery); // Set our Installation query

        push.setMessage(childNameStr +" "+ geofenceTransitionDetails + " at " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE));
        push.sendInBackground();

        Log.v("Intent", "completed push");
    }


    private void performParseCheck(final String geofenceId, final String geofenceTransitionString){
        //convert geofenceId to int
        int geofenceIntId = Integer.parseInt(geofenceId);

        c = Calendar.getInstance();
        int hours = (c.get(Calendar.HOUR_OF_DAY))*100;
        int minutes = c.get(Calendar.MINUTE);
        final int timeToCheck = hours+minutes;
        Log.v("timetocheck",timeToCheck+"");
         final int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Log.v("day",dayOfWeek+"");
//        final Boolean addGeofence ;//= new Boolean(false);

//        int fromHour,toHour,fromMinute,toMinute;
//        if(minutes!=0) {
//             sysTime = hours + ":"+ minutes;
//        }
//        else {
//            sysTime = hours + ":" + "00";
//
//        }

//        Log.v("time", "hours:"+hours+"---min:"+minutes);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("ChildRuleLocation");
        query.whereEqualTo("userName", userName);
//        Log.v("userName", "name" + user.getUsername());
        query.whereEqualTo("childName", childNameStr);
        query.whereEqualTo("ruleLocationId", geofenceIntId);
//        Log.v("childName", "name" + childName);

        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            boolean addgeofence = false;
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null){
                    Log.v("inside done","in done");

                    String fromTime = parseObject.getString("ruleFromTime");
                    String[] fromTimeArr = fromTime.split(":");
                    int fromHour = Integer.parseInt(fromTimeArr[0]) * 100;
                    int fromMinute = Integer.parseInt(fromTimeArr[1]);
                    int fromTimeInt = fromHour+fromMinute;
                    Log.v("from time",fromTimeInt+"");

                    String toTime = parseObject.getString("ruleToTime");
                    String[] toTimeArr = toTime.split(":");
                    int toHour = Integer.parseInt(toTimeArr[0])*100;
                    int toMinute = Integer.parseInt(toTimeArr[1]);
                    int toTimeInt = toHour+toMinute;
                    Log.v("to time",toTimeInt+"");
                    String dayRule = "";
                    switch(dayOfWeek){
                        case 1:
                            dayRule = parseObject.getString("Sunday");
                            break;
                        case 2:
                            dayRule = parseObject.getString("Monday");
                            break;
                        case 3:
                            dayRule = parseObject.getString("Tuesday");
                            break;
                        case 4:
                            dayRule = parseObject.getString("Wednesday");
                            break;
                        case 5:
                            dayRule = parseObject.getString("Thursday");
                            break;
                        case 6:
                            dayRule = parseObject.getString("Friday");
                            break;
                        case 7:
                            dayRule = parseObject.getString("Saturday");
                            break;
                    }

                    if(dayRule!=null) {
                        Log.v("day", dayRule);

                        if (timeToCheck > fromTimeInt && timeToCheck < toTimeInt && dayRule.matches("Yes")) {
//                        addGeofence = Boolean.FALSE;
//                        Log.v("location",parseObject.getString("locationName"));
//                        Log.v("location",parseObject.getString("ruleLocationId"));
//                        triggeringGeofencesNamesList.add(parseObject.getString("locationName"));
//                        triggeringGeofenceIdList.add(Integer.toString(parseObject.getInt("ruleLocationId")));
                            String triggeringGeofenceName = parseObject.getString("locationName");
                            String triggeringGeofenceId = Integer.toString(parseObject.getInt("ruleLocationId"));
                            Log.v("name+id",triggeringGeofenceName+triggeringGeofenceId);
                            geofenceAlertObj = new GeofenceAlertObj(triggeringGeofenceName,
                                    triggeringGeofenceId, geofenceTransitionString);

                            Log.v("stringAlert",geofenceAlertObj.alertString());
                            Log.v("idAlert", geofenceAlertObj.alertIdString());

                            saveAlertToParse(geofenceAlertObj.alertString(), geofenceAlertObj.alertIdString());
                            sendParsePush(geofenceAlertObj.alertString());


                        }

                    }

//                    return addgeofence;


                }
                else {
                    Log.d("parse check", "error in fetching");
                }

            }

        });




    }
}
