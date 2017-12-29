package com.esprit.chedliweldi.Activities;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.esprit.chedliweldi.Fragments.OffersFragment;
import com.esprit.chedliweldi.Fragments.ParentMainFragment;
import com.esprit.chedliweldi.Fragments.ParentMapFragment;
import com.esprit.chedliweldi.Fragments.ParentProfil;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.FragmentAdapter;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.List;

public class BabySitterMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_sitter_main);
        initView();
        initViewPager();

        setUpBoomMenu();






    }


    private void setUpBoomMenu() {
        BoomMenuButton bmb;
         HamButton.Builder boomButtonProfile;
         HamButton.Builder boomButtonAddJob;
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setNormalColor(getResources().getColor(R.color.primary));
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


    public void initViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_main);

        List<String> titles = new ArrayList<>();
        titles.add("offers");
        titles.add("map");

        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        //mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
        OffersFragment tab1 =  new OffersFragment();
        ParentMapFragment tab2 = new ParentMapFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(tab1);
        fragments.add(tab2);

        mViewPager.setOffscreenPageLimit(2);

        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mFragmentAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private DrawerLayout drawer;
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerView = navigationView.getHeaderView(0);
        LinearLayout nav_header = (LinearLayout) headerView.findViewById(R.id.nav_header);
        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BabySitterMainActivity.this, MainActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}