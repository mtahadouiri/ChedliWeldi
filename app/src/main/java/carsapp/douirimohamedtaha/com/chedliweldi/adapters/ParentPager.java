package carsapp.douirimohamedtaha.com.chedliweldi.adapters;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.MyOfferActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.AboutFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.MyOffersFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.OffersFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.PhotosFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.ReviewFragment;


/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class ParentPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ParentPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
               OffersFragment tab1 =  new OffersFragment();
                return tab1;
            case 1:
               MyOffersFragment tab2 =  new  MyOffersFragment();
                return tab2;
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