package com.esprit.chedliweldi.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.dd.morphingbutton.MorphingButton;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;

import static com.esprit.chedliweldi.Fragments.Login.PREFS_NAME;

public class AddJob extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, OnMapReadyCallback,LocationListener {
    private GoogleMap map;
    private MapView mapView;
    private LinearLayout cardView1;
    private TextView timeFrom, timeTo;
    private EditText title, descr;
    private Button addJob;
    private AppBarLayout appBarLayout;
    private LatLng position;
    private int id;
    private String date;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);
    private final SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd", /*Locale.getDefault()*/Locale.ENGLISH);

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private CompactCalendarView compactCalendarView;

    private MorphingButton btnMorph;
    private boolean isExpanded = false;
    private String selectedDate;
    private LocationManager locationManager;
    private String provider;
    private SupportMapFragment mapFragment;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_app_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("New job");

       // checkLocationPermission();
        cardView1 = (LinearLayout) findViewById(R.id.linearLayoutTime);
        cardView1.setTag("cardView1");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        title = (EditText) findViewById(R.id.txtTitle);
        descr = (EditText) findViewById(R.id.txtDescription);
        timeFrom = (TextView) findViewById(R.id.txtFrom);
        timeTo = (TextView) findViewById(R.id.txtTo);
        addJob = (Button) findViewById(R.id.btnAddJob);
        btnMorph = (MorphingButton) findViewById(R.id.morphing);
        morphToSquare(btnMorph, 0);
        settings = this.getSharedPreferences(PREFS_NAME, 0);

        btnMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((title.getText().toString().trim().length()) <= 0) {
                    morphToFailure(btnMorph, integer(R.integer.mb_animation));
                } else {
                    AddJobPost(Integer.parseInt(settings.getString("id",null)), selectedDate, title.getText().toString(), descr.getText().toString(), timeFrom.getText().toString(), timeTo.getText().toString(),position);
                }

            }
        });

       /* addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morphToSquare(btnMorph, integer(R.integer.mb_animation));
                AddJobPost(4, selectedDate, title.getText().toString(), descr.getText().toString(), timeFrom.getText().toString(), timeTo.getText().toString());
            }
        });*/

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddJob.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setStartTime(8, 00);
                tpd.setEndTime(18, 15);
                tpd.setCancelable(true);
                tpd.setUserVisibleHint(true);
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });


        appBarLayout = findViewById(R.id.app_bar_layout);

        // Set up the CompactCalendarView
        compactCalendarView = findViewById(R.id.compactcalendar_view);

        // Force English
        compactCalendarView.setLocale(TimeZone.getDefault(), /*Locale.getDefault()*/Locale.ENGLISH);

        compactCalendarView.setShouldDrawDaysHeader(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                selectedDate = dateFormatSQL.format(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        final ImageView arrow = findViewById(R.id.date_picker_arrow);

        RelativeLayout datePickerButton = findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(v -> {
            float rotation = isExpanded ? 0 : 180;
            ViewCompat.animate(arrow).rotation(rotation).start();

            isExpanded = !isExpanded;
            appBarLayout.setExpanded(isExpanded, true);
        });

    }

    private void AddJobPost(int parentId, String date, String title, String descr, String from, String to ,LatLng position) {
        String lati = "" + position.latitude;
        String longi = ""+position.longitude;
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS + "AddJob.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);
                    id = Integer.parseInt(response);
                    this.date = selectedDate;
                    morphToSuccess(btnMorph);

                },
                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                    morphToFailure(btnMorph, 1);
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "" + parentId);
                params.put("date", date);
                params.put("desc", descr);
                params.put("from", from);
                params.put("to", to);
                params.put("title", title);
                params.put("longi",lati);
                params.put("alt", longi);
                Log.d("Params", params.values().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        timeFrom.setText(hourString + ":" + minuteString);
        timeTo.setText(hourStringEnd + ":" + minuteStringEnd);
    }

    private void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
        selectedDate = dateFormatSQL.format(date);

        if (compactCalendarView != null) {
            compactCalendarView.setCurrentDate(date);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = findViewById(R.id.title);

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    private void setSubtitle(String subtitle) {
        TextView datePickerTextView = findViewById(R.id.date_picker_text_view);

        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_200))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_blue))
                .colorPressed(color(R.color.mb_blue_dark))
                .text("Add");
        btnMorph.morph(square);
    }

    private void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.green_primary))
                .colorPressed(color(R.color.green_primary_dark))
                .icon(R.drawable.ic_right_arrow);
        btnMorph.morph(circle);
        btnMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("morph", "succes click");
                Intent i = new Intent(AddJob.this,CreateTaskListForJob.class);
                i.putExtra("id",id);
                i.putExtra("date",date);
                startActivity(i);
            }
        });
    }

    private void morphToFailure(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.red_primary))
                .colorPressed(color(R.color.red_primary_dark))
                .icon(R.drawable.ic_close_white_24dp);
        btnMorph.morph(circle);
    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map=googleMap;
        if(checkLocationPermission()){
            googleMap.setMyLocationEnabled(true);
            map.setMyLocationEnabled(true);
        }
        position =new LatLng(Double.parseDouble(settings.getString("altitude",null)),Double.parseDouble(settings.getString("longitude",null)));
        googleMap.clear();
        googleMap.addMarker(new MarkerOptions().position(position));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                // TODO Auto-generated method stub
                position=point;
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(point));
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();

        Log.i("Location info: Lat", lat.toString());
        Log.i("Location info: Lng", lng.toString());
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

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location permission")
                        .setMessage("We need your permission to access your details")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(AddJob.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // details-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                        //Request details updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            locationManager.removeUpdates(this);
        }
    }
}
