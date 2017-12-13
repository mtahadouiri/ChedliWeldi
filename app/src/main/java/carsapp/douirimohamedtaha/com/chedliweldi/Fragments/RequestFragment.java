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
import android.support.v7.widget.DividerItemDecoration;
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
import org.json.JSONObject;

import java.util.*;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.ProfilActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.RequestsActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RecycleItemClickListener;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RequestRecyclerViewAdapter;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class RequestFragment extends Fragment {
    RecyclerView offers ;
    RequestRecyclerViewAdapter adapter ;
    LinearLayoutManager layoutManager;
    JSONArray requests;
    String idUser;

    public static RequestFragment newInstance(String idUser) {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        args.putString("id", idUser);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.activity_request, container, false);
        String id = getArguments().getString("id");
        offers = (RecyclerView) v. findViewById(R.id.recycler_view);
        layoutManager=new LinearLayoutManager(getContext());
        offers.setLayoutManager(layoutManager);

        offers.addOnItemTouchListener(new RecycleItemClickListener(getContext(), offers, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                /*
                Log.i(" dfs","sdf");
                boolean wrapInScrollView = true;
                final MaterialDialog n =  new MaterialDialog.Builder(RequestsActivity.this
                )

                        .customView(R.layout.profil_request, false)

                        .show();

                View v = n.getView();
                final Button accept = (Button) v.findViewById(R.id.btnAccept);
                final ImageView profilImage = (ImageView) v.findViewById(R.id.profileImage);

                final TextView txtName = (TextView) v.findViewById(R.id.txtUserName);

                try {
                    Glide.with(RequestsActivity.this).load(AppController.IMAGE_SERVER_ADRESS+requests.getJSONObject(position).getString("photo")).into(profilImage);
                    txtName.setText(requests.getJSONObject(position).getString("firstName")+" " +requests.getJSONObject(position).getString("lastName") );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("oo ","sdfsdf");
                       requests.remove(position);
                        adapter.notifyItemRemoved(position);
                        n.dismiss();
                    }
                });

                Button refuse = (Button) v.findViewById(R.id.btnRefuse);
                refuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("oo ","sdfsdf");
                        n.dismiss();
                    }
                });

                */

                Intent i = new Intent(getContext(),ProfilActivity.class);
                //   JSONObject jsonObject  =new JSONObject();
                // i.putExtra("json",jsonObject);
                try {
                    ProfilActivity.user=requests.getJSONObject(position);
                    startActivity(i);
                } catch (JSONException e) {

                }

            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));



        getRequests(id);



        return v;
    }

    private void getRequests(final String id_offer) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getRequestsByOffer";
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
                        requests = jsonObject.getJSONArray("requests");

                        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                                offers.getContext(),
                                layoutManager.getOrientation()
                        );
                        offers.addItemDecoration(mDividerItemDecoration);
                        adapter = new RequestRecyclerViewAdapter(getActivity(), requests);
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
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_offer",id_offer);
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

    private void respondToRequest(final String respond) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"respondRequest";
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
                headers.put("respond",respond);
                //   headers.put("id_user",LoginActivity.connectedUser);
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
