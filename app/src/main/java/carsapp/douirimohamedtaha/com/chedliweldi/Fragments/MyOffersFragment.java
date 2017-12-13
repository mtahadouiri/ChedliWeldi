package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.*;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.LoginActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.MyOfferActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.ProfilActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.RequestsActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.MyOfferRecyclerViewAdapter;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RecycleItemClickListener;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class MyOffersFragment extends Fragment {

    RecyclerView offers ;
    MyOfferRecyclerViewAdapter adapter ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.activity_my_offers, container, false);

        offers = (RecyclerView) v.findViewById(R.id.recycler_view);
        offers.setLayoutManager(new LinearLayoutManager(getContext()));

        offers.addOnItemTouchListener(new RecycleItemClickListener(getContext(), offers, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                final String  ff;
                try {
                    ff = off.getJSONObject(position).getString("id");
                    RequestFragment requestFragment = RequestFragment.newInstance(ff);
                    getFragmentManager().beginTransaction().add(R.id.frame, requestFragment).addToBackStack("").commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));

        getOffers(LoginActivity.connectedUser);



        return v;
    }

    JSONArray off;
    private void getOffers(final String id_user) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getUserOffers";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {


                    off = new JSONArray(response);




                    Log.i("","");



                    adapter = new MyOfferRecyclerViewAdapter(getActivity(), off);
                    offers.setAdapter(adapter);



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
                headers.put("id_user",id_user);
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
