package com.cherryfish.stephanie.myfavoroutepics;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;


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

        View dialogView = inflater.inflate(R.layout.add_selfie_dialog,null);
        builder.setView(dialogView);
        dialog = builder.create();

        //used to proceed with adding image to list, initialize as false and set to true once image is selected
        photoSelected=false;

        //initialize selfie image view and set up onClickListener to access images/camera
        selfieImage = (ImageView) dialogView.findViewById(R.id.add_selfie_image);
        selfieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first check if app has permission to access camera
                int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    //request permission if not granted
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            ACCESS_PERMISSIONS,
                            REQUEST_ACCESS
                    );
                } else{
                    //display dialog for user to select camera or existing photo
                    CameraOrImageDialog cameraOrImageDialog = new CameraOrImageDialog();
                    cameraOrImageDialog.show(getActivity().getSupportFragmentManager(), "CameraOrImage");
                }
            }
        });

        // initialize caption edittext field
        selfieCaption = (EditText) dialogView.findViewById(R.id.add_selfie_caption);
        fragment=this;

        // initialize add button
        addButton= (Button) dialogView.findViewById(R.id.add_selfie_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check that a photo has been selected
                if (photoSelected) {
                    Bitmap bitmap = ((BitmapDrawable) selfieImage.getDrawable()).getBitmap();
                    //store caption to save, convert selfie image to file to upload to thumbor
                    caption = selfieCaption.getText().toString();
                    image = bitmapToUri(getContext(), bitmap);
                    File finalFile = new File(getRealPathFromURI(image));
                    //API call to store image as thumbor instance
                    new ThumborPost(fragment).execute(finalFile);


                }
                //if no image selected display message to user
                else Toast.makeText(getContext(), "Select an image to upload", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;

    }


    // update imageview when and image is selected from camera or storage
    public void updateImage(Bitmap image){
        selfieImage.setImageBitmap(image);
        selfieImage.setBackground(null);
        selfieImage.setPadding(0,0,0,0);
    }

    //returns the URI of bitmap object
    public Uri bitmapToUri (Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "newimage", null);
        return Uri.parse(path);
    }

    //returns String of full path of image
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public void update(String url){
        //add items to first spot in list if list is new
        if (MainActivity.urls != null) {
            MainActivity.captions.add(0, caption);
            MainActivity.urls.add(0, url);
        }
        //create new lists to store if first image for app
        else {
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

