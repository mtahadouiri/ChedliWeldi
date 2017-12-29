package com.esprit.chedliweldi.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.esprit.chedliweldi.Activities.Home;
import com.esprit.chedliweldi.Activities.MainActivity;
import com.esprit.chedliweldi.Activities.MyOfferActivity;
import com.esprit.chedliweldi.Activities.OfferListActivity;
import com.esprit.chedliweldi.Activities.ParentMainActivity;
import com.esprit.chedliweldi.Activities.SignUpActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.BabySittersJSONParser;

public class Login extends Fragment {

    public static final String PREFS_NAME = "user";
    @Bind(R.id.btnNext)
    Button btnSignIn ;
    @Bind(R.id.etEmail)
    EditText email ;
    @Bind(R.id.etPassword)
    EditText password ;
    @Bind(R.id.txtSignUp)
    TextView txtSignUp ;
    public static  String connectedUser="4";
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private String TAG = "LoginFB";
    private String userId;
    private URL profilePicture;
    private String firstName;
    private String lastName;
    private String emailTxT;
    private String birthday;
    private String gender;


    //public static String type="Babysitter";
    public static String type="Parent";

    public void validateLogin(String email ,String password){




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.login, container, false);
        ButterKnife.bind(this,v);
        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton)v.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile"));
            }
        });
        loginButton.setReadPermissions("email");
        // If using in a fragment
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            userId = object.getString("id");
                            try {
                                profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                            } catch (MalformedURLException e) {
                                Log.e(TAG,e.toString());
                            }
                            if(object.has("first_name"))
                                firstName = object.getString("first_name");
                            if(object.has("last_name"))
                                lastName = object.getString("last_name");
                            if (object.has("email"))
                                emailTxT = object.getString("email");
                            if (object.has("birthday"))
                                birthday = object.getString("birthday");
                            if (object.has("gender"))
                                gender = object.getString("gender");
                            // We need an Editor object to make preference changes.
                            // All objects are from android.context.Context
                            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("name", firstName);
                            editor.putString("lastname", lastName);
                            editor.putString("email", emailTxT);
                            editor.putString("birthday", birthday);
                            editor.putString("imageUrl", profilePicture.toString());
                            // Commit the edits!
                            editor.commit();

                            uploadFbUser(firstName,lastName,emailTxT,profilePicture);

                            Intent main = new Intent(getActivity(),Home.class);
                            main.putExtra("name",firstName);
                            main.putExtra("lastname",lastName);
                            main.putExtra("email",emailTxT);
                            main.putExtra("imageUrl",profilePicture.toString());
                            startActivity(main);
                            //finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                //Here we put the requested fields to be returned from the JSONObject
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("FbException",exception.toString());
            }
        });
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



        java.util.Map<String, String> params = new HashMap<String, String>();
        params.put("email", "sdfsd");
        params.put("password", "test2");
        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"login";
        StringRequest sr = new StringRequest(Request.Method.POST, url , response -> {


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
                    else{
                        Log.i("etat","success");

                        connectedUser=jsonObject.getString("id");
                        type= jsonObject.getString("type");
                        if(type.equals("Babysitter")){
                            showListoffers();
                          //  showParentMain();
                        }
                        else {
                    getBabysiiters();
                        }
                       // showMyoffer();


                    }
                   // showMyoffer();


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }, error -> {
            VolleyLog.d("", "Error: " + error.getMessage());
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

    public void showParentMain(){
        Intent i = new Intent(getActivity(),ParentMainActivity.class);
        startActivity(i);
    }

    private void signUp(String email , String password,int phoneNumber,String firstName,String lastName ,String adress ,String birthDate,String type ) {



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
        String url =  AppController.TAHA_ADRESS+"getBabysitters";
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Check if already logged in
        boolean loggedIn = AccessToken.getCurrentAccessToken()!=null;
        if (loggedIn){

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {


                    try {
                        userId = object.getString("id");
                        try {
                            profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
                        } catch (MalformedURLException e) {
                            Log.e(TAG,e.toString());
                        }
                        if(object.has("first_name"))
                            firstName = object.getString("first_name");
                        if(object.has("last_name"))
                            lastName = object.getString("last_name");
                        if (object.has("email"))
                            emailTxT = object.getString("email");
                        if (object.has("birthday"))
                            birthday = object.getString("birthday");
                        if (object.has("gender"))
                            gender = object.getString("gender");

                        checkFbuser(emailTxT);
/*
                        Intent main = new Intent(getActivity(),Home.class);
                        main.putExtra("name",firstName);
                        main.putExtra("lastname",lastName);
                        main.putExtra("email",emailTxT);
                        main.putExtra("imageUrl",profilePicture.toString());
                        startActivity(main);*/

                        //finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });



            //Here we put the requested fields to be returned from the JSONObject
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private void checkFbuser(String emailTxT) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = AppController.TAHA_ADRESS + "getUserByEmail?email="+emailTxT;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // response
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("status").equals("found")){
                            // We need an Editor object to make preference changes.
                            // All objects are from android.context.Context
                            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("name", jsonObject.getString("firstname"));
                            editor.putString("id", jsonObject.getString("id"));
                            editor.putString("lastname", jsonObject.getString("lastname"));
                            editor.putString("email", jsonObject.getString("email"));
                            editor.putString("birthday", jsonObject.getString("birthdate"));
                            editor.putString("altitude", jsonObject.getString("altitude"));
                            editor.putString("longitude", jsonObject.getString("longitude"));
                            editor.putString("phoneNumber", jsonObject.getString("phoneNumber"));
                            editor.putString("imageUrl", profilePicture.toString());
                            // Commit the edits!
                            editor.commit();
                            Intent main = new Intent(getActivity(),Home.class);
                            startActivity(main);
                        }else {
                            uploadFbUser(firstName,lastName,emailTxT,profilePicture);
                        }
                    } catch (JSONException e) {

                        Log.e("JSON",e.toString());
                    }

                },
                error -> {
                    // error
                    Log.d("Error.checkFbuser", error.toString());
                }
        );
        queue.add(postRequest);
    }

    private void uploadFbUser(String firstName, String lastName, String emailTxT, URL profilePicture) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = AppController.TAHA_ADRESS + "fbuser";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Fragment parentProfile = new ParentProfil();

                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,parentProfile).commit();
                },
                error -> {
                    // error
                }
        ) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put("name", "" + firstName);
                params.put("lastname", lastName);
                params.put("email", emailTxT);
               // params.put("birthday", birthday);
                params.put("imageUrl", profilePicture.toString());
                Log.d("Params", params.values().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //accessTokenTracker.stopTracking();
    }
}
