package com.esprit.chedliweldi.Activities;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.appyvet.materialrangebar.RangeBar;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Fragments.OffersFragment;
import com.esprit.chedliweldi.Fragments.ParentMainFragment;
import com.esprit.chedliweldi.Fragments.ParentMapFragment;
import com.esprit.chedliweldi.Fragments.ParentProfil;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.BabysitterRecyclerViewAdapter;
import com.esprit.chedliweldi.Utils.DrawerInitializer;
import com.esprit.chedliweldi.Utils.FragmentAdapter;
import com.esprit.chedliweldi.adapters.OfferRecycleViewAdapter;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class BabySitterMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  , LocationListener {
    private static final long MIN_TIME_BW_UPDATES = 0;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LocationManager locationManager;
    private String provider;
    private Location mLastLocation;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private boolean canGetLocation;
    private static Location location;
    private double latitude;
    private double longitude;
    private RangeBar rangebar;
    private static int minDist,maxDist;
    public static int getMinDistance() {
        return minDist;
    }

    public static int getMaxDistance() {
        return maxDist;
    }
    public static Location getUserLocation(){
        return location;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_sitter_main);
       //initView();

      //  initView(this);

        DrawerInitializer.initView(this);
        setupLocation();

        setUpBoomMenu();
        getOffers();
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


public List<JSONObject>data;


    private void getOffers() {




        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"offers";
        StringRequest sr = new StringRequest(Request.Method.GET, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){
                        Log.i("etat","failed");
                    }
                    else{
                        JSONArray offerss = jsonObject.getJSONArray("offers");
                        data=filtreParents(offerss);

                        initViewPager();

                        tab1.refreshAdapter(data);



                        Log.i("etat","success");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Log.d("", ""+error.getMessage()+","+error.toString());
            }
        }){


            @Override
            public java.util.Map<String, String> getHeaders() throws AuthFailureError {
                java.util.Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }



    public List<JSONObject> filtreParents(JSONArray parents) throws JSONException {


        List<JSONObject> data = new ArrayList<>();

        for (int i=0;i<parents.length();i++){
            JSONObject parent =parents.getJSONObject(i);
            Location mallLoc = new Location("");
            mallLoc.setLatitude(parent.getDouble("altitude"));
            mallLoc.setLongitude(parent.getDouble("altitude"));
            float distance =mallLoc.distanceTo(getUserLocation())/1000;

            if ((mallLoc.distanceTo(getUserLocation())/1000) < getMinDistance() || (mallLoc.distanceTo(getUserLocation())/1000) > getMaxDistance()) {
            }
            else{

                parent.put("distance",distance);
                data.add(parent);

            }

        }

        //compare


        Collections.sort(data, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject t, JSONObject b) {


                try {
                    return Double.compare(t.getDouble("distance"),b.getDouble("distance"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return  0;
            }
        });

/*
        adapter=new BabysitterRecyclerViewAdapter(babysitters,getContext());
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
*/
        return  data
                ;
    }


    OffersFragment tab1 ;
    ParentMapFragment tab2 ;
void setupLocation(){
    minDist=0;
    maxDist=5000;
    locationManager = (LocationManager) this
            .getSystemService(Context.LOCATION_SERVICE);

    // getting GPS status
    isGPSEnabled = locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER);

    // getting network status
    isNetworkEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    if (!isGPSEnabled && !isNetworkEnabled) {
        // no network provider is enabled
    } else {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        } else {
            this.canGetLocation = true;
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("activity", "LOC Network Enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        Log.d("activity", "LOC by Network");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.d("NETWORK_PROVIDER", "sd");
                        Log.d("latitude", "Here : " + latitude);
                        Log.d("longitude", "Here : " + longitude);

                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("activity", "RLOC: GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Log.d("activity", "RLOC: loc by GPS");

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            Log.d("GPS_PROVIDER", "sd");
                            Log.d("latitude", "Here : " + latitude);
                            Log.d("longitude", "Here : " + longitude);
                        }
                    }
                }
            }
        }
    }
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
         tab1 =  new OffersFragment();
         tab2 = new ParentMapFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(tab1);
       // fragments.add(tab2);

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
        Toolbar toolbar = (Toolbar)  findViewById(R.id.toolbar_main);
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

    private void initView(AppCompatActivity activity) {

        Toolbar toolbar = (Toolbar)   activity.findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

       DrawerLayout  drawer = (DrawerLayout) activity. findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}