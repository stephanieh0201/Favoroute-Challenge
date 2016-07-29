package com.cherryfish.stephanie.myfavoroutepics;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class TravelSearchDialog extends DialogFragment {

    EditText searchTerm;
    Button searchButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // set up dialog to allow user to search for travel destination
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.travel_search_dialog,null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        //finding edittext box for user to enter travel search term
        searchTerm = (EditText) dialogView.findViewById(R.id.travel_search_term);

        //set up search button
        searchButton= (Button) dialogView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //ensure user has entered text into field
                if (searchTerm.getText().length() > 0) {
                    dialog.dismiss();
                    MainActivity.searchTerm = searchTerm.getText().toString();
                    TravelList travelList = new TravelList();
                    getFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, travelList, "travelFrag")
                            .addToBackStack(null)
                            .commit();
                } else
                    Toast.makeText(getContext(), "Enter a travel search term.", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;

    }
}

