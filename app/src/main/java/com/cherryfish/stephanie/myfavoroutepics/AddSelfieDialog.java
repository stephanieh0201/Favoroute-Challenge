package com.cherryfish.stephanie.myfavoroutepics;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class AddSelfieDialog extends DialogFragment {

    EditText selfieCaption;
    Button addButton;
    ImageView selfieImage;
    Bundle addBundle;
    private static final int REQUEST_ACCESS = 1;
    private static String[] ACCESS_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle bundle = getArguments();

        View dialogView = inflater.inflate(R.layout.add_selfie_dialog,null);
        builder.setView(dialogView);
        addBundle = new Bundle();

        final AlertDialog dialog = builder.create();
        selfieCaption = (EditText) dialogView.findViewById(R.id.add_selfie_caption);

        selfieImage = (ImageView) dialogView.findViewById(R.id.add_selfie_image);
        selfieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("choosing image or camera");
                int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (permission != PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            ACCESS_PERMISSIONS,
                            REQUEST_ACCESS
                    );
                }
                    else {
                CameraOrImageDialog cameraOrImageDialog = new CameraOrImageDialog();
                cameraOrImageDialog.show(getActivity().getSupportFragmentManager(), "CameraOrImage");
                    }
            }
        });
        addButton= (Button) dialogView.findViewById(R.id.add_selfie_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });





        return dialog;

    }

    public void updateImage(Uri image){
        System.out.println("updating image" + image.toString());
        selfieImage.setImageURI(image);
    }
}

