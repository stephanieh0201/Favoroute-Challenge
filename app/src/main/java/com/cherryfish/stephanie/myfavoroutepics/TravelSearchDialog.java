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

import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class TravelSearchDialog extends DialogFragment {

    EditText searchTerm;
    Button searchButton;
    AlertDialog dialog;
    TravelSearchDialog fragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // set up dialog to allow user to search for travel destination
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        fragment=this;
        View dialogView = inflater.inflate(R.layout.travel_search_dialog,null);
        builder.setView(dialogView);

        dialog = builder.create();
        //finding edittext box for user to enter travel search term
        searchTerm = (EditText) dialogView.findViewById(R.id.travel_search_term);

        //set up search button
        searchButton= (Button) dialogView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //ensure user has entered text into field
                if (searchTerm.getText().length() > 0) {
                    String searchWord =searchTerm.getText().toString().replace(" ", "%20");
                    MainActivity.searchTerm = searchWord;
                    new PhotoSearch(fragment).execute(MainActivity.searchTerm);

                } else
                    Toast.makeText(getContext(), "Enter a travel search term.", Toast.LENGTH_SHORT).show();
            }
        });

        return dialog;

    }

    //send data to travellist the travel data grabbed from Flickr API call
    public void displayData(List<String> images, List<String> captions){
        dialog.dismiss();
        TravelList travelList = new TravelList();
        travelList.urls=images;
        travelList.captions=captions;
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, travelList, "travelFrag")
                .addToBackStack(null)
                .commit();
    }

    public void displayError(){
        Toast.makeText(getContext(), "Error searching for " + searchTerm.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}


