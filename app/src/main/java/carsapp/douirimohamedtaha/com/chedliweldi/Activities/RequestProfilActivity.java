package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.TabsFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RequestProfilActivity extends AppCompatActivity {

   public static  JSONObject user;

   ImageView profileImage;
   TextView fullName;
   MaterialRatingBar rate;
   Button accept ;
   Button refuse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

       // makeJsonObjectRequest();
      //  signUp("salima@gmail.com","sdsdf",1254259,"salima","reguez","adress","23-03-1998","1");


        setContentView(R.layout.profil_request_temp);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        fullName=(TextView) findViewById(R.id.txtFullName);
        rate =(MaterialRatingBar) findViewById(R.id.rate);
        accept =(Button) findViewById(R.id.btnAccept);
        refuse=(Button) findViewById(R.id.btnRefuse);


        if(user !=null){


            try {
                fullName.setText(user.getString("firstName")+" "+user.getString("lastName"));
                Glide.with(this).load(AppController.IMAGE_SERVER_ADRESS+user.getString("photo")).transform(new AppController.CircleTransform(this)).into(profileImage);

                rate.setRating((float) user.getDouble("rate"));
            } catch (JSONException e) {

            }

        }



        Intent i = getIntent();
       // user= (JSONObject) i.getSerializableExtra("user");

        this.setFinishOnTouchOutside(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TabsFragment()).commit();
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    respondToRequest("accepted",user.getString("id_request"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }






    @Override
    public void onResume() {
        super.onResume();

        Window window = getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 1100;
        params.height=1500 ;

        window.setAttributes(params);
    }


    private void respondToRequest(final String respond , final String idRequest) {

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


                        finish();

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
                headers.put("id_request",idRequest);
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
