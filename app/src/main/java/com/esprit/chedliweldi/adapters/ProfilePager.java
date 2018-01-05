package com.esprit.chedliweldi.adapters;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.esprit.chedliweldi.Fragments.AboutFragment;
import com.esprit.chedliweldi.Fragments.PhotosFragment;
import com.esprit.chedliweldi.Fragments.ReviewFragment;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class ProfilePager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
String idUser;
String about;
JSONObject data;
    //Constructor to the class
    public ProfilePager(FragmentManager fm, int tabCount, JSONObject data) {

        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.data=data;

        try {
            this.about=data.getString("about");
            this.idUser=data.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                AboutFragment tab1 =  AboutFragment.newInstance(data);
                return tab1;
            case 1:
                PhotosFragment tab2 =  PhotosFragment.newInstance(idUser);
                return tab2;
            case 2:
                ReviewFragment tab3 = ReviewFragment.newInstance(idUser);
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}