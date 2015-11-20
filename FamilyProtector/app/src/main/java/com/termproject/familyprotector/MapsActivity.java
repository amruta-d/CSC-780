package com.termproject.familyprotector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements View.OnClickListener,GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    GPSTracker gps;
    double latitude, longitude;
    Marker marker;
    Circle circle;
    Paint mPaint;
    HttpURLConnection urlConnection = null;
    FloatingActionButton floatingActionButton;
    String addressString = "";
    String locationNameStr;
    float locationPerimeterValue;
    EditText locationNameEditText, locationPerimeterEditText;
    String queryParam;
    SeekBar geofenceRadiusSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        geofenceRadiusSeekBar = (SeekBar)findViewById(R.id.geofence_radius_bar);
        floatingActionButton.setOnClickListener(MapsActivity.this);



        gps = new GPSTracker(this);
        if (gps.canGetLocationCheck()) {
            latitude = gps.getLatitudeVal();
            longitude = gps.getLongitudeVal();

            queryParam = Double.toString(latitude) + "," + Double.toString(longitude);

            Log.v("maps", "Lat: " + latitude + "\n" + longitude);


        } else {
            queryParam = "Thornton+Hall,+San+Francisco+State+University,+1600+Holloway+Ave,+San+Francisco,+CA+94132";
        }

        setUpMapIfNeeded();

        MapSearchTask mapSearchTask = new MapSearchTask();
        mapSearchTask.execute(queryParam);

        mMap.setOnMapLongClickListener(this);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.floating_action_button) {
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View perimeterSaver = layoutInflater.inflate(R.layout.dialog_location_save,null);
            AlertDialog.Builder dialogPerimeter = new AlertDialog.Builder(this);
            dialogPerimeter.setTitle(R.string.dialog_title);
            dialogPerimeter.setView(perimeterSaver);
            TextView locationAddress = (TextView) perimeterSaver.findViewById(R.id.text_address);
            locationNameEditText = (EditText) perimeterSaver.findViewById(R.id.edit_location_name);
            locationPerimeterEditText = (EditText) perimeterSaver.findViewById(R.id.edit_location_perimeter);
            locationAddress.setText(addressString);
            dialogPerimeter
                    .setCancelable(false)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            locationNameStr = locationNameEditText.getText().toString();
                            String locationPerimeterStr = locationPerimeterEditText.getText().toString();
                            if(locationNameStr.matches("")){
                                locationNameStr = "Rule Location";
                            }
                            if(locationPerimeterStr.matches("")){
                                locationPerimeterValue = 60.0f;
                            }
                            else{
                                locationPerimeterValue = Float.valueOf(locationPerimeterStr);
                            }

                            saveRuleLocation();
                            createGeofenceCirlce();
                            geofenceRadiusSeekBar.setVisibility(View.VISIBLE);
                            String toastStr = "Location with perimeter";
                            Toast.makeText(getApplicationContext(), toastStr, Toast.LENGTH_LONG).show();

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
            AlertDialog alertDialog = dialogPerimeter.create();
            alertDialog.show();
        }
    }
    

    @Override
    public void onMapLongClick(LatLng point){
        marker.remove();
        if(circle!=null){
            circle.remove();
        }
        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title("New Location"));
        marker.showInfoWindow();
        MapSearchTask mapSearchTask = new MapSearchTask();
        queryParam = Double.toString(point.latitude) + "," + Double.toString(point.longitude);
        mapSearchTask.execute(queryParam);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_maps_activity, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();


