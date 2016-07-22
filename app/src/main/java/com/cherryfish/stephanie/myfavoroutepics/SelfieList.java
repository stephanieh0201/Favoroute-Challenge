package com.cherryfish.stephanie.myfavoroutepics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class SelfieList extends Fragment{
    private RecyclerView recyclerView;
    private SelfieAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selfie_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);


        // Setting the LayoutManager.
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new SelfieAdapter((MainActivity)getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }
}
