package com.cherryfish.stephanie.myfavoroutepics;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class ListAdapter extends RecyclerView.Adapter {

    public MainActivity activity;
    public List<String> captions;
    public List<Bitmap> photos;


    ListAdapter(MainActivity activity, List<Bitmap> photos, List<String> captions){
        this.activity=activity;
        this.photos=photos;
        this.captions=captions;
    }
    //create the listview item
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.selfie_item, parent, false);
        return new ImageViewHolder(itemView);

    }

    //display image and caption to view
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rHolder, int position) {
        final int pos = position;
        ImageViewHolder holder = (ImageViewHolder) rHolder;
        Bitmap original= photos.get(position);

        holder.selfie.setImageBitmap(original);
        holder.selfie.setAdjustViewBounds(true);
        holder.selfie.setScaleType(ImageView.ScaleType.FIT_CENTER);
        holder.caption.setText(captions.get(position));

    }

    //return number of items in list
    @Override
    public int getItemCount() {
        if (photos!=null){
            System.out.println(photos.size());
            return photos.size();
        }
        else return 0;
    }
}

