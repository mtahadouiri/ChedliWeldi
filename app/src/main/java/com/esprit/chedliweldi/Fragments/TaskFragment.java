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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.adapters.DisplayTaskListAdapter;
import com.esprit.chedliweldi.adapters.PrivateOfferRecycleViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class TaskFragment extends Fragment {

    RecyclerView recyclerview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes

        View v = inflater.inflate(R.layout.task, container, false);
        recyclerview = (RecyclerView) v.findViewById(R.id.rv);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        String id=getArguments().getString("id");

        getTasks(id);



        //  DisplayTaskListAdapter adapter = new DisplayTaskListAdapter();


        











        return v;
    }
LinearLayout tmp;
    @Override
    public void onResume() {
        super.onResume();

        
    }



    public static TaskFragment newInstance(String idUser) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putString("id", idUser);


        fragment.setArguments(args);
        return fragment;
    }



    private void getTasks(final String idUser) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getTasks";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){


                    }
                    else{

                        JSONArray tasks = jsonObject.getJSONArray("tasks");

                      DisplayTaskListAdapter adapter = new DisplayTaskListAdapter(tasks);
                      recyclerview.setAdapter(adapter);



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


    }



}
