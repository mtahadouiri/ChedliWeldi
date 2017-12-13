package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.ReviewRecycleViewAdapter;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class PhotosFragment extends Fragment implements ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader {
   public static ImageGalleryFragment fragment;
    LinearLayoutCompat linear ;


    public static PhotosFragment newInstance(String idUser) {
        PhotosFragment fragment = new PhotosFragment();
        Bundle args = new Bundle();
        args.putString("id", idUser);

        fragment.setArguments(args);
        return fragment;
    }

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String id = getArguments().getString("id");


        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v= inflater.inflate(R.layout.photos, container, false);






        ImageGalleryActivity.setImageThumbnailLoader(this);

        ImageGalleryFragment.setImageThumbnailLoader(this);
        FullScreenImageGalleryActivity.setFullScreenImageLoader(PhotosFragment.this);




      getPhotos(id);

       // getActivity().  getSupportFragmentManager().executePendingTransactions();
        return  v;
    }

    @Override
    public void loadFullScreenImage(final ImageView iv, String imageUrl, int width, final LinearLayout bglinearLayout) {



        Picasso.with(iv.getContext())
                .load(imageUrl)
                .resize(width, 0)
                .into(iv);
        //  Glide.with(iv.getContext()).load(imageUrl).into(iv);

    }

    static LinearLayout tmp;
    @Override
    public void loadImageThumbnail(ImageView iv, String imageUrl, int dimension) {


        Picasso.with(iv.getContext())
                .load(imageUrl)
                .resize(dimension, dimension)
                .centerCrop()
                .into(iv); Glide.with(iv.getContext()).load(imageUrl).into(iv); Glide.with(iv.getContext()).load(imageUrl).into(iv);






    }

    @Override
    public void onResume() {

        super.onResume();



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


                        ArrayList<String> i = new ArrayList();
                        i.add("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
                        i.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");
                        i.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, i);
                        fragment = new ImageGalleryFragment();
                        fragment.setArguments(bundle);

                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.photosL, fragment, "wcc")
                                .commit();
                    }
                    else{
                       ArrayList<String> photos = new ArrayList<>();
                       JSONArray photosJson = jsonObject.getJSONArray("photos");
                       for (  int i =0 ;i<photosJson.length();i++){
                         JSONObject photo = photosJson.getJSONObject(i);


                           photos.add(AppController.IMAGE_SERVER_ADRESS+photo.getString("photo"));
                       }
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, photos);
                        fragment = new ImageGalleryFragment();
                        fragment.setArguments(bundle);


                        getChildFragmentManager()
                                .beginTransaction()
                                .replace(R.id.photosL, fragment, "wcc")
                                .commit();

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

        ;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
/*

        if(tmp==null){

            View m = super.getView();
            tmp = (LinearLayout) m;


            tmp.setBackgroundColor(Color.BLACK);

            LinearLayout tmpe = (LinearLayout) tmp.getChildAt(1);
            Log.i("","");
            AppBarLayout dd  = (AppBarLayout) tmpe.getChildAt(0);
            Log.i("","");

            AppBarLayout dd  = (AppBarLayout) tmp.getChildAt(0);
            dd.setExpanded(false, false);
            dd.setVisibility(View.GONE);

            Log.i("","");
        }


        }
        */
    }


    public static void removeAppBar(){
        if(tmp==null){


            tmp = (LinearLayout) fragment.getView();





            AppBarLayout dd  = (AppBarLayout) tmp.getChildAt(0);
            Log.i("","");


            dd.setExpanded(false, false);
            dd.setVisibility(View.GONE);

            Log.i("","");
        }
    }

    @Nullable
    @Override
    public View getView() {

        return super.getView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        tmp=null;
    }
}
