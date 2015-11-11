package com.termproject.familyprotector;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mehul on 10/22/2015.
 */
public class PlaceDetailsJSONParser {

    public List<HashMap<String,String>> parseJSONObject(JSONObject jsonObject){
        Double latitude = Double.valueOf(0);
        Double longitude = Double.valueOf(0);
        String formattedAddress = "";

        HashMap<String, String> placeHashMap = new HashMap<String, String>();
        List<HashMap<String, String>> listOfPlaces = new ArrayList<HashMap<String,String>>();

        try{

            latitude = (Double)jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").get("lat");
            longitude = (Double)jsonObject.getJSONObject("result").getJSONObject("geometry").getJSONObject("location").get("lng");
            formattedAddress = (String)jsonObject.getJSONObject("result").get("formatted_address");

        }
        catch (Exception e){
            Log.e("PlaceDetailsJSONParser", e.toString());
        }
        placeHashMap.put("lat",Double.toString(latitude));
        placeHashMap.put("lng",Double.toString(longitude));
        placeHashMap.put("formatted_address",formattedAddress);

        listOfPlaces.add(placeHashMap);

        return listOfPlaces;
    }
}
