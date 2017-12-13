package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.Login;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.MyOfferRecyclerViewAdapter;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.RecycleItemClickListener;

public class MyOfferActivity extends AppCompatActivity {

    RecyclerView offers ;
   MyOfferRecyclerViewAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_offers);
        initView();
       ;

        offers = (RecyclerView) findViewById(R.id.recycler_view);
        offers.setLayoutManager(new LinearLayoutManager(this));

          getOffers(LoginActivity.connectedUser);

        offers.addOnItemTouchListener(new RecycleItemClickListener(this, offers, new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Intent i = new Intent(MyOfferActivity.this,RequestsActivity.class);
                final String  ff;
                try {
                    ff = off.getJSONObject(position).getString("id");
                    i.putExtra("id",ff);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }



            @Override
            public void onItemLongClick(View view, int position) {
                Log.i(" dfs","sdf");
            }
        }));



    }
    JSONArray off;
    private void getOffers(final String id_user) {


        Log.e("sdf", "uploadUser:  near volley new request ");


        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getUserOffers";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {


                     off = new JSONArray(response);




                 Log.i("","");



                        adapter = new MyOfferRecyclerViewAdapter(MyOfferActivity.this, off);
                        offers.setAdapter(adapter);



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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("id_user",id_user);
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
