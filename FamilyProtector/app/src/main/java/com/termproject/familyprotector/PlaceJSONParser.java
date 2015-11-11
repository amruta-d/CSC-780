package com.termproject.familyprotector;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mehul on 10/22/2015.
 */
public class PlaceJSONParser {

    public List<HashMap<String,String>> parseJSONObject(JSONObject jsonObject){
        JSONArray jsonPlaces = null;
        try{

            jsonPlaces = jsonObject.getJSONArray("predictions");


        }
        catch(Exception e){

            Log.e("PlaceJSONParser",e.toString());

        }
        return getPlaces(jsonPlaces);
    }
    private List<HashMap<String,String>> getPlaces(JSONArray jsonPlaces){
        int placesCount = jsonPlaces.length();
        List<HashMap<String,String>> placesList = new ArrayList<HashMap<String, String>>();
        HashMap<String,String> place =null;

        for (int i=0; i<placesCount;i++){
            try{
                place = getPlace((JSONObject)jsonPlaces.get(i));
                placesList.add(place);

            }
            catch (Exception e){
                Log.e("PlaceJSONParser",e.toString());

            }
        }
        return placesList;

    }


    private HashMap<String,String> getPlace(JSONObject jsonPlace){

        HashMap<String,String> place = new HashMap<String,String>();

        String id = "";
        String reference = "";
        String description = "";

        try{

            description = jsonPlace.getString("description");
            id = jsonPlace.getString("id");
            reference = jsonPlace.getString("reference");

            place.put("description",description);
            place.put("_id",id);
            place.put("reference",reference);


        }
        catch (Exception e){

            Log.e("PlaceJSONParser",e.toString());

        }

        return place;

    }
}
