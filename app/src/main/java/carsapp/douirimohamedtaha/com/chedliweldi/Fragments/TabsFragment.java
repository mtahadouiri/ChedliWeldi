package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import org.json.JSONException;
import org.json.JSONObject;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.ProfilActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.RequestProfilActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.ProfilePager;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class TabsFragment extends Fragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener  {
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
       View v= inflater.inflate(R.layout.tabs, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Resume"));
       tabLayout.addTab(tabLayout.newTab().setText("Photos"));
       tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) v.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        RequestProfilActivity profil = (RequestProfilActivity) getActivity();
        //Creating our pager adapter
        JSONObject d = profil.user;
        String id="4";
        String about ="What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s ";
        try {
          id =d.getString("id");
          about=d.getString("about");
        } catch (JSONException e) {

        }
        ProfilePager adapter = new ProfilePager(getActivity().getSupportFragmentManager(), tabLayout.getTabCount(),id,about);

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
       PhotosFragment.removeAppBar();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
