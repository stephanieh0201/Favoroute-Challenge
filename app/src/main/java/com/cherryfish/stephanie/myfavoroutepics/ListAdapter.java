package com.cherryfish.stephanie.myfavoroutepics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class ListAdapter extends RecyclerView.Adapter {

    public MainActivity activity;
    public List<String> captions;
    public List<String> urls;
    Bitmap image;


    ListAdapter(MainActivity activity, List<String> urls, List<String> captions){
        this.activity=activity;
        this.urls=urls;
        this.captions=captions;
    }

    //create the listview item
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.selfie_item, parent, false);
        return new ImageViewHolder(itemView);

    }

    //display each image and caption to view
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rHolder, int position) {
        final int pos = position;
        final RecyclerView.ViewHolder r = rHolder;
        final String original= urls.get(position);
        //create thread to grab image from URL
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try{
                    URL url = new URL(original);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    int nh = (int) ( bitmap.getHeight() * (300.0 / bitmap.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 300, nh, true);
                    image=scaled;
            } catch (IOException e) {
                System.out.println(e);
            }
            }

        });
        thread.start();
        try {
            //wait until thread finishes loading image
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ImageViewHolder holder = (ImageViewHolder) r;

        //set url image to image view and adjust settings
        holder.selfie.setImageBitmap(image);
        holder.selfie.setAdjustViewBounds(true);
        holder.selfie.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //set caption text
        holder.caption.setText(captions.get(position));
    }

    //return number of items in list
    @Override
    public int getItemCount() {
        if (urls!=null){
            return urls.size();
        }
        else return 0;
    }
}

