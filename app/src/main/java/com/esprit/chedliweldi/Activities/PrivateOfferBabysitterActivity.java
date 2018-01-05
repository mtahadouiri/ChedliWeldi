package com.esprit.chedliweldi.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Fragments.Login;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.adapters.DisplayTaskListAdapter;
import com.esprit.chedliweldi.adapters.PrivateOfferRecycleViewAdapter;
import com.esprit.chedliweldi.adapters.RecycleItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrivateOfferBabysitterActivity extends AppCompatActivity {

    RecyclerView recyclerOffers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_offer);
        recyclerOffers = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerOffers.getContext(),
                layoutManager.getOrientation());
        recyclerOffers.addItemDecoration(dividerItemDecoration);
        recyclerOffers.setLayoutManager(layoutManager);
        recyclerOffers.addOnItemTouchListener(new RecycleItemClickListener(this, recyclerOffers, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                try {
                    showRequestDialog(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));

        getOffers(Login.connectedUser);
    }




    JSONArray offers;
    PrivateOfferRecycleViewAdapter adapter;
    private void getOffers(final String id_user) {

        String url = AppController.SERVER_ADRESS+"getPrivateOffers";
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

                         adapter = new PrivateOfferRecycleViewAdapter(AppController.getContext(), offers);
                        recyclerOffers.setAdapter(adapter);


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
                headers.put("user_id",id_user);
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

    private void acceptOffer(final String idOffer,int position) {

        String url = AppController.SERVER_ADRESS+"acceptPrivateOffer";
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
                             //success!!!
                     n.dismiss();
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
                headers.put("offer_id",idOffer);
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

    private void refuseOffer(final String idOffer,int position) {

        String url = AppController.SERVER_ADRESS+"refusePrivateOffer";
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
                        //success!!!
                    //    offers.remove(position);
                     //   adapter.notifyItemRemoved(position);
                        n.dismiss();
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
                headers.put("offer_id",idOffer);
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

    MaterialDialog n;
   private void showRequestDialog(int position) throws JSONException {


      final JSONObject   offer = offers.getJSONObject(position);

        Log.i(" dfs","sdf");
        boolean wrapInScrollView = true;
        n =  new MaterialDialog.Builder(PrivateOfferBabysitterActivity.this
        )

                .customView(R.layout.private_offer_request, false)

                .show();

        View v = n.getView();
        final Button accept = (Button) v.findViewById(R.id.btnAccept);

        final ImageView profilImage = (ImageView) v.findViewById(R.id.profileImage);



        final TextView txtName = (TextView) v.findViewById(R.id.txtUserName);
        final TextView description = (TextView) v.findViewById(R.id.txtDescription);
        final TextView txtDate = (TextView) v.findViewById(R.id.txtDate);
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv);
       LinearLayoutManager layoutManager=new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);


        try {
            Glide.with(PrivateOfferBabysitterActivity.this).load(AppController.IMAGE_SERVER_ADRESS+offer.getString("photo")).transform(new AppController.CircleTransform(PrivateOfferBabysitterActivity.this)).into(profilImage);
           // txtName.setText(requests.getJSONObject(position).getString("firstName")+" " +requests.getJSONObject(position).getString("lastName") );
            //double ratee=requests.getJSONObject(position).getDouble("rate");
            //rate.setRating((float) ratee );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    acceptOffer(offer.getString("id"),position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Button refuse = (Button) v.findViewById(R.id.btnRefuse);
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    refuseOffer(offer.getString("id"),position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        getTasks(offer.getString("id"),recyclerView);






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
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                java.util.Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_offer",idUser);
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
