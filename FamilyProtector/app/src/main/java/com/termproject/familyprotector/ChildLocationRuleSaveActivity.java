package com.termproject.familyprotector;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

public class ChildLocationRuleSaveActivity extends AppCompatActivity implements View.OnClickListener{

    EditText locationNameEditText;
    TimePicker ruleLocationFromTime, ruleLocationToTime;
    Button saveButton;
    UserLocalStore userLocalStore;
    double latitude,longitude;
    String locationNameStr,addressString,childName,username;
    float locationPerimeterValue;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_location_rule_save);
        locationNameEditText = (EditText)findViewById(R.id.edit_location_name);

        ruleLocationFromTime = (TimePicker)findViewById(R.id.location_timepicker_from);
        ruleLocationToTime = (TimePicker)findViewById(R.id.location_timepicker_to);
        saveButton = (Button)findViewById(R.id.rule_location_save_button);
        userLocalStore = new UserLocalStore(this);

        saveButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        String strStartTime = ruleLocationFromTime.getCurrentHour() + ":" + ruleLocationFromTime.getCurrentMinute();
        String strEndTime = ruleLocationFromTime.getCurrentHour() + ":" + ruleLocationFromTime.getCurrentMinute();
        saveRuleLocationToParse();
        startActivity(new Intent(this,ParentHomeScreen.class));
    }

    private void saveRuleLocationToParse (){

        latitude =userLocalStore.getLocationLatitude();
        longitude = userLocalStore.getLocationLongitude();
        locationNameStr = locationNameEditText.getText().toString();
        if(locationNameStr.matches("")){
            locationNameStr = "Rule Location";
        }
        addressString = userLocalStore.getLocationAddress();
        locationPerimeterValue = userLocalStore.getLocationPerimeter();
        childName = userLocalStore.getChildDetails();
        user = userLocalStore.getLoggedInUser();



        ParseObject ruleLocation = new ParseObject("ChildRuleLocation");
        ParseGeoPoint ruleLatLng = new ParseGeoPoint(latitude,longitude);
        ruleLocation.put("userName",user.getUsername());
        ruleLocation.put("childName",childName);
        ruleLocation.put("locationName",locationNameStr);
        ruleLocation.put("locationAddress", addressString);
        ruleLocation.put("geopoint", ruleLatLng);
        ruleLocation.put("locationRadius", locationPerimeterValue);
        ruleLocation.saveInBackground();

    }

}
