package com.cherryfish.stephanie.myfavoroutepics;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.pollexor.Thumbor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
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
    String caption;
    Uri image;
    AddSelfieDialog fragment;
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
//        String uriS = "10.0.0.9:8888/unsafe/http://www.transindus.co.uk/sites/default/files/14-highlight-Perhentian-Islands-Malaysia-b.jpg";
//        Uri uri = Uri.parse(uriS);
//        selfieImage.setImageURI(uri);
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
        fragment=this;

        // set up add button
        addButton= (Button) dialogView.findViewById(R.id.add_selfie_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check that a photo has been selected
                if (photoSelected) {
                    Bitmap bitmap = ((BitmapDrawable) selfieImage.getDrawable()).getBitmap();

                    caption = selfieCaption.getText().toString();
                    image = bitmapToUri(getContext(), bitmap);
//                    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
//                    byte [] b=baos.toByteArray();
//                    String temp= Base64.encodeToString(b, Base64.DEFAULT);
//                    return temp;
                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP


                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(image));
                    System.out.println("To string: " + finalFile.toString());
                    System.out.println("Abs path: " + finalFile.getAbsolutePath());
                    System.out.println("Abs file: " + finalFile.getAbsoluteFile());
//                    System.out.println(temp);


//                    Thumbor thumbor = Thumbor.create("http://10.0.0.9:8888/");
                    new ThumborPost(fragment).execute(finalFile);

//                    String url = thumbor.buildImage(finalFile.toString()).toUrl();
//                    System.out.println(url);
//                    System.out.println(finalFile.toString());
//                    update(url);

                    //add image and caption to first position in list if existing images are stored

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

    public Uri bitmapToUri (Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "newimage", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void update(String url){
        System.out.println("adding caption and url");
        if (MainActivity.urls != null) {
//                        MainActivity.images.add(0, bitmap);
            MainActivity.captions.add(0, caption);
            MainActivity.urls.add(url);



        }
        //create new lists to store if first image for app
        else {
//                        MainActivity.images = new ArrayList<Bitmap>();
//                        MainActivity.images.add(bitmap);
            MainActivity.urls = new ArrayList<String>();
            MainActivity.urls.add(url);
            MainActivity.captions = new ArrayList<String>();
            MainActivity.captions.add(caption);
        }
        SelfieList fragment = (SelfieList) getFragmentManager().findFragmentByTag("SelfieList");
        fragment.addImages(MainActivity.urls, MainActivity.captions);

        dialog.dismiss();
    }
}

