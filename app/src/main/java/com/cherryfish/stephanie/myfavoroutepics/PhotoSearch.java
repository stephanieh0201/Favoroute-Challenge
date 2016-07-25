package com.cherryfish.stephanie.myfavoroutepics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class PhotoSearch extends AsyncTask<String, Void, Boolean> {
    String apiURL= "https://api.flickr.com/services/rest/?method=flickr.photos.search";
    String apiKey="0f6f6c131f8eb464ded3ac9ada60bc00";

    List<String> captions = new ArrayList<String>();
    List<Bitmap>photoBitmaps = new ArrayList<Bitmap>();
    TravelList fragment;

    PhotoSearch(TravelList fragment){
        this.fragment=fragment;
    }

    @Override
    protected Boolean doInBackground(String... params) {

            //Create JSON object for post
        try {
            String url = apiURL + "&api_key=" + apiKey+"&format=json&nojsoncallback=1&text=" + params[0]+ "&extras=url_n";
            JSONObject results = postRequest(url);
            System.out.println(results.toString());

            JSONObject object = (JSONObject) results.get("photos");
            JSONArray photos = (JSONArray) object.get("photo");
            System.out.println("size is " + photos.length());

            for (int i=0;i<10;i++) {
                System.out.println("i is "+ i);
                JSONObject photo = (JSONObject) photos.get(i);
                String image = (String) photo.get("url_n");
                photoBitmaps.add(i, urlStringToBitmap(image));
                captions.add(i, (String) photo.get("title"));
//                System.out.println(captions[i]);

            }
            return true;
            } catch (JSONException e) {
            e.printStackTrace();
        }
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


    public static Bitmap urlStringToBitmap(String s){
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(s).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }
    public void onPostExecute(Boolean result){

        fragment.displayData(photoBitmaps, captions);
    }
}
