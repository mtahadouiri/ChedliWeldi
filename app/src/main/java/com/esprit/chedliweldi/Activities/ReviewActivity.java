package com.esprit.chedliweldi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.adapters.ReviewRecycleViewAdapter;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ReviewActivity extends AppCompatActivity {

    RecyclerView reviews ;
    TextView nbrReviews ;
   ReviewRecycleViewAdapter adapter ;
 LinearLayoutManager layoutManager;
    MaterialRatingBar rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews);

       ;

        reviews = (RecyclerView) findViewById(R.id.recycler_view);
        nbrReviews = (TextView) findViewById(R.id.nbrReviews);
        rate = (MaterialRatingBar) findViewById(R.id.rate);

        layoutManager=new LinearLayoutManager(this);
        reviews.setLayoutManager(layoutManager);




        Intent  k = getIntent();

        //String id =k.getStringExtra("id");

          getReviews("4");




    }
    JSONArray requests;
    private void getReviews(final String idUser) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"reviews";
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
                       requests = jsonObject.getJSONArray("reviews");
                     JSONObject   data =jsonObject.getJSONObject("data");
                                nbrReviews.setText("Reviews " +data.getString("count"));
                        rate.setRating((float) data.getDouble("rate"));
                        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                                reviews.getContext(),
                                layoutManager.getOrientation()
                        );
                        reviews.addItemDecoration(mDividerItemDecoration);
                        adapter = new ReviewRecycleViewAdapter(ReviewActivity.this, requests);
                        reviews.setAdapter(adapter);
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
                headers.put("user_id",idUser);
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
