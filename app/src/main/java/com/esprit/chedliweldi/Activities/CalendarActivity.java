package com.esprit.chedliweldi.Activities;

/**
 * Created by oussama_2 on 11/27/2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.esprit.chedliweldi.Fragments.Login;
import com.esprit.chedliweldi.Utils.DrawerInitializer;
import com.esprit.chedliweldi.adapters.RecycleItemClickListener;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.adapters.EventRecyclerViewAdapter;

public class CalendarActivity extends AppCompatActivity   {

    private static final String TAG = "MainActivity";
    RecyclerView events ;
    EventRecyclerViewAdapter adapter ;

    private java.util.Calendar currentCalender = java.util.Calendar.getInstance(Locale.getDefault());
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    CompactCalendarView  compactCalendarView ;

    public JSONArray dates;



    void setupCalendar(JSONArray data) throws ParseException, JSONException {
        for(int i=0;i<data.length();i++){
          String da=  data.getJSONObject(i).getString("date");
            Date date =dateFormatter.parse(da);
            currentCalender.setTime(date);
            Event ev1 = new Event(Color.GREEN, currentCalender.getTimeInMillis(), "Some extra d");
            compactCalendarView.addEvent(ev1,false);
        }
        compactCalendarView.invalidate();
    }


    private void getDates(final String idUser) {




        //  JSONObject jsonObj = new JSONObject(params);
        String url = AppController.SERVER_ADRESS+"getDates";
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

                        dates = jsonObject.getJSONArray("dates");
                        setupCalendar(dates);

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


    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        DrawerInitializer.initView(this);
        DrawerInitializer.setUpBoomMenu(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        compactCalendarView= (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(false);
        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        compactCalendarView.getFirstDayOfCurrentMonth();
         events = (RecyclerView) findViewById(R.id.recycler_view);
        events.setLayoutManager(new LinearLayoutManager(this));
        events.addOnItemTouchListener(new RecycleItemClickListener(this, events, new RecycleItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent i = new Intent(CalendarActivity.this,ScheduledOfferBabysitterActivity.class);
                        try {
                            i.putExtra("id",JsonEvents.getJSONObject(position).getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(i);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }));


        getDates(Login.connectedUser);
        Date d = new Date();

        toolbar.setTitle( "Calendar "+ dateFormatter.format(d));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);

            String date =    dateFormatter.format(dateClicked);
                toolbar.setTitle( "Calendar "+ date);
            getCalendar(Login.connectedUser,date);

                Log.d(TAG, "Day was clicked: " + dateClicked + " with events " + events);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
            }
        });

    }


JSONArray JsonEvents ;

    void refreshEvents(){

    }

    private void getCalendar(final String idUser,final String date) {




        //  JSONObject jsonObj = new JSONObject(params);
        String url = AppController.SERVER_ADRESS+"getCalendarByDate";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){
                        Log.i("etat","failed");
                         JsonEvents = new JSONArray();

                    }
                    else{

                        JsonEvents = jsonObject.getJSONArray("offers");

                        Log.i("etat","success");
                    }
                    adapter = new EventRecyclerViewAdapter(CalendarActivity.this, JsonEvents);
                    events.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


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
                headers.put("date",date);
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




}