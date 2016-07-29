package com.cherryfish.stephanie.myfavoroutepics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        recyclerView = (RecyclerView) view.findViewById(R.id.selfie_recycler);

        // Setting the LayoutManager.
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        noPics = (TextView) view.findViewById(R.id.no_pics);

        //getting stored images and captions
        MainActivity.images = getImages();
        MainActivity.captions = getCaptions();

        //show message instructing to take photos if no images
        if (MainActivity.images.size()>0) {
            System.out.println("view invisible");
            System.out.println(MainActivity.images.size());
            noPics.setVisibility(View.GONE);
        }
        else {
            System.out.println("view visible");
            System.out.println(MainActivity.images.size());
            noPics.setVisibility(View.VISIBLE);
        }
        adapter = new ListAdapter((MainActivity) getActivity(), MainActivity.images, MainActivity.captions);
        recyclerView.setAdapter(adapter);

        //set up colors
        MainActivity.showSelfies.setBackgroundColor(MainActivity.teal);
        MainActivity.showSelfies.setTextColor(MainActivity.navy);
        MainActivity.showTravels.setBackgroundColor(MainActivity.navy);
        MainActivity.showTravels.setTextColor(MainActivity.teal);
        return view;


    }


    //update view to show images through adapter and save selfies to device
    public void addImages(List<Bitmap> selfies, List<String> captions) {
        noPics.setVisibility(View.GONE);
        adapter.photos = selfies;
        adapter.captions = captions;
        adapter.notifyDataSetChanged();
        saveImages();
        saveCaptions();
    }

    //save selfie images to device
    public void saveImages() {

        for (int i = 0; i < MainActivity.images.size(); i++) {

            String name = i + "." + "png";
            FileOutputStream outputStream;
            try {
                outputStream = getContext().openFileOutput(name, Context.MODE_PRIVATE);
                System.out.println(outputStream);
                MainActivity.images.get(i).compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //save captions to device
    public void saveCaptions() {

        for (int i = 0; i < MainActivity.captions.size(); i++) {

            String name = i + "." + "txt";
            System.out.println("saving caption as " + name);
            FileOutputStream outputStream;
            try {
                outputStream = getContext().openFileOutput(name, Context.MODE_PRIVATE);
                System.out.println(outputStream);
                OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream);

                streamWriter.write(MainActivity.captions.get(i));
                streamWriter.flush();
                streamWriter.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
//                System.out.println("getting caption" + sb.toString());
                tempCaps.add(sb.toString());
            } catch (Exception e) {
                System.out.println("problem finding caption");
            }

        }
        return tempCaps;

    }

    //get the saved images
    public List<Bitmap> getImages() {
        List<Bitmap> tempImgs = new ArrayList<Bitmap>();
        for (int i = 0; i < MainActivity.selfieListSize; i++) {
            String name = i + ".png";
            try {
                FileInputStream fis = getContext().openFileInput(name);
                Bitmap b = BitmapFactory.decodeStream(fis);
                fis.close();
//                System.out.println("getting image " + fis);
                tempImgs.add(b);

            } catch (Exception e) {
                System.out.println("problem finding image");
            }

        }
        return tempImgs;

    }



}
