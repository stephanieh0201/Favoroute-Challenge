package com.cherryfish.stephanie.myfavoroutepics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView selfie;
    public TextView caption;
    public View item;

    // view holder for recycler list items
    public ImageViewHolder(View itemView) {
        super(itemView);
        item=itemView;
        selfie = (ImageView) itemView.findViewById(R.id.selfie_image);
        caption = (TextView) itemView.findViewById(R.id.selfie_caption);


    }
}

