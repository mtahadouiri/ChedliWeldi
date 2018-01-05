package com.esprit.chedliweldi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.adapters.DisplayTaskListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import mehdi.sakout.fancybuttons.FancyButton;

public class ScheduledOfferBabysitterActivity extends AppCompatActivity {


 LinearLayoutManager layoutManager;
 TextView description ;
    TextView date ;
    ImageView image;
    TextView fullName;
    FancyButton btnPublic ;
    RecyclerView recyclerView;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent  k = getIntent();
        id =k.getStringExtra("id");
        setContentView(R.layout.scheduled_offer_babysitter);
        description = (TextView) findViewById(R.id.txtDescription);
        fullName = (TextView) findViewById(R.id.txtFullName);
        image = (ImageView) findViewById(R.id.profileImage);
        date = (TextView) findViewById(R.id.txtDate);
        recyclerView=(RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getOfferDetail(id);
        getTasks(id,recyclerView);
    }

    private void getOfferDetail(final String idOffer) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getOffer2";
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

                   JSONObject offer = jsonObject.getJSONObject("offer");
                        description.setText(offer.getString("description"));
                        try {
                            Date dd = dateFormatter.parse(offer.getString("start"));
                            dateFormatter.applyPattern("E M 'at' h:m a");
                          String  dateStr=dateFormatter.format(dd);
                            date.setText(dateStr);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        fullName.setText(offer.getString("firstName")+" "+offer.getString("lastName"));
                        // customViewHolder.description.setText(feedItem.getString("description"));
                        Glide.with(getBaseContext()).load(AppController.IMAGE_SERVER_ADRESS+offer.getString("photo")).transform(new AppController.CircleTransform(getBaseContext())).into(image);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_offer",idOffer);
                //   headers.put("id_user",LoginActivity.connectedUser);
                //  headers.put("abc", "value");
                return headers;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }
    private void getTasks(final String idUser,RecyclerView rv) {


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
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        int dd= tasks.length();
                        int ddd= tasks.length();



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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_offer",idUser);
                //  headers.put("abc", "value");
                return headers;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
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
