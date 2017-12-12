package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

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

import java.util.HashMap;
import java.util.Map;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RecycleItemClickListener;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RequestRecyclerViewAdapter;

public class RequestsActivity extends AppCompatActivity {

    RecyclerView offers ;
   RequestRecyclerViewAdapter adapter ;
 LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

       ;

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
            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));


        Intent  k = getIntent();

        String id =k.getStringExtra("id");

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
                        offers.addItemDecoration(mDividerItemDecoration);
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




}
