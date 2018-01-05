package com.esprit.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.esprit.chedliweldi.Activities.FullScreenImageGalleryActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.DisplayUtility;
import com.esprit.chedliweldi.Utils.ImageProcessClass;
import com.esprit.chedliweldi.adapters.ImageGalleryAdapter;
import com.esprit.chedliweldi.adapters.RecycleItemClickListener;
import dmax.dialog.SpotsDialog;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class ManagePhotoFragment extends Fragment {

Toolbar toolbar;
    RecyclerView recyclerView;
    ImageGalleryAdapter imageGalleryAdapter;
    ArrayList<String> photos = new ArrayList<>();
    ArrayList<String>idPhotos= new ArrayList<String>();
    SpotsDialog dialog ;
    FloatingActionButton add ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.manage_photo, container, false);

         dialog = new SpotsDialog(getContext());
     //    dialog.setMessage("uploading");
        initToolbar(v);
        add =(FloatingActionButton) v.findViewById(R.id.fab);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent chooseIntent = new Intent(Intent.EXTRA_ALLOW_MULTIPLE, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //chooseIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(intent, 2);
            }
        });

        setUpRecyclerView();
        getPhotos(Login.connectedUser);



        return v;
    }

    Bitmap bitmap;
    List<Bitmap> images;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){
            ClipData d= data.getClipData();

            int numberOfImages= d.getItemCount();
            for (int i = 0; i < numberOfImages; i++) {
                try {
                    images = new ArrayList<>();

                    Uri u = data.getClipData().getItemAt(i).getUri();
                    Bitmap  bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), u);
                    images.add(bitmap);
                    dialog.show();
                    for ( Bitmap image:images ) {
                        ImageUploadToServerFunction(image,Login.connectedUser);
                    }



                    Log.i("","");

                } catch (Exception e) {

                }
            }

        }


    }

    private void setUpRecyclerView() {
        int numOfColumns;
        if (DisplayUtility.isInLandscapeMode(getActivity())) {
            numOfColumns = 4;
        } else {
            numOfColumns = 3;
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numOfColumns));
        imageGalleryAdapter = new ImageGalleryAdapter(getContext(), photos);
        recyclerView.setAdapter(imageGalleryAdapter);
        recyclerView.addOnItemTouchListener(new RecycleItemClickListener(getContext(),recyclerView, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                Intent i = new Intent(getActivity(), FullScreenImageGalleryActivity.class);
              //  i.putStringArrayListExtra(FullScreenImageGalleryActivity.KEY_IMAGES,photos);

                      i.putStringArrayListExtra(FullScreenImageGalleryActivity.KEY_IMAGES,photos);
                      i.putStringArrayListExtra("positions",idPhotos);
                i.putExtra("enable",true);
                i.putExtra(FullScreenImageGalleryActivity.KEY_POSITION,position);

                startActivity(i);



            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));




    }

          // imageGalleryAdapter.notifyDataSetChanged();



    private  void refreshRecyleView(List<String>photos){
this.photos.clear();
this.photos.addAll(photos);
imageGalleryAdapter.notifyDataSetChanged();
    }


    void initToolbar(View v){

        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();



            }
        });
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("photos");
        }

    }




    public void ImageUploadToServerFunction(Bitmap bitmap,String id){

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
               // Bitmap b = Bitmap.createScaledBitmap(bitmap, 80, 80, false);

                //profileImg.setImageDrawable(RoundedBitmapDrawableUtility.getCircleBitmapDrawable(getActivity(),b));
                //loading.hide();
                //mask.setVisibility(View.INVISIBLE);
                count++;
                  if(count==images.size()){
                      dialog.dismiss();
                  }

                String m =string1;
                // Printing uploading success message coming from server on android app.
                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();

                // Setting image as transparent after done uploading.
//                imageView.setImageResource(android.R.color.transparent);


            }

            int count=0;

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

             //   HashMapParams.put(ImageName, GetImageNameEditText);
                HashMapParams.put("user_id",id);

                HashMapParams.put("image_path", ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(AppController.SERVER_ADRESS+"uploadPhotos", HashMapParams);



                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }


    @Override
    public void onResume() {
        super.onResume();
        getPhotos(Login.connectedUser);
    }

    private void getPhotos(final String idUser) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"photos";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){


                    }
                    else{
                        List<String> photos = new ArrayList<>();
                        JSONArray skillsJson = jsonObject.getJSONArray("photos");
                        for (  int i =0 ;i<skillsJson.length();i++){
                            JSONObject skill = skillsJson.getJSONObject(i);

                           idPhotos.add(skill.getString("id"));
                            photos.add(AppController.IMAGE_SERVER_ADRESS+skill.getString("photo"));
                        }

                        refreshRecyleView(photos);


                       // tags.setTags(skills);

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
                headers.put("user_id",idUser);
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
