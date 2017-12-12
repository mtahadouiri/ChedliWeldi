package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.adapters.FullScreenImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.adapters.ImageGalleryAdapter;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.Login;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.MyOffersFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.OfferRecycleViewAdapter;
import me.gujun.android.taggroup.TagGroup;

public class CalendarActivity extends AppCompatActivity implements WeekViewLoader {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private static final String TAG = "MainActivity";
    ImageGalleryFragment fragment;
   LinearLayoutCompat linear ;

    private PaletteColorType paletteColorType;
    TagGroup tags;
    RelativeLayout infoTab ;
Button btn;
WeekView mWeekView;
List<WeekViewEvent> events = new ArrayList<>();



 WeekViewEvent setupEvent(JSONObject json ) throws Exception {
    String name="mission"+json.getString("lastName");

    int id = json.getInt("idOffer");

     int newMonth=12 ;
     int newYear=2017 ;
     Calendar startTimee = Calendar.getInstance();
     startTimee.set(Calendar.HOUR_OF_DAY, 3);
     startTimee.set(Calendar.MINUTE, 0);
     startTimee.set(Calendar.MONTH, newMonth-1);
     startTimee.set(Calendar.YEAR, newYear);
     Calendar endTimee = (Calendar) startTimee.clone();
     endTimee.add(Calendar.HOUR, 1);
     endTimee.set(Calendar.MONTH, newMonth-1);

     Calendar startTime = Calendar.getInstance();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

     Date date=sdf.parse(json.getString("end"));

     startTime.setTime(sdf.parse(json.getString("start")));

     Calendar endTime = (Calendar) startTime.clone();
     endTime.setTime(sdf.parse(json.getString("end")));
     WeekViewEvent event = new WeekViewEvent(id,name, startTime, endTime);
     event.setColor(getResources().getColor(R.color.event_color_01));
  return  event ;

 }

 List<WeekViewEvent> setupCalendar(JSONArray array) throws Exception {

     List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

     for(int i=0;i<array.length();i++){
         events.add(setupEvent(array.getJSONObject(i)));
     }

     return events;
 }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2, menu);
        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {

                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    List<WeekViewEvent> getEvent(){
    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    int newMonth=12 ;
    int newYear=2017 ;
    Calendar startTime = Calendar.getInstance();
    startTime.set(Calendar.HOUR_OF_DAY, 3);
    startTime.set(Calendar.MINUTE, 0);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    Calendar endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR, 1);
    endTime.set(Calendar.MONTH, newMonth-1);

    WeekViewEvent event = new WeekViewEvent(1,"re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_01));
    events.add(event);

    startTime = Calendar.getInstance();
    startTime.set(Calendar.HOUR_OF_DAY, 3);
    startTime.set(Calendar.MINUTE, 30);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    endTime = (Calendar) startTime.clone();
    endTime.set(Calendar.HOUR_OF_DAY, 4);
    endTime.set(Calendar.MINUTE, 30);
    endTime.set(Calendar.MONTH, newMonth-1);
    event = new WeekViewEvent(10, "re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_02));
    events.add(event);

    startTime = Calendar.getInstance();
    startTime.set(Calendar.HOUR_OF_DAY, 4);
    startTime.set(Calendar.MINUTE, 20);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    endTime = (Calendar) startTime.clone();
    endTime.set(Calendar.HOUR_OF_DAY, 5);
    endTime.set(Calendar.MINUTE, 0);
    event = new WeekViewEvent(10, "re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_03));
    events.add(event);

    startTime = Calendar.getInstance();
    startTime.set(Calendar.HOUR_OF_DAY, 5);
    startTime.set(Calendar.MINUTE, 30);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR_OF_DAY, 2);
    endTime.set(Calendar.MONTH, newMonth-1);
    event = new WeekViewEvent(2, "re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_02));
    events.add(event);

    startTime = Calendar.getInstance();
    startTime.set(Calendar.HOUR_OF_DAY, 5);
    startTime.set(Calendar.MINUTE, 0);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    startTime.add(Calendar.DATE, 1);
    endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR_OF_DAY, 3);
    endTime.set(Calendar.MONTH, newMonth - 1);
    event = new WeekViewEvent(3, "re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_03));
    events.add(event);

    startTime = Calendar.getInstance();
    startTime.set(Calendar.DAY_OF_MONTH, 15);
    startTime.set(Calendar.HOUR_OF_DAY, 3);
    startTime.set(Calendar.MINUTE, 0);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR_OF_DAY, 3);
    event = new WeekViewEvent(4, "re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_04));
    events.add(event);

    startTime = Calendar.getInstance();
    startTime.set(Calendar.DAY_OF_MONTH, 1);
    startTime.set(Calendar.HOUR_OF_DAY, 3);
    startTime.set(Calendar.MINUTE, 0);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR_OF_DAY, 3);
    event = new WeekViewEvent(5, "re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_01));
    events.add(event);

    startTime = Calendar.getInstance();
    startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
    startTime.set(Calendar.HOUR_OF_DAY, 15);
    startTime.set(Calendar.MINUTE, 0);
    startTime.set(Calendar.MONTH, newMonth-1);
    startTime.set(Calendar.YEAR, newYear);
    endTime = (Calendar) startTime.clone();
    endTime.add(Calendar.HOUR_OF_DAY, 3);
    event = new WeekViewEvent(5, "re", startTime, endTime);
    event.setColor(getResources().getColor(R.color.event_color_02));
    events.add(event);

    return events;
}

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calendar);
        initView();

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setWeekViewLoader(this);
        mWeekView.setNumberOfVisibleDays(3);

        // Lets change some dimensions to best fit the view.
        mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        mWeekView.setEventTextSize(20);
        mWeekView.setEventPadding(0);

getCalendar(Login.connectedUser);





    }


    LinearLayout tmp;





    @Override
    public double toWeekViewPeriodIndex(Calendar instance) {
        return 0;
    }

    @Override
    public List<? extends WeekViewEvent> onLoad(int periodIndex) {
     return events;
    }

    JSONArray offers;
    private void getCalendar(final String idUser) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getCalendar";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");



                    if (d){
                        Log.i("etat","failed");
                    }
                    else{

                        offers = jsonObject.getJSONArray("offers");
                         events=setupCalendar(offers);

                        mWeekView.notifyDatasetChanged();





                        Log.i("etat","success");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_user",idUser);
                //  headers.put("abc", "value");
                return headers;
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");


                //  headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


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
        //  navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        Menu m = navigationView.getMenu();
        //   MenuItem foo_menu_item=m.add("foo");

        MenuItem myOffers = (MenuItem) m.findItem(R.id.nav_my_offers);


        myOffers.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i = new Intent(AppController.getContext(),MyOfferActivity.class);
                startActivity(i);

                return false;
            }
        });

        MenuItem goingOffers = (MenuItem) m.findItem(R.id.on_going_offers);

        MenuItem calendar = (MenuItem) m.findItem(R.id.calendar);



        goingOffers.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i = new Intent(AppController.getContext(),OnGoingOfferActivity.class);
                startActivity(i);
                return false;
            }

        });


        calendar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent i = new Intent(AppController.getContext(),CalendarActivity.class);
                startActivity(i);
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
                startActivity(i);
                return false;
            }
        });







        View headerView = navigationView.getHeaderView(0);


        LinearLayout nav_header = (LinearLayout) headerView.findViewById(R.id.nav_header);
        nav_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppController.getContext(), MainActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


    }



}