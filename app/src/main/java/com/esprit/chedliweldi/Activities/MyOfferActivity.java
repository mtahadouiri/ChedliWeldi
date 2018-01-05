package com.esprit.chedliweldi.Activities;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;

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
import com.esprit.chedliweldi.Utils.DrawerInitializer;
import com.esprit.chedliweldi.adapters.OfferAdapter;
import me.gujun.android.taggroup.TagGroup;

public class MyOfferActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";

   LinearLayoutCompat linear ;



    TagGroup tags;
    RelativeLayout infoTab ;
Button btn;
    GridView grid;
    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoffers);
        //NotificationBadge  mBadge = (NotificationBadge) findViewById(R.id.badge);
        //mBadge.setNumber(5);
        DrawerInitializer.initView(this);
        DrawerInitializer.setUpBoomMenu(this);

           Intent k = getIntent();
        String i =k.getStringExtra("id_request");
        String f =k.getStringExtra("id_offer");



        grid =  (GridView) findViewById(R.id.grid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                JSONObject offer = offers.getJSONObject(i);
                if(!offer.isNull("requested_babysitter")){
                    Intent intent = new Intent(MyOfferActivity.this,PrivateOfferParentActivity.class);
                    intent.putExtra("id",offer.getString("id"));
                    startActivity(intent);
                    return;
                    }


                    if(offer.getString("status").equals("scheduled")){
                        Intent intent = new Intent(MyOfferActivity.this,ScheduledOfferParentActivity.class);
                        String ff = offers.getJSONObject(i).getString("id");
                        intent.putExtra("id",ff);

                        startActivity(intent);
                        return;
                    }


           if(offer.getString("status").equals("pending")){
              Intent intent = new Intent(MyOfferActivity.this,RequestsActivity.class);


                  String ff = offers.getJSONObject(i).getString("id");
                  String description =offers.getJSONObject(i).getString("description");
                   String date =offers.getJSONObject(i).getString("start");
                 int nbr =offers.getJSONObject(i).getInt("requests");
                intent.putExtra("id",ff);
              intent.putExtra("description",description);
              intent.putExtra("date",date);
                 intent.putExtra("requests",nbr);
    startActivity(intent);
}



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        getOffers("4");






    }

    JSONArray offers;

    private void getOffers(final String id_user) {




        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getUserOffers";
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

                        offers = jsonObject.getJSONArray("offers");


                        OfferAdapter adapter = new OfferAdapter(offers);

                        grid.setAdapter(adapter);



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
                headers.put("id_user",id_user);
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