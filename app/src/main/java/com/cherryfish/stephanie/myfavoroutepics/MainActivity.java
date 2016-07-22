package com.cherryfish.stephanie.myfavoroutepics;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static TextView addSelfie, showSelfies, showTravels;
    FragmentManager fragmentManager;
    public static int teal, navy;
    public static String searchTerm;

//    apikey==0f6f6c131f8eb464ded3ac9ada60bc00

//    apisecret=eed5bbe4aabbb63f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        teal = ContextCompat.getColor(getApplicationContext(), R.color.Teal);
        navy = ContextCompat.getColor(getApplicationContext(), R.color.Navy);

        addSelfie = (TextView) findViewById(R.id.add_selfie);
        addSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSelfie();
            }
        });

        showSelfies = (TextView) findViewById(R.id.selfies);
        showSelfies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelfieList();
            }
        });

        showTravels = (TextView) findViewById(R.id.travels);
        showTravels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTravelsList();
            }
        });

        //set up fragment manager to navigate through the app
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            showSelfieList();
        } else {

        }
    }

    public void showSelfieList() {


        SelfieList selfieList = new SelfieList();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, selfieList)
                .addToBackStack(null)
                .commit();
    }

    public void showTravelsList() {


        TravelSearchDialog travelSearchDialog = new TravelSearchDialog();
        travelSearchDialog.show(getSupportFragmentManager(), "TravelSearchDialog");


    }

    public void addSelfie() {
        AddSelfieDialog addSelfieDialog = new AddSelfieDialog();
        addSelfieDialog.show(getSupportFragmentManager(), "AddSelfieDialog");
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            super.onBackPressed();
            super.onBackPressed();
        } else {
            super.onBackPressed();

        }
        Fragment f = (Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof SelfieList){
            MainActivity.showSelfies.setBackgroundColor(MainActivity.teal);
            MainActivity.showSelfies.setTextColor(MainActivity.navy);
            MainActivity.showTravels.setBackgroundColor(MainActivity.navy);
            MainActivity.showTravels.setTextColor(MainActivity.teal);
        } else if (f instanceof TravelList) {
            MainActivity.showSelfies.setBackgroundColor(MainActivity.navy);
            MainActivity.showSelfies.setTextColor(MainActivity.teal);
            MainActivity.showTravels.setBackgroundColor(MainActivity.teal);
            MainActivity.showTravels.setTextColor(MainActivity.navy
            );



        }
    }
}

