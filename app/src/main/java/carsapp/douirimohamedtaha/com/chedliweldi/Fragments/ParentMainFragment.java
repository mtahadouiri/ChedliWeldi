package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.ProfilActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.ParentPager;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.ProfilePager;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class ParentMainFragment extends Fragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener  {
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
       View v= inflater.inflate(R.layout.parent_tabs, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Offers"));
       tabLayout.addTab(tabLayout.newTab().setText("MyOffer"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);

        //Creating our pager adapter

        ParentPager adapter = new ParentPager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);
        return  v;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabLayout.getTabAt(position).select();
     //  PhotosFragment.removeAppBar();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
