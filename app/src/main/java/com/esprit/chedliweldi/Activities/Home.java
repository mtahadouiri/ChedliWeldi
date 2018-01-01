package com.esprit.chedliweldi.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appyvet.materialrangebar.RangeBar;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Fragments.Feed;
import com.esprit.chedliweldi.Fragments.Login;
import com.esprit.chedliweldi.Fragments.Map;
import com.esprit.chedliweldi.Fragments.ParentProfil;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.FragmentAdapter;

import static com.esprit.chedliweldi.AppController.getContext;
import static com.esprit.chedliweldi.Fragments.Login.PREFS_NAME;

public class Home extends AppCompatActivity implements LocationListener, Feed.OnFragmentInteractionListener, Map.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, ParentProfil.OnFragmentInteractionListener {
    private static final long MIN_TIME_BW_UPDATES = 0;
    private static final float MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DrawerLayout drawer;
    private BoomMenuButton bmb;
    private HamButton.Builder boomButtonProfile;
    private HamButton.Builder boomButtonAddJob;

    public String resp;
    public List<Babysitter> babysitters = new ArrayList<>();

    private static boolean isShowPageStart = true;
    private final int MESSAGE_SHOW_DRAWER_LAYOUT = 0x001;
    private final int MESSAGE_SHOW_START_PAGE = 0x002;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case MESSAGE_SHOW_DRAWER_LAYOUT:
                    drawer.openDrawer(GravityCompat.START);
                    SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirst", false);
                    editor.apply();
                    break;
            }
        }
    };
    private String name;
    private String lastName;
    private String imageUrl;
    private String email;
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
    private Feed feed;
    private Map map;

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
        setContentView(R.layout.activity_home);
       bmb = (BoomMenuButton) findViewById(R.id.bmb);
        bmb.setNormalColor(getResources().getColor(R.color.primary));
        minDist=0;
        maxDist=100;
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


        initView();
        feed = new Feed();
        map=new Map();
        initViewPager();

        setUpBoomMenu();

      /*  Bundle inBundle = getIntent().getExtras();
        name = inBundle.getString("name");
        lastName = inBundle.getString("lastname");
        imageUrl = inBundle.getString("imageUrl");
        email = inBundle.getString("email");*/

    }


    private void setUpBoomMenu() {
        boomButtonProfile = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_add_white_24dp)
                .normalTextRes(R.string.ham_job)
                .subNormalTextRes(R.string.ham_job_sub)
                .normalColorRes(R.color.primary);
        bmb.addBuilder(boomButtonProfile);
        boomButtonProfile.listener(new OnBMClickListener() {
            @Override
            public void onBoomButtonClick(int index) {
                Intent i = new Intent(Home.this, AddJob.class);
                startActivity(i);
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
                Fragment parentProfile = new ParentProfil();
                Bundle bundle = new Bundle();
                bundle.putString("First time", "no");
                parentProfile.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fl, parentProfile).addToBackStack("Home").commit();
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
    public void onFragmentInteraction(Uri uri) {

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //  navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Menu m = navigationView.getMenu();
        //   MenuItem foo_menu_item=m.add("foo");

        MenuItem myOffers = (MenuItem) m.findItem(R.id.nav_my_offers);


        myOffers.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
/*
                Intent i = new Intent(AppController.getContext(),MyOfferActivity.class);
                startActivity(i);

            /*    Fragment MessageFragment = new Chat();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl,MessageFragment).addToBackStack("Home").commit();*/
                Intent i = new Intent(getContext(), Messages.class);
                startActivity(i);
                return false;
            }
        });

        MenuItem goingOffers = (MenuItem) m.findItem(R.id.on_going_offers);

        MenuItem calendar = (MenuItem) m.findItem(R.id.calendar);


        goingOffers.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i = new Intent(getContext(), OnGoingOfferActivity.class);
                startActivity(i);
                return false;
            }

        });


        calendar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i = new Intent(getContext(), CalendarActivity.class);
                startActivity(i);
                return false;
            }

        });

        MenuItem onGoingParent = (MenuItem)m.findItem(R.id.On_Going_Parent);
        onGoingParent.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent(getContext(), OnGoing.class);
                startActivity(i);
                return false;
            }
        });


        if (Login.type.equals("Babysitter")) {
            myOffers.setVisible(false);
            goingOffers.setVisible(true);
            calendar.setVisible(true);
        } else {
            calendar.setVisible(false);
            goingOffers.setVisible(false);
        }


        MenuItem settings = (MenuItem) m.findItem(R.id.nav_settings);


        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i = new Intent(getContext(), SettingActivity.class);
                startActivity(i);
                return false;
            }
        });


        View headerView = navigationView.getHeaderView(0);


        LinearLayout nav_header = (LinearLayout) headerView.findViewById(R.id.nav_header);
        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


    }

    public void initViewPager() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_main);
        mViewPager = (ViewPager) findViewById(R.id.view_pager_main);

        List<String> titles = new ArrayList<>();
        titles.add(getString(R.string.tab_title_main_1));
        titles.add(getString(R.string.tab_title_main_2));

        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        //mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(feed);
        fragments.add(map);

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






    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_main_1:
                handleDial();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent = new Intent();

      /*  switch (item.getItemId()) {
            case R.id.nav_recycler_and_swipe_refresh:
                intent.setClass(this, RecyclerViewActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_scrolling:
                intent.setClass(this, ScrollingActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_full_screen:
                intent.setClass(this, FullscreenActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_bottom_navigation:
                intent.setClass(this, BottomNavigationActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_settings:
                intent.setClass(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_about:
                intent.setClass(this, AboutActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_donate:
                intent.setClass(this, DonateActivity.class);
                startActivity(intent);
                break;

            case R.id.nav_color:
                if (checkAppInstalled(Constant.MATERIAL_DESIGN_COLOR_PACKAGE)) {
                    intent = getPackageManager().getLaunchIntentForPackage(Constant.MATERIAL_DESIGN_COLOR_PACKAGE);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    intent.setData(Uri.parse(Constant.MATERIAL_DESIGN_COLOR_URL));
                    intent.setAction(Intent.ACTION_VIEW);
                    startActivity(intent);
                }
                break;
        }*/

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkAppInstalled(String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "sd");
        Log.d("latitude", "Here : " + latitude);
        Log.d("longitude", "Here : " + longitude);
        feed.filtreBabysitters(feed.getBabysiiters());
       // map.populateMap();
        updateLocation(latitude+"",longitude+"");
    }
    private void updateLocation(String lat,String longi) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = AppController.TAHA_ADRESS + "updateLocation";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);

                },
                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                }
        ) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<String, String>();
                params.put("alt",lat);
                params.put("long", longi);
                params.put("email", Home.this.getSharedPreferences(PREFS_NAME, 0).getString("email",null));
                Log.d("Params", params.values().toString());

                return params;
            }
        };
        queue.add(postRequest);
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

    public void handleDial() {
        // Sets fonts for all
//        Typeface font = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");
////        ViewGroup root = (ViewGroup) findViewById(R.id.mylayout);
////        setFont(root, font);

        // Gets the buttons references for the buttons
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.filter_dialog, null);
        dialogBuilder.setView(dialogView);

        //Sets the buttons to bold.
