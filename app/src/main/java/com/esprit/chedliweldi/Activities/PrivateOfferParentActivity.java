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
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.DrawerInitializer;
import com.esprit.chedliweldi.adapters.RecycleItemClickListener;
import com.esprit.chedliweldi.adapters.RequestRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import mehdi.sakout.fancybuttons.FancyButton;

public class PrivateOfferParentActivity extends AppCompatActivity {


 LinearLayoutManager layoutManager;
 TextView description ;
    TextView date ;
    ImageView image;
    TextView fullName;
    FancyButton btnPublic ;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent  k = getIntent();
        id =k.getStringExtra("id");
        setContentView(R.layout.private_offer_parent);
        description = (TextView) findViewById(R.id.txtDescription);
        fullName = (TextView) findViewById(R.id.txtFullName);
        image = (ImageView) findViewById(R.id.profileImage);
        date = (TextView) findViewById(R.id.txtDate);
        btnPublic = (  FancyButton )findViewById(R.id.btn_public);
        btnPublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeOfferPublic(id);
            }
        });
        getOfferDetail(id);
    }



    private void getOfferDetail(final String idOffer) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getPrivateOffer";
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
    private void makeOfferPublic(final String idOffer) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"makeOfferPublic";
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

                        Intent i = new Intent(PrivateOfferParentActivity.this,RequestsActivity.class);
                        i.putExtra("id",id);
                        startActivity(i);




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
                headers.put("offer_id",idOffer);
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
