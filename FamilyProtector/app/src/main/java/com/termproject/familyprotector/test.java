package com.termproject.familyprotector;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Mehul on 11/14/2015.
 */
public class test {
    GoogleMap mMap;
    Circle circle;
    private double latitude,longitude;

    public void testFunct() {

        circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(100)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));
    }
}
