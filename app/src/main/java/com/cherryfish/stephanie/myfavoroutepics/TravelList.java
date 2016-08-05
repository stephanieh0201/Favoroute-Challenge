package com.cherryfish.stephanie.myfavoroutepics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class TravelList extends Fragment{
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<String> captions;
    List<String> urls;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travel_list, container, false);

        //initialize the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);

        //set colors
        MainActivity.showSelfies.setBackgroundColor(MainActivity.navy);
        MainActivity.showSelfies.setTextColor(MainActivity.teal);
        MainActivity.showTravels.setBackgroundColor(MainActivity.teal);
        MainActivity.showTravels.setTextColor(MainActivity.navy);


        // initialize layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //initialize adapter
        adapter = new ListAdapter((MainActivity)getActivity(), urls, captions);
        recyclerView.setAdapter(adapter);

        return view;

    }

    //display the travel data grabbed from Flickr API call
    public void displayData(List<String> images, List<String> captions){
        adapter.urls=images;
        adapter.captions=captions;
        adapter.notifyDataSetChanged();
    }
}
