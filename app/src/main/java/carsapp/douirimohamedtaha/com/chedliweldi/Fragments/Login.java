package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import butterknife.Bind;
import butterknife.ButterKnife;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.Home;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.LoginActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.MainActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.MyOfferActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.OfferListActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.SignUpActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.Utils.BabySittersJSONParser;
import me.gujun.android.taggroup.TagGroup;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login extends Fragment {

    @Bind(R.id.btnNext)
    Button btnSignIn ;
    @Bind(R.id.etEmail)
    EditText email ;
    @Bind(R.id.etPassword)
    EditText password ;
    @Bind(R.id.txtSignUp)
    TextView txtSignUp ;
    public static  String connectedUser="4";
    public void validateLogin(String email ,String password){




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.login, container, false);
        ButterKnife.bind(this,v);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(email.getText().toString(),password.getText().toString());
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i  =new Intent(getActivity(),SignUpActivity.class)
                        ;
                startActivity(i);
            }
        });




        return v;
    }




    private void login(final String email , final String password) {


        Log.e("sdf", "uploadUser:  near volley new request ");

        java.util.Map<String, String> params = new HashMap<String, String>();
        params.put("email", "sdfsd");
        params.put("password", "test2");
        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"login";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){

                        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                                .title("authentication")
                                .content("incorect email or password")
                                .positiveText("ok")
                                .show();


                    }
                    else{
                        Log.i("etat","success");
                        connectedUser=jsonObject.getString("id");
                        if(jsonObject.getString("type").equals("Babysitter")){
                            showListoffers();
                        }
                        else {
                    getBabysiiters();
                        }
                       // showMyoffer();


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
                params.put("email", email);
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

    void showMyoffer(){
        Intent i = new Intent(getActivity(),MyOfferActivity.class);
        startActivity(i);
    }

    void showListoffers(){
        Intent i = new Intent(getActivity(),OfferListActivity.class);
        startActivity(i);
    }

    private void signUp(String email , String password,int phoneNumber,String firstName,String lastName ,String adress ,String birthDate,String type ) {


        Log.e("sdf", "uploadUser:  near volley new request ");

        final java.util.Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("adress", adress);
        params.put("birthDate", birthDate);
        params.put("type", type);
        params.put("phoneNumber", String.valueOf(phoneNumber));
        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"register";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){
                        //oops
                    }
                    else{
                        String dd =jsonObject.getString("message");
                        String ddf =jsonObject.getString("message");

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


                return params;
            }

            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                java.util.Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);




    }




    private void getBabysiiters() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url =  AppController.TAHA_ADRESS+"getBabysitters.php";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        MainActivity.bbySitters= BabySittersJSONParser.parseData(response);
                        Intent i = new Intent(getContext(), Home.class);
                        startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",""+ error.getMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



}
