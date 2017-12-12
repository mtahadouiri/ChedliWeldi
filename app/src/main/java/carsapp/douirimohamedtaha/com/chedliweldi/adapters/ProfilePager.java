package carsapp.douirimohamedtaha.com.chedliweldi.adapters;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.AboutFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.PhotosFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.ReviewFragment;


/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class ProfilePager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public ProfilePager(FragmentManager fm, int tabCount) {
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
                AboutFragment tab1 = new  AboutFragment();
                return tab1;
            case 1:
                PhotosFragment tab2 = new PhotosFragment();
                return tab2;
            case 2:
                ReviewFragment tab3 = new ReviewFragment();
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