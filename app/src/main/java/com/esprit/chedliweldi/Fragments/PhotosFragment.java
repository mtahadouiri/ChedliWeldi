package com.esprit.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import com.esprit.chedliweldi.Activities.FullScreenImageGalleryActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.DisplayUtility;
import com.esprit.chedliweldi.adapters.ImageGalleryAdapter;
import com.esprit.chedliweldi.adapters.RecycleItemClickListener;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class PhotosFragment extends Fragment  {
    LinearLayoutCompat linear ;


    public static PhotosFragment newInstance(String idUser) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putString("id", idUser);

        fragment.setArguments(args);
        return fragment;
    }

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String id = getArguments().getString("id");


        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v= inflater.inflate(R.layout.photos, container, false);

        recyclerView =(RecyclerView) v.findViewById(R.id.rv);


      getPhotos(id);

       // getActivity().  getSupportFragmentManager().executePendingTransactions();
        return  v;
    }


    @Override
    public void onResume() {

        super.onResume();



    }
    ArrayList<String> photos = new ArrayList();
    private void getPhotos(final String idUser) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"photos";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){



                        photos.add("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
                        photos.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");
                        photos.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");

                    }
                    else{

                       JSONArray photosJson = jsonObject.getJSONArray("photos");
                       for (  int i =0 ;i<photosJson.length();i++){
                         JSONObject photo = photosJson.getJSONObject(i);


                           photos.add(AppController.IMAGE_SERVER_ADRESS+photo.getString("photo"));
                       }


                    }
                    setUpRecyclerView();






                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Log.d("", ""+error.getMessage()+","+error.toString());
            }
        }){

            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String,String> headers = new HashMap<String, String>();
                headers.put("user_id",idUser);
                //  headers.put("abc", "value");
                return headers;
            }

            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                java.util.Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);

        ;


    }



   RecyclerView recyclerView;
    ImageGalleryAdapter imageGalleryAdapter;

    private void setUpRecyclerView() {
        int numOfColumns;
        if (DisplayUtility.isInLandscapeMode(getActivity())) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numOfColumns));
        imageGalleryAdapter = new ImageGalleryAdapter(getContext(), photos);
        recyclerView.setAdapter(imageGalleryAdapter);
        recyclerView.addOnItemTouchListener(new RecycleItemClickListener(getContext(),recyclerView, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                Intent i = new Intent(getActivity(), FullScreenImageGalleryActivity.class);
                i.putStringArrayListExtra(FullScreenImageGalleryActivity.KEY_IMAGES,photos);
                i.putExtra(FullScreenImageGalleryActivity.KEY_POSITION,position);
                startActivity(i);



            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));




    }

    @Nullable
    @Override
    public View getView() {

        return super.getView();
    }


}
