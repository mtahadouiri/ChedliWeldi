package com.esprit.chedliweldi.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.chedliweldi.Activities.Home;
import com.esprit.chedliweldi.Activities.ProfilActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Utils.BabySittersJSONParser;
import com.esprit.chedliweldi.Utils.BabysitterRecyclerViewAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import com.esprit.chedliweldi.Activities.MainActivity;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Entities.MyItem;
import com.esprit.chedliweldi.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Map.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Map#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Map extends Fragment implements OnMapReadyCallback,OnInfoWindowClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap mMap;
    private Marker mMarcadorActual;
    private ClusterManager<MyItem> mClusterManager;

    private OnFragmentInteractionListener mListener;
    private List<Babysitter> babysitters;

    public Map() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Map.
     */
    // TODO: Rename and change types and number of parameters
    public static Map newInstance(String param1, String param2) {
        Map fragment = new Map();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        mMap.setMyLocationEnabled(true);
        mMap.setOnInfoWindowClickListener(this);

      //  mClusterManager = new ClusterManager<MyItem>(getContext(), mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
       // mMap.setOnMarkerClickListener(mClusterManager);
        getBabysiiters();
        //populateMap();
       // mMap.setOnCameraIdleListener(mClusterManager);
        //mMap.setOnMarkerClickListener(mClusterManager);

    }

    public void populateMap() {
        for (Babysitter b: babysitters) {

            Log.d("Babysitters",babysitters.size()+"");
            Marker m = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(b.getAltitude(),b.getLongitude()))
                    .title(b.getFirstName()+" "+b.getLastName())
            .snippet(b.getDistance()+" km"));

            m.setTag(b);

            Log.d("Marker",""+m.getPosition());
            MyItem offsetItem = new MyItem(m.getPosition());
            //mClusterManager.addItem(offsetItem);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(getContext(), ProfilActivity.class);
        Babysitter b = (Babysitter) marker.getTag();
        JSONObject j = new JSONObject();
        try {
            j.put("id",b.getId());
            j.put("about",b.getDescr());
            j.put("photo",b.getImgURL());
            j.put("rate",4);
            j.put("firstName",b.getFirstName());
            j.put("lastname",b.getLastName());
            j.put("phoneNumber",b.getPhone());
            ProfilActivity.user=j;
        } catch (JSONException e) {

        }
        i.putExtra("user", (Parcelable) marker.getTag());
        startActivity(i);
    }

   /* @Override
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(getContext(),
                marker.getTitle() +
                        " has been clicked " + marker.getTag().toString() + " times.",
                Toast.LENGTH_SHORT).show();
        return false;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public List<Babysitter> getBabysiiters() {
        babysitters=new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = AppController.TAHA_ADRESS+"getBabysitters";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        babysitters= BabySittersJSONParser.parseData(response);
                        //MainActivity.bbySitters=babysitters;
                        filtreBabysitters(babysitters);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        return babysitters;
    }

    public void filtreBabysitters(List<Babysitter> babysitters) {

        List<Babysitter> bb = new ArrayList<>();
        Log.d("Babysittersssssssssss",babysitters.size()+"");
        Iterator<Babysitter> iter = babysitters.iterator();


        for (Babysitter b :
                babysitters) {
            Location mallLoc = new Location("");
            mallLoc.setLatitude(b.getAltitude());
            mallLoc.setLongitude(b.getLongitude());

            Log.d("Distance"," "+(mallLoc.distanceTo(Home.getUserLocation())/1000));
            b.setDistance(mallLoc.distanceTo(Home.getUserLocation())/1000);
            if ((mallLoc.distanceTo(Home.getUserLocation())/1000) < Home.getMinDistance() || (mallLoc.distanceTo(Home.getUserLocation())/1000) > Home.getMaxDistance()) {
                bb.add(b);
            }
        }
        babysitters.removeAll(bb);
        populateMap();

    }
}
