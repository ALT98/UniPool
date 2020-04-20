package com.example.unipool;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetDirectionsData extends AsyncTask<Object, String, String> {

    GoogleMap mMap;
    String url;
    LatLng startLatLng, endLatLng;

    HttpURLConnection httpURLConnection = null;
    String data = "";
    InputStream inputStream = null;
    Context c;

    GetDirectionsData(Context c){
        this.c = c;
    }

    @Override
    protected String doInBackground(Object... params) {
        mMap = (GoogleMap)params[0];
        url = (String)params[1];
        startLatLng = (LatLng)params[2];
        endLatLng = (LatLng)params[3];

        try {
            URL myurl = new URL(url);
            httpURLConnection = (HttpURLConnection)myurl.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String line="";
            while((line = bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data = sb.toString();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /*protected void onPostExcecute(String s){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0)
                .getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        int count = jsonArray.length();
        String[] polyline_array = new String[count];
        JSONObject jsonObject2;
        for (int i = 0; i < count; i++) {
            jsonObject2 = jsonArray.getJSONObject(i);
            String polygone = jsonObject2.getJSONObject("polyline").getString("points");
            polyline_array[i] = polygone;
        }
        int count2 = polyline_array.length;
    }*/

}
