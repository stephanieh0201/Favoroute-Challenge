package com.cherryfish.stephanie.myfavoroutepics;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class PhotoSearch extends AsyncTask<Void, Void, Boolean> {
    String apiURL= "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    String apiKey="0f6f6c131f8eb464ded3ac9ada60bc00";
    @Override
//    https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=YOURAPIKEY&format=json&nojsoncallback=1&text=cats&extras=url_o
    protected Boolean doInBackground(Void... params) {

            //Create JSON object for post
            String url = apiURL + "&api_key=" + apiKey+"&format=json&nojsoncallback=1&text=" + "thailand";
            JSONObject results = postRequest(url);
//            String message = (String)jsnobject.get("Message");
            System.out.println(results.toString());
            return null;


    }


    public JSONObject postRequest(String url) {


        URL route = null;
        HttpURLConnection client = null;

        try {
            //Create URL route
            route = new URL(url);


            //open connection and ready it for post request
            client = (HttpURLConnection) route.openConnection();
            client.setRequestMethod("GET");
            client.connect();
            //receive results
            int HttpResult = client.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            System.out.println("" + sb.toString());
            JSONObject jsnobject = new JSONObject(sb.toString());
            return jsnobject;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
