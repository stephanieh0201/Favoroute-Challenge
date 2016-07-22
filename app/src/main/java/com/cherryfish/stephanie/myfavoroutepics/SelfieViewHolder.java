package com.cherryfish.stephanie.myfavoroutepics;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class SelfieViewHolder  extends RecyclerView.ViewHolder {

    public ImageView selfie;
    public TextView caption;
    public View item;

    public SelfieViewHolder(View itemView) {
        super(itemView);
        item=itemView;
        selfie = (ImageView) itemView.findViewById(R.id.selfie_image);
        caption = (TextView) itemView.findViewById(R.id.selfie_caption);


    }
}