//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        MenuItem searchItem = menu.findItem(R.id.action_search);
        //      SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        //   MenuItem searchItem = menu.findItem(R.id.action_search);
        //     SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                Log.v("LISTENER", "onQueryTextChange called - " + newText);
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                //textview.setText(query);
                //opensearch();
                Log.v("LISTENER", "OnQuerySubmit called - " + query);
                hideSoftKeyboard(MapsActivity.this);
                MapSearchTask mapSearchTask = new MapSearchTask();
                mapSearchTask.execute(query);

                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void saveRuleLocation (){

        ParseObject ruleLocation = new ParseObject("ChildRuleLocation");
        ParseGeoPoint ruleLatLng = new ParseGeoPoint(latitude,longitude);
        ruleLocation.put("locationName",locationNameStr);
        ruleLocation.put("locationAddress", addressString);
        ruleLocation.put("geopoint", ruleLatLng);
        ruleLocation.put("locationRadius", locationPerimeterValue);
        ruleLocation.saveInBackground();



    }

    public void createGeofenceCirlce(){
        Log.v("1.latitude and lon","Latitude: "+Double.toString(latitude)+"Long: "+ Double.toString(longitude));
        if(circle!=null){
            circle.remove();
        }

        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(locationPerimeterValue)
                .strokeColor(Color.parseColor("#0084d3"))
                .fillColor(Color.parseColor("#500084d3")));

        if(mMap.getCameraPosition().zoom ==16.9f)
        {
            mMap.moveCamera(CameraUpdateFactory.zoomBy(2.5f));

        }



    }

    public class MapSearchTask extends AsyncTask<String, Void, HashMap<String, String>> {


        @Override
        protected HashMap<String, String> doInBackground(String... params) {
            HashMap<String, String> location = new HashMap<String, String>();
            Log.v("LISTENER", "doInBackground called - " + "");
            String apiKey = "AIzaSyAUSETHO5_4d_lGrGfjX4vAowf6DrqaNmk";
            try {
//                URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=san+francisco&key=AIzaSyBTA9R3hjm618utMuwOkN1FYM_ykKE1Wo8");

//                URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=san+francisco+tourist+spots&types=establishment&natural_feature&key=AIzaSyAUSETHO5_4d_lGrGfjX4vAowf6DrqaNmk");
                final String GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";
                final String QUERY_PARAM = "query";
                final String APIKEY_PARAM = "key";

                Uri builtUri = Uri.parse(GOOGLE_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, params[0])
                        .appendQueryParameter(APIKEY_PARAM, apiKey)
                        .build();

                URL url = new URL(builtUri.toString());
//                URL url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=Thornton+Hall,+San+Francisco+State+University,+1600+Holloway+Ave,+San+Francisco,+CA+94132&key=AIzaSyAUSETHO5_4d_lGrGfjX4vAowf6DrqaNmk");

                // Create the request to Google Palces, and open the connection

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Log.v("maps", "i am here!!");

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray resultsArr = jsonObject.getJSONArray("results");
                location.put("addressStr", resultsArr.getJSONObject(0).getString("formatted_address"));
                JSONObject locationObject = resultsArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
//                location.put("lat", Double.parseDouble(locationObject.getString("lat")));
//                location.put("lng",Double.parseDouble(locationObject.getString("lng")));
                location.put("lat", locationObject.getString("lat"));
                location.put("lng", locationObject.getString("lng"));
//                location.put("addressStr", "hello");
//                location.put("lat", "37.69743690000001");
//                location.put("lng", "122.4802931");
                Log.v("MAPS - LNG", locationObject.getString("lat"));
                Log.v("MAPS - LNG", locationObject.getString("lng"));
                Log.v("MAPS - LNG", resultsArr.getJSONObject(0).getString("formatted_address"));


//                Log.v("MAPS", buffer.toString());

            } catch (Exception e) {
                Log.v("ERROR", e.toString());

            }


            return location;

        }

        protected void onPostExecute(HashMap<String, String> locationMap) {
            String markerTitle = locationMap.get("addressStr");
            addressString = locationMap.get("addressStr");
            latitude = Double.parseDouble(locationMap.get("lat"));
            longitude = Double.parseDouble(locationMap.get("lng"));
            marker.remove();


            setUpMap(latitude, longitude, markerTitle);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap(double latudude, double longitude, String titleStr)} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            //  handleIntent(getIntent());
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap(latitude, longitude, "Current Location");
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap(double latitude, double longitude, String titleStr) {

        marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(titleStr));
        marker.showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16.9f));

        if(circle!=null){
            createGeofenceCirlce();
        }


        //   handleIntent(getIntent());
    }
}
