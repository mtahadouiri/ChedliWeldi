package com.esprit.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
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
import java.util.Map;

import com.esprit.chedliweldi.Activities.LoginActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.adapters.OfferRecycleViewAdapter;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class OffersFragment extends Fragment {

    RecyclerView offers ;
    OfferRecycleViewAdapter adapter ;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.activity_offer_list, container, false);

        offers = (RecyclerView)v. findViewById(R.id.recycler_view);
        offers.setLayoutManager(new LinearLayoutManager(getContext()));
        getOffers();

        return v;
    }

    private void getOffers() {




        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"offers";
        StringRequest sr = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){
                        Log.i("etat","failed");
                    }
                    else{
                        JSONArray offerss = jsonObject.getJSONArray("offers");

                        adapter = new OfferRecycleViewAdapter(getActivity(), offerss);
                        offers.setAdapter(adapter);
                        Log.i("etat","success");
                    }


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
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                java.util.Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }


    private void sendRequest(final String id_user, final String id_offer) {




        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"sendRequest";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){
                        Log.i("etat","failed");
                    }
                    else{
                        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                                .title("request send")
                                .content("the request has been sent successfully")
                                .positiveText("ok")
                                .show();

                        Log.i("etat","success");
                    }


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

                headers.put("id_user", LoginActivity.connectedUser);
                headers.put("id_offer", id_offer);
                return headers;
            }

            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }








}
