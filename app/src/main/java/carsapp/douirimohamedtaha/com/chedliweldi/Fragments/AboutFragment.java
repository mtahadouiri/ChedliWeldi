package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
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
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class AboutFragment extends Fragment {
TagGroup tags;
    ImageGalleryFragment fragment;
    LinearLayout t;
    //Overriden method onCreateView
TextView txtAbout ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes

        View v = inflater.inflate(R.layout.about, container, false);
        String id = getArguments().getString("id");
        String about = getArguments().getString("about");

        txtAbout=(TextView) v.findViewById(R.id.txtAbout);
                tags = (TagGroup) v.findViewById(R.id.tags);

        txtAbout.setText(about);
        tags.setTags(new String[]{"good listener","friendly","good looking","amazing dancer","teach music"});


getSkills(id);


        











        return v;
    }
LinearLayout tmp;
    @Override
    public void onResume() {
        super.onResume();

        
    }



    public static AboutFragment newInstance(String idUser,String about) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString("id", idUser);
        args.putString("about", about);

        fragment.setArguments(args);
        return fragment;
    }



    private void getSkills(final String idUser) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"skills";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){


                    }
                    else{
                        ArrayList<String> skills = new ArrayList<>();
                        JSONArray skillsJson = jsonObject.getJSONArray("skills");
                        for (  int i =0 ;i<skillsJson.length();i++){
                            JSONObject skill = skillsJson.getJSONObject(i);


                            skills.add(skill.getString("skill"));
                        }

                       tags.setTags(skills);

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