//        barColor.setTypeface(font, Typeface.BOLD);
//        connectingLineColor.setTypeface(font, Typeface.BOLD);
//        pinColor.setTypeface(font, Typeface.BOLD);

        // Gets the RangeBar
        rangebar = (RangeBar) dialogView.findViewById(R.id.rangebar1);


        // Setting Index Values -------------------------------

        // Gets the index value TextViews
        final EditText leftIndexValue = (EditText) dialogView.findViewById(R.id.leftIndexValue);
        final EditText rightIndexValue = (EditText) dialogView.findViewById(R.id.rightIndexValue);
        if(minDist>=0 && maxDist>=0){
            rangebar.setLeft(minDist);
            rangebar.setRight(maxDist);
            leftIndexValue.setText("" + minDist);
            rightIndexValue.setText("" + maxDist);
        }
        // Sets the display values of the indices
        rangebar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {
                leftIndexValue.setText("" + leftPinIndex);
                minDist=leftPinIndex;
                rightIndexValue.setText("" + rightPinIndex);
                maxDist=rightPinIndex;
            }

        });
        Button btnDone =(Button)dialogView.findViewById(R.id.btnDone);

        final AlertDialog b = dialogBuilder.create();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feed.filtreBabysitters(feed.getBabysiiters());
                map.populateMap();
                b.dismiss();
            }
        });

        b.show();


        // Sets the indices themselves upon input from the user

        // Setting Number Attributes -------------------------------

        // Sets tickStart

        // Sets tickEnd

        // Setting Color Attributes---------------------------------

    }

    private int getValueInDP(int value) {
        int valueInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics());
        return valueInDp;
    }

}

