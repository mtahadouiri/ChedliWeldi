package com.esprit.chedliweldi.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import butterknife.Bind;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Fragments.Login;
import com.esprit.chedliweldi.Fragments.ParentProfil;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.BabySittersJSONParser;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.esprit.chedliweldi.Fragments.Login.PREFS_NAME;

public class LoginActivity extends AppCompatActivity implements ParentProfil.OnFragmentInteractionListener{

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()

                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String id =settings.getString("id",null);
        String type =settings.getString("id",null);
        if(id==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Login()).commit();
        }
        else{
            Login.connectedUser=id;
            Login.type=type;
            if(type.equals("Babysitter")){
                showListoffers();
            }
            else{
                getBabysiiters();
            }

        }


    }


    private void getBabysiiters() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url =  AppController.TAHA_ADRESS+"getBabysitters";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        MainActivity.bbySitters= BabySittersJSONParser.parseData(response);
                        Intent i = new Intent(LoginActivity.this, Home.class);
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


    void showListoffers(){
        Intent i = new Intent(this,BabySitterMainActivity.class);
        startActivity(i);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
