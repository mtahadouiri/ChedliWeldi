package com.esprit.chedliweldi.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esprit.chedliweldi.Activities.BabySitterMainActivity;
import com.esprit.chedliweldi.Activities.MainActivity;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Entities.MyItem;
import com.esprit.chedliweldi.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONException;
import org.json.JSONObject;

/**

 * to handle interaction events.
 * Use the {@link ParentMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParentMapFragment extends Fragment implements OnMapReadyCallback {
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



    public ParentMapFragment() {
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
    public static ParentMapFragment newInstance(String param1, String param2) {
        ParentMapFragment fragment = new ParentMapFragment();
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






    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

/*
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
*/

        mClusterManager = new ClusterManager<MyItem>(getContext(), mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
       // mMap.setOnMarkerClickListener(mClusterManager);

        populateMap();
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

    }

    public void populateMap() {
        BabySitterMainActivity a = (BabySitterMainActivity) getActivity();

        for (JSONObject b: a.data) {
            Marker m = null;
            try {
                m = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(b.getDouble("altitude"),b.getDouble("longitude")))
                        .title("Perth"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            m.setTag(b);
            //Log.d("Marker",""+m.getPosition());
            MyItem offsetItem = new MyItem(m.getPosition());
            mClusterManager.addItem(offsetItem);
        }
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


}
