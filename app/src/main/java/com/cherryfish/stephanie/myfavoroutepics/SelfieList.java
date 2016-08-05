package com.cherryfish.stephanie.myfavoroutepics;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stephanie Verlingo on 7/21/2016.
 */
public class SelfieList extends Fragment {
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView noPics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.selfie_list, container, false);

        //initialize recycler view for images
        recyclerView = (RecyclerView) view.findViewById(R.id.selfie_recycler);

        // initializing layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //initialize textview for no pictures
        noPics = (TextView) view.findViewById(R.id.no_pics);

        //grab image URLs and captions
        MainActivity.urls= getURLS();
        MainActivity.captions = getCaptions();

        //show message instructing to take photos if no images
        if (MainActivity.urls != null) {
            if (MainActivity.urls.size() > 0) {
                noPics.setVisibility(View.GONE);
            } else {
                noPics.setVisibility(View.VISIBLE);
            }
        }
            else {
                noPics.setVisibility(View.VISIBLE);
            }

        //create new adapter for image recycler view
        adapter = new ListAdapter((MainActivity) getActivity(), MainActivity.urls, MainActivity.captions);
        recyclerView.setAdapter(adapter);

        //set up colors
        MainActivity.showSelfies.setBackgroundColor(MainActivity.teal);
        MainActivity.showSelfies.setTextColor(MainActivity.navy);
        MainActivity.showTravels.setBackgroundColor(MainActivity.navy);
        MainActivity.showTravels.setTextColor(MainActivity.teal);
        return view;

    }


    //update view to show images through adapter and save selfies/captions to device
    public void addImages(List<String> urls, List<String> captions) {
        noPics.setVisibility(View.GONE);
        adapter.urls=urls;
        adapter.captions = captions;
        adapter.notifyDataSetChanged();
        saveURLs();
        saveCaptions();
    }


    //save selfie images to device
    public void saveURLs() {
        for (int i = 0; i < MainActivity.urls.size(); i++) {
            String name = "image" + i + ".txt";
            FileOutputStream outputStream;;
            try {
                outputStream = getContext().openFileOutput(name, Context.MODE_PRIVATE);
                OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
                streamWriter.write(MainActivity.urls.get(i));
                streamWriter.flush();
                streamWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //save captions to device
    public void saveCaptions() {

        for (int i = 0; i < MainActivity.captions.size(); i++) {

            String name = i + ".txt";
            FileOutputStream outputStream;
            try {
                outputStream = getContext().openFileOutput(name, Context.MODE_PRIVATE);
                OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);
                streamWriter.write(MainActivity.captions.get(i));
                streamWriter.flush();
                streamWriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //get the saved image URLs
    public List<String> getURLS(){
        List<String> captions = new ArrayList<String>();
        for (int i=0; i<MainActivity.selfieListSize; i++) {
            String name = "image" + i + ".txt";
            try {
                FileInputStream inputStream = getContext().openFileInput(name);
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                inputStream.close();
                captions.add(sb.toString());
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        return captions;

    }

    //get the saved captions
    public List<String> getCaptions(){
        List<String> tempCaps = new ArrayList<String>();
        for (int i=0; i<MainActivity.selfieListSize; i++) {
            String name = i + ".txt";
            try {
                FileInputStream inputStream = getContext().openFileInput(name);
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                inputStream.close();
                tempCaps.add(sb.toString());
            } catch (Exception e) {
                System.out.println(e);
            }

        }
        return tempCaps;

    }

}
