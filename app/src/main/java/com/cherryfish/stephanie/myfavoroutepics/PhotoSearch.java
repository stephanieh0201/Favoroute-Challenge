package com.cherryfish.stephanie.myfavoroutepics;


import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    List<String> urls = new ArrayList<String>();
    TravelSearchDialog fragment;

    PhotoSearch(TravelSearchDialog fragment){
        this.fragment=fragment;
    }

    @Override
    protected Boolean doInBackground(String... params) {


        try {
            //set up url to send requests
            String url = apiURL + "&api_key=" + apiKey+"&format=json&nojsoncallback=1&text=" + params[0]+ "&extras=url_n";
            JSONObject results = postRequest(url);

            //create JSON object to send to post
            JSONObject object = (JSONObject) results.get("photos");
            JSONArray photos = (JSONArray) object.get("photo");
            int size=10;
            if (photos==null || photos.length()==0){
                return false;
            }
            if (photos.length()<10){
                size=photos.length();
            }
            for (int i=0;i<10;i++) {
                JSONObject photo = (JSONObject) photos.get(i);
                String image = (String) photo.get("url_n");
                urls.add(i, image);
                captions.add(i, (String) photo.get("title"));

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


    public void onPostExecute(Boolean result){
        if (result) fragment.displayData(urls, captions);
        else fragment.displayError();
    }
}
