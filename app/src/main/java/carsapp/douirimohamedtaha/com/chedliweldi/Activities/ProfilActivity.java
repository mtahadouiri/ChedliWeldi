package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
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
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfilActivity extends AppCompatActivity {

   public static  JSONObject user;

   ImageView profileImage;
   TextView fullName;
   MaterialRatingBar rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

       // makeJsonObjectRequest();
      //  signUp("salima@gmail.com","sdsdf",1254259,"salima","reguez","adress","23-03-1998","1");


        setContentView(R.layout.profil);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        fullName=(TextView) findViewById(R.id.txtFullName);
        rate =(MaterialRatingBar) findViewById(R.id.rate);
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
        window.setAttributes(params);
    }




}
