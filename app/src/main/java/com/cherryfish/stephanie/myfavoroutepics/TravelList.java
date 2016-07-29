package com.cherryfish.stephanie.myfavoroutepics;

import android.graphics.Bitmap;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travel_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);



        MainActivity.showSelfies.setBackgroundColor(MainActivity.navy);
        MainActivity.showSelfies.setTextColor(MainActivity.teal);
        MainActivity.showTravels.setBackgroundColor(MainActivity.teal);
        MainActivity.showTravels.setTextColor(MainActivity.navy
        );


        // Setting the LayoutManager.
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListAdapter((MainActivity)getActivity(), null, null);
        recyclerView.setAdapter(adapter);
        new PhotoSearch(this).execute(MainActivity.searchTerm);
        return view;


    }


    public void displayData(List<Bitmap> images, List<String> captions){
        adapter.photos=images;
        adapter.captions=captions;
        adapter.notifyDataSetChanged();
    }
}
