package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.activities.ImageGalleryActivity;


import java.util.ArrayList;

import carsapp.douirimohamedtaha.com.chedliweldi.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class AboutFragment extends Fragment {
TagGroup tags;
    ImageGalleryFragment fragment;
    LinearLayout t;
    //Overriden method onCreateView


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.about, container, false);

        tags = (TagGroup) v.findViewById(R.id.tags);
        tags.setTags(new String[]{"good listener","friendly","good looking","amazing dancer","teach music"});

        ArrayList<String> i = new ArrayList();
        i.add("https://cdn.pixabay.com/photo/2016/06/18/17/42/image-1465348_960_720.jpg");
        i.add("https://cdn.pixabay.com/photo/2016/11/03/04/02/ancient-1793421_960_720.jpg");

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ImageGalleryActivity.KEY_IMAGES, i);

        











        return v;
    }
LinearLayout tmp;
    @Override
    public void onResume() {
        super.onResume();

        
    }


}
