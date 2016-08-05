package com.cherryfish.stephanie.myfavoroutepics;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;
import java.io.File;


import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class ThumborPost extends AsyncTask<File, Void, Boolean> {
    String hostUrl = "http://188.226.195.167:8889/image/";
  //  String apiKey="0f6f6c131f8eb464ded3ac9ada60bc00";
//    List<String> captions = new ArrayList<String>();
//    List<Bitmap>photoBitmaps = new ArrayList<Bitmap>();
//    TravelList fragment;

    AddSelfieDialog fragment;

    ThumborPost(AddSelfieDialog fragment){
        this.fragment=fragment;
          }

    @Override
    protected Boolean doInBackground(File... params) {
        URL route = null;
        HttpURLConnection client = null;
        HttpPostHC4 request = new HttpPostHC4(hostUrl);
        System.out.println("httpposthc4 opened");
        String BOUNDRY = "----------------------------";

            try {

                String contentDisposition = "Content-Disposition: form-data; media=\"" + params[0].getName() + "\"";
                String contentType = "Content-Type: image/jpeg";

                // multipart request
                StringBuffer requestBody = new StringBuffer();
                requestBody.append("--");
                requestBody.append(BOUNDRY);
                requestBody.append('\n');
                requestBody.append(contentDisposition);
                requestBody.append('\n');
                requestBody.append(contentType);
                requestBody.append('\n');
                requestBody.append('\n');
                requestBody.append(params[0]);
                requestBody.append("--");
                requestBody.append(BOUNDRY);
                requestBody.append("--");


                //Create URL route
                route = new URL(hostUrl);
                System.out.println("route is new URL");
                //open connection and ready it for post request
                client = (HttpURLConnection) route.openConnection();
                client.setRequestMethod("POST");
                client.setDoOutput(true);
                client.setDoInput(true);
                client.setUseCaches(false);
                client.setRequestProperty("Content-Type", "multipart/form-data; boundary="+BOUNDRY);
                System.out.println("connection opened");

                // Send the body
                DataOutputStream dataOS = new DataOutputStream(client.getOutputStream());
                dataOS.writeBytes(requestBody.toString());
                dataOS.flush();
                dataOS.close();

                //receive results
                StringBuilder sb = new StringBuilder();

                int HttpResult = client.getResponseCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                System.out.println("" + sb.toString());

                return true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



    public static Bitmap urlStringToBitmap(String s){
        Bitmap image = null;
        try {
            InputStream in = new URL(s).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }
    public void onPostExecute(String url){
        fragment.update(url);
//        fragment.displayData(photoBitmaps, captions);
    }
}
