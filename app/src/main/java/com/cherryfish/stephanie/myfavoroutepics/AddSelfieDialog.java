package com.cherryfish.stephanie.myfavoroutepics;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class AddSelfieDialog extends DialogFragment {

    EditText selfieCaption;
    Button addButton;
    ImageView selfieImage;
    AlertDialog dialog;
    static Boolean photoSelected;
    private static final int REQUEST_ACCESS = 1;
    private static String[] ACCESS_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create dialog box displaying selfie and caption
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        photoSelected=false;
        View dialogView = inflater.inflate(R.layout.add_selfie_dialog,null);
        builder.setView(dialogView);
        dialog = builder.create();

        selfieImage = (ImageView) dialogView.findViewById(R.id.add_selfie_image);
        selfieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("choosing image or camera");
                int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                //check if app has permission to access camera
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    //request permission if not granted
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            ACCESS_PERMISSIONS,
                            REQUEST_ACCESS
                    );
                } else {
                    //display dialog for user to select camera or existing photo
                    CameraOrImageDialog cameraOrImageDialog = new CameraOrImageDialog();
                    cameraOrImageDialog.show(getActivity().getSupportFragmentManager(), "CameraOrImage");
                }
            }
        });

        // set up caption edittext field
        selfieCaption = (EditText) dialogView.findViewById(R.id.add_selfie_caption);

        // set up add button
        addButton= (Button) dialogView.findViewById(R.id.add_selfie_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check that a photo has been selected
                if (photoSelected) {
                    Bitmap bitmap = ((BitmapDrawable) selfieImage.getDrawable()).getBitmap();
                    String caption = selfieCaption.getText().toString();
                    //add image and caption to first position in list if exisiting images are stored
                    if (MainActivity.images != null) {
                        MainActivity.images.add(0, bitmap);
                        MainActivity.captions.add(0, caption);
                    }
                    //create new lists to store if first image for app
                    else {
                        MainActivity.images = new ArrayList<Bitmap>();
                        MainActivity.images.add(bitmap);
                        MainActivity.captions = new ArrayList<String>();
                        MainActivity.captions.add(caption);
                    }
                    SelfieList fragment = (SelfieList) getFragmentManager().findFragmentByTag("SelfieList");
                    fragment.addImages(MainActivity.images, MainActivity.captions);

                    dialog.dismiss();
                }
                //if no image selected display message to user
                else Toast.makeText(getContext(), "Select an image to upload", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;

    }


    // method to update imageview when selected from camera or storage
    public void updateImage(Bitmap image){
        selfieImage.setImageBitmap(image);
        selfieImage.setBackground(null);
        selfieImage.setPadding(0,0,0,0);
    }
}

