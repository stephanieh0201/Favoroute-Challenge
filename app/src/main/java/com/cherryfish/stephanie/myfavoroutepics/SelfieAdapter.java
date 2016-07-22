package com.cherryfish.stephanie.myfavoroutepics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class SelfieAdapter extends RecyclerView.Adapter {
    public String[] charityName;
    public Bitmap[] banner;
    public Bitmap[] prof;
    public Integer[] entityID;
    public Integer[] entityTypeID;
    public MainActivity activity;

    //    private final Integer[]banner2;
    SelfieAdapter(MainActivity activity){
        this.activity=activity;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.selfie_item, parent, false);
        return new SelfieViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rHolder, int position) {
        final int pos = position;
        SelfieViewHolder holder = (SelfieViewHolder) rHolder;
        Bitmap original = BitmapFactory.decodeResource(activity.getResources(), R.drawable.beach);
        int width = original.getWidth();
        int height = original.getHeight();
        int maxSize=400;
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize ;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }


        Bitmap b = Bitmap.createScaledBitmap(original, width, height, true);
        holder.selfie.setImageBitmap(b);
        holder.selfie.setAdjustViewBounds(true);
        holder.selfie.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.caption.setText("Testing captions");

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}

