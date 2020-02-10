package com.example.smartshoppingapp.MapsClasses;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearByPlacesData extends AsyncTask<Object, String, String> {

        String googlePlacesdata;
        String url;
        GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {

        mMap = (GoogleMap) objects[0] ;
        url = (String)objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();

        try {
            googlePlacesdata = downloadUrl.readUrl(url);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return googlePlacesdata;
    }
    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parse(s);
        showNearbyPlaces(nearbyPlaceList);
    }


    private void showNearbyPlaces(List<HashMap<String, String>> nearbyplaceslist)
    {
        for (int i = 0 ; i < nearbyplaceslist.size() ; i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyplaceslist.get(i);

            String placename = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("latitude"));
            double lng = Double.parseDouble(googlePlace.get("longitude"));
            LatLng latLng = new LatLng(lat , lng);
            markerOptions.position(latLng);
            markerOptions.title(placename);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        }

    }

}