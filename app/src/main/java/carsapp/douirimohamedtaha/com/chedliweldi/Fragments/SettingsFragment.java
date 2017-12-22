package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.wang.avi.AVLoadingIndicatorView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.LoginActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.MainActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.SettingActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.Utils.ImageProcessClass;
import carsapp.douirimohamedtaha.com.chedliweldi.Utils.RoundedBitmapDrawableUtility;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RequestRecyclerViewAdapter;
import me.gujun.android.taggroup.TagGroup;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class SettingsFragment extends Fragment {
TagGroup tags;
    ImageGalleryFragment fragment;
    boolean check = true;
    Button choose;
    Button upload;
    Button SelectImageGallery, UploadImageServer;

    ImageView imageView;
    ImageView mask;
    Bitmap bitmap;
    EditText imageName;
    AVLoadingIndicatorView  loading ;



    String GetImageNameEditText="my_offer";

    String ImageName = "image_name" ;

    String ImagePath = "image_path" ;

   // String ServerUploadPath ="http://10.0.2.2/rest/upload.php" ;
    String ServerUploadPath =AppController.SERVER_ADRESS+"upload" ;


    LinearLayout t;
    //Overriden method onCreateView
ImageView profileImg;
TextView fullName;
RelativeLayout relativeLayout;
Toolbar toolbar;
    RelativeLayout infoTab;
    RelativeLayout passwordTab;
    RelativeLayout notificationTab;
    RelativeLayout managePhotosTab;

    RelativeLayout skillsTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.settings, container, false);
        getUserById(Login.connectedUser);
   loading = (AVLoadingIndicatorView) v.findViewById(R.id.loading);
   mask=(ImageView) v.findViewById(R.id.mask);
   fullName= (TextView) v.findViewById(R.id.textView7);

        loading.hide();

        profileImg =(ImageView) v.findViewById(R.id.imageView4);
        relativeLayout =(RelativeLayout) v.findViewById(R.id.relativeLayout);
        skillsTab=(RelativeLayout) v.findViewById(R.id.manageSkills);
        managePhotosTab=(RelativeLayout) v.findViewById(R.id.managePhotos);
        notificationTab=(RelativeLayout) v.findViewById(R.id.notification_bar);
        passwordTab=(RelativeLayout) v.findViewById(R.id.change_password_tab);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mask.setVisibility(View.VISIBLE);
                loading.show();
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);



                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
            }
        });


notificationTab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});



        skillsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,R.animator.enter_from_left,R.animator.exit_to_right)
                        .replace(R.id.frame, new EditSkillsFragment(), "").addToBackStack("dff")


                        .commit();
            }
        });


        passwordTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,R.animator.enter_from_left,R.animator.exit_to_right)
                        .replace(R.id.frame, new ChangePasswordFragment(), "").addToBackStack("dff")


                        .commit();
            }
        });


        infoTab =v. findViewById(R.id.infoSetting);
        infoTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,R.animator.enter_from_left,R.animator.exit_to_right)
                        .replace(R.id.frame, new InfoFragment(), "").addToBackStack("dff")


                        .commit();
            }
        });


        managePhotosTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,R.animator.enter_from_left,R.animator.exit_to_right)
                        .replace(R.id.frame, new ManagePhotoFragment(), "").addToBackStack("dff")


                        .commit();
            }
        });

       // Glide.with(getActivity()).load(AppController.IMAGE_SERVER_ADRESS+"man.png").transform(new AppController.CircleTransform(getActivity())).into(profileImg);

        /*
        choose = (Button) v.findViewById(R.id.btnchoose);


        upload = (Button) v.findViewById(R.id.btnupload);

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);


            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageUploadToServerFunction();
            }
        });

        /*

        infoTab =v. findViewById(R.id.infoSetting);
        infoTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.slide_in_left,R.animator.slide_in_right)
                        .replace(R.id.fragmentLayout, new InfoFragment(), "").addToBackStack("dff")


                        .commit();
            }
        });


        /*
      toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        */
        return v;
    }




    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();


       bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();








            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
            //    progressDialog.dismiss();
           Bitmap b = Bitmap.createScaledBitmap(bitmap, 80, 80, false);
                profileImg.setImageDrawable(RoundedBitmapDrawableUtility.getCircleBitmapDrawable(getActivity(),b));
                loading.hide();
                mask.setVisibility(View.INVISIBLE);

                String m =string1;
                // Printing uploading success message coming from server on android app.
                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
//                imageView.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

              ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ImageName, GetImageNameEditText);
                HashMapParams.put("user_id", Login.connectedUser);

                HashMapParams.put(ImagePath, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);



                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }



    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);



        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);


                    ImageUploadToServerFunction();





            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else{
            loading.hide();
            mask.setVisibility(View.INVISIBLE);
        }
    }


    private void getUserById(final String id) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getUserById";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    fullName.setText(jsonObject.getString("firstName")+" "+jsonObject.getString("lastName"));
                    Glide.with(getActivity()).load(AppController.IMAGE_SERVER_ADRESS+jsonObject.getString("photo")).transform(new AppController.CircleTransform(getActivity())).into(profileImg);


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
                headers.put("id_user",id);
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

