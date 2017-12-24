package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.ChooseTypeFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.CreateProfilFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity {


    public  String email ;
    public   String password ;
    public  String adress;
    public  String firstName;
    public   String lastName;
    public   String phoneNumber;
    public String type ;
    public   int gender ;




    public void validateLogin(String email ,String password){




    }

    @Bind(R.id.Fragcontainer)
    FrameLayout layout ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()

                .setFontAttrId(R.attr.fontPath)
                .build()
        );



       // makeJsonObjectRequest();
      //  signUp("salima@gmail.com","sdsdf",1254259,"salima","reguez","adress","23-03-1998","1");
        setContentView(R.layout.sign_up);


        ButterKnife.bind(this);

       getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.Fragcontainer,new ChooseTypeFragment()).commit();

    //    getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.Fragcontainer,new CreateProfilFragment()).commit();






    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void signIn(){

    }

    private void login(final String email , final String password) {


    Log.e("sdf", "uploadUser:  near volley new request ");

        Map<String, String> params = new HashMap<String, String>();
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
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
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


    private void signUp(String email , String password,int phoneNumber,String firstName,String lastName ,String adress ,String birthDate,String type ) {


        Log.e("sdf", "uploadUser:  near volley new request ");

        final Map<String, String> params = new HashMap<String, String>();
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
            protected Map<String,String> getParams(){


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }






}
