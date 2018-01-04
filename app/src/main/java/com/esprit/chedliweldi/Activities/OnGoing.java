package com.esprit.chedliweldi.Activities;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Entities.Task;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.BabySittersJSONParser;
import com.esprit.chedliweldi.adapters.TaskListAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.esprit.chedliweldi.AppController.getContext;
import static com.esprit.chedliweldi.Fragments.Login.PREFS_NAME;
import static com.esprit.chedliweldi.Fragments.Login.type;

public class OnGoing extends AppCompatActivity implements OnMapReadyCallback {
    List<Task> taskList;
    List<Task> doneTaskList;
    RecyclerView rv ;
    TaskListAdapter adapter;
    private GoogleMap mMap;
    private double lat=0,longi=0;
    private Marker m;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        extras = getIntent().getExtras();
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

    }

    private void getJobDetails(){
        SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Log.d("ID",settings.getString("id",null));
        String url;
        if(type.equals("Parent")){
             url = AppController.TAHA_ADRESS+"getTaskListParentByJobId?id="+extras.getString("jobId");
        }
        else{
             url = AppController.TAHA_ADRESS+"getTaskListBabySitter?id="+settings.getString("id",null);
        }
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JSONArray task_array = null;
                        Task task = null;
                        try {

                            task_array = new JSONArray(response);
                            taskList = new ArrayList<>();

                            for (int i = 0; i < task_array.length(); i++) {

                                JSONObject obj = task_array.getJSONObject(i);
                                task = new Task();
                                task.setId(obj.getString("idTask"));
                                task.setDetails(obj.getString("details"));
                                task.setName(obj.getString("task"));
                                task.setTime(obj.getString("time"));
                                task.setState(obj.getString("state"));
                                Log.d("Task",task.toString());
                                taskList.add(task);
                            }
                            JSONObject obj = task_array.getJSONObject(0);
                            lat=Double.parseDouble(obj.getString("latitude"));
                            longi=Double.parseDouble(obj.getString("longitude"));
                            Marker m = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(lat,longi))
                                    .title("Job"));
                            m.setTag("Job");
                            moveToCurrentLocation(m.getPosition());
                            adapter=new TaskListAdapter(taskList,getContext());
                            adapter.notifyDataSetChanged();
                            rv.setAdapter(adapter);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //ask for permissions..
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        getJobDetails();



    }


}
