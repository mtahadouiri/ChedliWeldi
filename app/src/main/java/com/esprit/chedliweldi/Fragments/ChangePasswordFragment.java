package com.esprit.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class ChangePasswordFragment extends Fragment {

Button confirm ;
EditText password;
EditText newPassword;
EditText confirmPassword;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.change_password, container, false);
       initToolbar(v);
       confirm =(Button) v.findViewById(R.id.btnConfirm);
       password =(EditText) v.findViewById(R.id.password);
       confirmPassword =(EditText) v.findViewById(R.id.confirmPassword);
       newPassword =(EditText) v.findViewById(R.id.newPassword);

       confirm.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
           validate(Login.connectedUser,password.getText().toString());
           }
       });
        return v;
    }







    void initToolbar(View v){

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("sdfsfd");
        }

    }



    private void validate(final String id, String password ) {
        String url = AppController.SERVER_ADRESS+"validatePassword";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){


                    }
                    else{
                       Log.i("","");
                        changePassword(Login.connectedUser,newPassword.getText().toString());
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
            protected java.util.Map<String,String> getParams(){
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id);
                params.put("password", password);
                return params;
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




    private void changePassword(final String id, String password ) {

        String url = AppController.SERVER_ADRESS+"changePassword";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){


                    }
                    else{
                        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                                .title("password")
                                .content("password changed successfuly")
                                .positiveText("ok")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {

                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        getFragmentManager().popBackStackImmediate();
                                    }
                                })
                                .show();


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
            protected java.util.Map<String,String> getParams(){
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id);
                params.put("password", password);
                return params;
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
