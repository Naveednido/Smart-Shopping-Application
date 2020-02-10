package com.example.smartshoppingapp.MapsClasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {


    private HashMap<String, String> getplaces(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googleplacesMap = new HashMap<>();
        String placename = "-NA-";
        String vicinity = "=NA-";
        String latitude = "" ;
        String longitude = "";
        String reference = "";


            try {
                if (!googlePlaceJson.isNull("name"))
                {
                    placename = googlePlaceJson.getString("name");
                }
                if (!googlePlaceJson.isNull("vicinity"))
                {
                    vicinity = googlePlaceJson.getString("vicinity");
                }
                latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

                reference = googlePlaceJson.getString("reference");

                googleplacesMap.put("place_name" , placename);
                googleplacesMap.put("vicinity" , vicinity);
                googleplacesMap.put("latitude" , latitude);
                googleplacesMap.put("longitude" , longitude);
                googleplacesMap.put("reference" , reference);



            } catch (JSONException e) {
                e.printStackTrace();
                 }

            return googleplacesMap;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placeslist = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for (int i=0 ; i < count ; i++)
        {
            try {
                placeMap = getplaces((JSONObject) jsonArray.get(i));
                placeslist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placeslist;

    }

    public List<HashMap<String, String>> parse (String jsondata)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject ;

        try {
            jsonObject = new JSONObject(jsondata);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }

}
