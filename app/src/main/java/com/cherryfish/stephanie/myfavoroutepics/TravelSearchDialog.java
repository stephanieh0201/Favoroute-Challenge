package com.cherryfish.stephanie.myfavoroutepics;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class TravelSearchDialog extends DialogFragment {

    EditText searchTerm;
    Button searchButton;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle bundle = getArguments();
        View dialogView = inflater.inflate(R.layout.travel_search_dialog,null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();
        searchTerm = (EditText) dialogView.findViewById(R.id.travel_search_term);

        searchButton= (Button) dialogView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TravelList fragment = (TravelList)getChildFragmentManager().findFragmentByTag("travelFrag");
//                System.out.println(searchTerm.getText().toString());
//                fragment.search(searchTerm.getText().toString());

                dialog.dismiss();
                MainActivity.searchTerm=searchTerm.getText().toString();
                TravelList travelList = new TravelList();
                getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, travelList, "travelFrag")
                        .addToBackStack(null)
                        .commit();
            }
        });





        return dialog;

}
}

