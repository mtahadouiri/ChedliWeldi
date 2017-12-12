package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.activities.FullScreenImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import carsapp.douirimohamedtaha.com.chedliweldi.R;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class PhotosFragment extends Fragment implements ImageGalleryAdapter.ImageThumbnailLoader, FullScreenImageGalleryAdapter.FullScreenImageLoader {
    ImageGalleryFragment fragment;
    LinearLayoutCompat linear ;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v= inflater.inflate(R.layout.photos, container, false);


             v.setId(View.generateViewId());


        ArrayList<String> i = new ArrayList();
        i.add("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
        i.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");
        i.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, i);

        ImageGalleryActivity.setImageThumbnailLoader(this);

        ImageGalleryFragment.setImageThumbnailLoader(this);

        FullScreenImageGalleryActivity.setFullScreenImageLoader(this);
        fragment = new ImageGalleryFragment();
        fragment.setArguments(bundle);

     getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.photosLayoute, fragment, "wcc")
                .commit();

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

    LinearLayout tmp;
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
        if(tmp==null){
            String i= fragment.getTag();
            tmp = (LinearLayout) fragment.getView();
            tmp.setBackgroundColor(Color.WHITE);
            AppBarLayout d  = (AppBarLayout) tmp.getChildAt(0);
            d.setExpanded(false, false);
            d.setVisibility(View.GONE);

            Log.i("","");
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);


    }
}
