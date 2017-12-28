package com.esprit.chedliweldi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.adapters.RecycleItemClickListener;
import com.esprit.chedliweldi.adapters.RequestRecyclerViewAdapter;

public class RequestsActivity extends AppCompatActivity {

    RecyclerView offers ;
   RequestRecyclerViewAdapter adapter ;
 LinearLayoutManager layoutManager;
 TextView description ;
    TextView date ;
    Button btnRequests ;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent  k = getIntent();

        setContentView(R.layout.activity_request);
        description = (TextView) findViewById(R.id.txtDescription);
        date = (TextView) findViewById(R.id.txtDate);
        btnRequests = (Button) findViewById(R.id.btnRequests);


        String description = k.getStringExtra("description");

      this.description.setText(description);
        id =k.getStringExtra("id");
        int nbrRequests= k.getIntExtra("requests",0);
        btnRequests.setText(nbrRequests+" requests");

        String date =k.getStringExtra("date");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            Date d = dateFormatter.parse(date);
            dateFormatter.applyPattern("E M 'at' h:m a");
            date=dateFormatter.format(d);
            this.date.setText(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        offers = (RecyclerView) findViewById(R.id.recycler_view);



        layoutManager=new LinearLayoutManager(this);
        offers.setLayoutManager(layoutManager);

        offers.addOnItemTouchListener(new RecycleItemClickListener(this, offers, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                Log.i(" dfs","sdf");
                boolean wrapInScrollView = true;
                final MaterialDialog n =  new MaterialDialog.Builder(RequestsActivity.this
                )

                        .customView(R.layout.profil_request, false)

                        .show();

                View v = n.getView();
                final Button accept = (Button) v.findViewById(R.id.btnAccept);
                final Button profil = (Button) v.findViewById(R.id.btnProfil);
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
                profil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(RequestsActivity.this,ProfilActivity.class);
                        //   JSONObject jsonObject  =new JSONObject();
                        // i.putExtra("json",jsonObject);
                        try {
                            ProfilActivity.user=requests.getJSONObject(position);
                            startActivity(i);
                        } catch (JSONException e) {

                        }
                    }
                });



                /*

*/
            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));





          getRequests(id);




    }
    JSONArray requests;
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
                      //  offers.addItemDecoration(mDividerItemDecoration);
                        adapter = new RequestRecyclerViewAdapter(RequestsActivity.this, requests);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_offer",id_offer);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("respond",respond);
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

    @Override
    protected void onResume() {
        super.onResume();
        getRequests(id);
    }
}
