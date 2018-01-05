package com.esprit.chedliweldi.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Fragments.TabsFragment;
import com.esprit.chedliweldi.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfilActivity extends AppCompatActivity {

    public static JSONObject user;

    ImageView profileImage;
    TextView fullName;
    MaterialRatingBar rate;
    ImageView callBtn;
    ImageView makeRequestBtn;
    ImageView sendMessage;
    Button accept;
    Button refuse;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static Babysitter babysitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // makeJsonObjectRequest();
        //  signUp("salima@gmail.com","sdsdf",1254259,"salima","reguez","adress","23-03-1998","1");

        Bundle extras = getIntent().getExtras();
        babysitter=extras.getParcelable("user");
        setContentView(R.layout.profil);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        fullName = (TextView) findViewById(R.id.txtFullName);
        rate = (MaterialRatingBar) findViewById(R.id.rate);

        callBtn = (ImageView) findViewById(R.id.callBtn);
        makeRequestBtn = (ImageView) findViewById(R.id.makeJobRequest);
        sendMessage=(ImageView)findViewById(R.id.msgBtn);

        if (babysitter != null ) {


            try {
                //fullName.setText(user.getString("firstName") + " " + user.getString("lastName"));
                fullName.setText(babysitter.getFirstName()+ " " + babysitter.getLastName());
             //   Glide.with(this).load(AppController.IMAGE_SERVER_ADRESS + user.getString("photo")).transform(new AppController.CircleTransform(this)).into(profileImage);
                Glide.with(this).load(babysitter.getImgURL()).transform(new AppController.CircleTransform(this)).into(profileImage);
               // rate.setRating((float) user.getDouble("rate"));
                rate.setRating((float) 4);
                callBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //callBabysitter(user.getString("phoneNumber"));
                            callBabysitter(babysitter.getPhone());
                       // } catch (JSONException e) {
                        } catch (Exception e) {

                        }
                    }
                });
                sendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(ProfilActivity.this,ChatRoom.class);
                        i.putExtra("fullName",babysitter.getFirstName()+" "+babysitter.getLastName());
                        i.putExtra("id",babysitter.getId());
                        startActivity(i);
                    }
                });
                makeRequestBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            SendJobRequest(user.getString("id"));
                        } catch (JSONException e) {

                        }
                    }
                });
            //} catch (JSONException e) {
            } catch (Exception e) {

            }

        }
        else if (user!=null){
            try {
                fullName.setText(user.getString("firstName") + " " + user.getString("lastName"));
                //fullName.setText(babysitter.getFirstName()+ " " + babysitter.getLastName());
                Glide.with(this).load(user.getString("photo")).transform(new AppController.CircleTransform(this)).into(profileImage);
                //Glide.with(this).load(AppController.IMAGE_SERVER_ADRESS + babysitter.getImgURL()).transform(new AppController.CircleTransform(this)).into(profileImage);

                rate.setRating((float) user.getDouble("rate"));
                //rate.setRating((float) 4);
                callBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            callBabysitter(user.getString("phoneNumber"));
                           // callBabysitter(babysitter.getPhone());
                             } catch (JSONException e) {
                        //} catch (Exception e) {

                        }
                    }
                });
                sendMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(ProfilActivity.this,ChatRoom.class);
                        try {
                            i.putExtra("fullName",user.getString("firstname")+" "+user.getString("lastname"));
                        } catch (JSONException e) {

                        }
                        try {
                            i.putExtra("id",user.getString("id"));
                        } catch (JSONException e) {

                        }
                        startActivity(i);
                    }
                });
                makeRequestBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            SendJobRequest(user.getString("id"));
                        } catch (JSONException e) {

                        }
                    }
                });
                } catch (JSONException e) {
            //} catch (Exception e) {

            }
        }


        Intent i = getIntent();
        // user= (JSONObject) i.getSerializableExtra("user");

        this.setFinishOnTouchOutside(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TabsFragment()).commit();

    }

    private void callBabysitter(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_CODE);
            return;
        }
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void finish() {
        super.finish();
        user=null;
    }

    private void respondToRequest(final String respond, final String idRequest) {

        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS + "respondRequest";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d = jsonObject.getBoolean("error");


                    if (d) {
                        Log.i("etat", "failed");
                    } else {


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
                Log.d("", "" + error.getMessage() + "," + error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("respond", respond);
                headers.put("id_request", idRequest);
                //   headers.put("id_user",LoginActivity.connectedUser);
                //  headers.put("abc", "value");
                return headers;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }

    public void SendJobRequest(String bbId){
        Intent i = new Intent(ProfilActivity.this, AddJob.class);
        i.putExtra("bbid",bbId);
        startActivity(i);
    }
}
