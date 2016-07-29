package com.cherryfish.stephanie.myfavoroutepics;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class CameraOrImageDialog extends DialogFragment {

    Button camera, photo;
    AlertDialog dialog;
    private static int CAMERA=0, PHOTO=1;
    Uri image;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create dialog box to display image options
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.camera_or_image, null);
        builder.setView(dialogView);
        dialog = builder.create();

        //set up button to select existing image to post
        photo = (Button) dialogView.findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, PHOTO);
            }
        });

        //set up button to take new photo to post
        camera = (Button) dialogView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo;
                try {
                    //create temporary storage for captured image
                    photo = storePhoto("picture", ".jpg");
                    photo.delete();
                }
                catch(Exception e) {
                    System.out.println("Unable to store image");
                    return;
                }
                image = Uri.fromFile(photo);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, image);

                //start camera intent
                startActivityForResult(takePicture, CAMERA);
            }
        });
        return dialog;
    }

    //process image results from camera or photo intents
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        //if action was to take new photo:
        if (requestCode==CAMERA) {
            if (resultCode == MainActivity.RESULT_OK) {
                //find temporary file for camera image and send to addselfie dialog
                Bitmap cameraImage = findPhoto();
                AddSelfieDialog.photoSelected=true;
                AddSelfieDialog fragment = (AddSelfieDialog) getFragmentManager().findFragmentByTag("AddSelfieDialog");
                fragment.updateImage(cameraImage);
            }
        }
        //if action was to select existing image
        if (requestCode==PHOTO) {
            if (resultCode == MainActivity.RESULT_OK) {
                //get data from returned intent and send to fragment to view post
                Uri selectedImage = imageReturnedIntent.getData();
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    AddSelfieDialog.photoSelected=true;
                    AddSelfieDialog fragment = (AddSelfieDialog) getFragmentManager().findFragmentByTag("AddSelfieDialog");
                    fragment.updateImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        dialog.dismiss();
    }

    //store camera image temporarily so able to grab full size image later
    private File storePhoto(String name, String extension) throws Exception {
        File file= Environment.getExternalStorageDirectory();
        file=new File(file.getAbsolutePath()+"/.temp/");
        if(!file.exists())
        {
            file.mkdirs();
        }
        return File.createTempFile(name, extension, file);
    }

    //find stored camera image and return image
    public Bitmap findPhoto()
    {
        getActivity().getContentResolver().notifyChange(image, null);
        ContentResolver contentResolver = getActivity().getContentResolver();
        Bitmap bitmap;
        try
        {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(contentResolver, image);
            return bitmap;
        }
        catch (Exception e)
        {
           System.out.println("image not loaded");
        }
        return null;
    }


}

