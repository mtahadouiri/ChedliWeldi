package com.esprit.chedliweldi.Utils;

import android.app.Activity;
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
import com.esprit.chedliweldi.Activities.Messages;
import com.esprit.chedliweldi.Activities.MyOfferActivity;
import com.esprit.chedliweldi.Activities.OnGoingOfferActivity;
import com.esprit.chedliweldi.Activities.SettingActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Message;
import com.esprit.chedliweldi.Fragments.Login;
import com.esprit.chedliweldi.R;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

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
//            navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)activity);
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
            MenuItem messages = (MenuItem) m.findItem(R.id.message);


            messages.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    Intent i = new Intent(AppController.getContext(),Messages.class);
                    activity.startActivity(i);


                    return false;
                }
            });


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



    public static void setUpBoomMenu(AppCompatActivity a) {
        BoomMenuButton bmb;
        HamButton.Builder boomButtonProfile;
        HamButton.Builder boomButtonAddJob;

        bmb = (BoomMenuButton) a. findViewById(R.id.bmb);
        bmb.setNormalColor(AppController.getContext().getResources().getColor(R.color.primary));
        boomButtonProfile = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_add_white_24dp)
                .normalTextRes(R.string.ham_job)
                .subNormalTextRes(R.string.ham_job_sub)
                .normalColorRes(R.color.primary);
        bmb.addBuilder(boomButtonProfile);
        boomButtonProfile.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                //  Intent i = new Intent(Home.this, AddJob.class);
                // startActivity(i);
            }
        });

        boomButtonAddJob = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_settings_white)
                .normalTextRes(R.string.ham_profile)
                .subNormalTextRes(R.string.ham_profile_sub)
                .normalColorRes(R.color.red_400);

        bmb.addBuilder(boomButtonAddJob);
        boomButtonAddJob.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                /*
                Fragment parentProfile = new ParentProfil();
                Bundle bundle = new Bundle();
                bundle.putString("First time", "no");
                parentProfile.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fl, parentProfile).addToBackStack("Home").commit();
                */
            }
        });

        boomButtonProfile = new HamButton.Builder()
                //.normalImageRes(R.drawable.profilee)
                .normalTextRes(R.string.dummy_content)
                .subNormalTextRes(R.string.title_activity_sign_up)
                .normalColorRes(R.color.blue_A400);

        bmb.addBuilder(boomButtonProfile);

        boomButtonAddJob = new HamButton.Builder()
                // .normalImageRes(R.drawable.profilee)
                .normalTextRes(R.string.dummy_content)
                .subNormalTextRes(R.string.title_activity_sign_up)
                .normalColorRes(R.color.primary_dark);

        bmb.addBuilder(boomButtonAddJob);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
