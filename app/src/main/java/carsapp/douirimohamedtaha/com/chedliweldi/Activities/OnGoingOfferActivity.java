package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.etiennelawlor.imagegallery.library.ImageGalleryFragment;
import com.etiennelawlor.imagegallery.library.enums.PaletteColorType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.Login;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.MyOfferRecyclerViewAdapter;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.OfferRecycleViewAdapter;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.OnGoingOfferRecycleViewAdapter;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RecycleItemClickListener;
import me.gujun.android.taggroup.TagGroup;

public class OnGoingOfferActivity extends AppCompatActivity  implements   NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    RecyclerView recyclerOffers ;
   OnGoingOfferRecycleViewAdapter adapter ;
    private DrawerLayout drawer;


 WeekViewEvent setupEvent(JSONObject json ) throws Exception {
    String name="mission by"+json.getString("firstName")+" "+json.getString("lastName");

    int id = json.getInt("id");


     Calendar startTime = Calendar.getInstance();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttt);
        initView();

        // Get a reference for the week view in the layout.

        recyclerOffers = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerOffers.setLayoutManager(new LinearLayoutManager(this));
        getOffers(Login.connectedUser);
       // getOffers(LoginActivity.connectedUser);

        recyclerOffers.addOnItemTouchListener(new RecycleItemClickListener(this, recyclerOffers, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {


            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));




    }


    LinearLayout tmp;

    JSONArray offers;
    private void getOffers(final String id ) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getOnGoingOffers";
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


                        adapter = new     OnGoingOfferRecycleViewAdapter(OnGoingOfferActivity.this, offers);
                        recyclerOffers.setAdapter(adapter);



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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_user",id);
                //  headers.put("abc", "value");
                return headers;
            }




        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
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
                Intent intent = new Intent(OnGoingOfferActivity.this, MainActivity.class);
                startActivity(intent);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });


    }


}






