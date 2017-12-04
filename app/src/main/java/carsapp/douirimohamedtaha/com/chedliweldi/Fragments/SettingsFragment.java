package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;


import carsapp.douirimohamedtaha.com.chedliweldi.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class SettingsFragment extends Fragment {
TagGroup tags;
    ImageGalleryFragment fragment;
    LinearLayout t;
    //Overriden method onCreateView

Toolbar toolbar;
    RelativeLayout infoTab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        View v = inflater.inflate(R.layout.settings, container, false);

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



}
