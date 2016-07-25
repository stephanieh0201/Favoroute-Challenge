package com.cherryfish.stephanie.myfavoroutepics;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class CameraOrImageDialog extends DialogFragment {

    Button camera, photo;
    AlertDialog dialog;
    private static int CAMERA=0, PHOTO=1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle bundle = getArguments();
        View dialogView = inflater.inflate(R.layout.camera_or_image, null);
        builder.setView(dialogView);

        dialog = builder.create();


        photo = (Button) dialogView.findViewById(R.id.photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, PHOTO);
            }
        });
        camera = (Button) dialogView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, CAMERA);

            }
        });

        return dialog;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (requestCode==CAMERA)
                if(resultCode == MainActivity.RESULT_OK){

                    Bundle extras = imageReturnedIntent.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    AddSelfieDialog fragment = (AddSelfieDialog)getFragmentManager().findFragmentByTag("AddSelfieDialog");
                    fragment.updateImage(imageBitmap);

                }

        if (requestCode==PHOTO)
                if(resultCode == MainActivity.RESULT_OK){

                    Uri selectedImage = imageReturnedIntent.getData();
                    Bitmap bitmap;

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        AddSelfieDialog fragment = (AddSelfieDialog)getFragmentManager().findFragmentByTag("AddSelfieDialog");
                        fragment.updateImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

        dialog.dismiss();
        }

}

