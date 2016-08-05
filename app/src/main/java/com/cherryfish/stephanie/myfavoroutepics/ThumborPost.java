package com.cherryfish.stephanie.myfavoroutepics;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;
import java.io.File;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class ThumborPost extends AsyncTask<File, Void, Boolean> {
    String hostUrl = "http://188.226.195.167:8889";
    String url;
    AddSelfieDialog fragment;

    ThumborPost(AddSelfieDialog fragment){
        this.fragment=fragment;
          }

    @Override
    protected Boolean doInBackground(File... params) {
        URL route = null;
//        HttpURLConnection client = null;
            try {
                //create new HTTP Client
                OkHttpClient client = new OkHttpClient();

                //set up multiform media file
                MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpeg");
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("media", "image.jpg",
                                RequestBody.create(MEDIA_TYPE_JPG, params[0]))
                        .build();

                //set up POST request with headers
                Request request = new Request.Builder()
                        .url("http://188.226.195.167:8889/image")
                        .post(requestBody)
                        .addHeader("Content-Type", "image/jpeg")
                        .addHeader("Slug", "photo.jpg")
                        .build();
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);

                }
                else{
                    //grab returned image location and return true to postexecute
                    String results = response.header("Location");
                    url= "http://188.226.195.167:8889" +results;
                    return true;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }


    public void onPostExecute(Boolean result){
        if (result)fragment.update(url);

    }
}
