package com.esprit.chedliweldi.Utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.esprit.chedliweldi.Activities.BabySitterMainActivity;
import com.esprit.chedliweldi.Activities.CalendarActivity;
import com.esprit.chedliweldi.Activities.MainActivity;
import com.esprit.chedliweldi.Activities.MyOfferActivity;
import com.esprit.chedliweldi.Activities.OnGoingOfferActivity;
import com.esprit.chedliweldi.Activities.SettingActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Fragments.Login;
import com.esprit.chedliweldi.R;

/**
 * Created by oussama_2 on 12/29/2017.
 */

public  class DrawerInitializer implements NavigationView.OnNavigationItemSelectedListener{


        public static void initView(AppCompatActivity activity) {

            Toolbar toolbar = (Toolbar)   activity.findViewById(R.id.toolbar_main);
             activity.setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) activity. findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            //drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)activity);
            navigationView.setItemIconTintList(null);

            View headerView = navigationView.getHeaderView(0);
            LinearLayout nav_header = (LinearLayout) headerView.findViewById(R.id.nav_header);
            nav_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AppController.getContext(), MainActivity.class);
                    activity.startActivity(intent);
                    DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                }
            });



            Menu m = navigationView.getMenu();
            //   MenuItem foo_menu_item=m.add("foo");

            MenuItem myOffers = (MenuItem) m.findItem(R.id.nav_my_offers);


            myOffers.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    Intent i = new Intent(AppController.getContext(),MyOfferActivity.class);
                    activity.startActivity(i);


                    return false;
                }
            });

            MenuItem goingOffers = (MenuItem) m.findItem(R.id.on_going_offers);

            MenuItem calendar = (MenuItem) m.findItem(R.id.calendar);



            goingOffers.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    Intent i = new Intent(AppController.getContext(),OnGoingOfferActivity.class);
                    activity.startActivity(i);
                    return false;
                }

            });


            calendar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    Intent i = new Intent(AppController.getContext(),CalendarActivity.class);
                    activity.startActivity(i);
                    return false;
                }

            });


            if(Login.type.equals("Babysitter")){
                myOffers.setVisible(false);
                goingOffers.setVisible(true);
                calendar.setVisible(true);
            }
            else{
                calendar.setVisible(false);
                goingOffers.setVisible(false);
            }


            MenuItem settings = (MenuItem) m.findItem(R.id.nav_settings);


            settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    Intent i = new Intent(AppController.getContext(),SettingActivity.class);
                    activity.startActivity(i);
                    return false;
                }
            });









        }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
