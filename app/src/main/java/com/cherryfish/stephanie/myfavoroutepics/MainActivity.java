package com.cherryfish.stephanie.myfavoroutepics;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView addSelfie, showSelfies, showTravels;
    FragmentManager fragmentManager;
    int teal, navy;
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
                Toast.makeText(MainActivity.this, "Add Selfie", Toast.LENGTH_SHORT).show();
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

    public void showSelfieList(){

        showSelfies.setBackgroundColor(teal);
        showSelfies.setTextColor(navy);
        showTravels.setBackgroundColor(navy);
        showTravels.setTextColor(teal);

        SelfieList selfieList = new SelfieList();
        fragmentManager.beginTransaction()
//          .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            .add(R.id.fragment_container, selfieList)
            .addToBackStack(null)
            .commit();
    }
    public void showTravelsList(){

        showSelfies.setBackgroundColor(navy);
        showSelfies.setTextColor(teal);
        showTravels.setBackgroundColor(teal);
        showTravels.setTextColor(navy);

        TravelSearchDialog travelSearchDialog= new TravelSearchDialog();
        travelSearchDialog.show(getSupportFragmentManager(), "TravelSearchDialog");


    }
}
