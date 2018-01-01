package com.esprit.chedliweldi.Fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.esprit.chedliweldi.Activities.Home;
import com.esprit.chedliweldi.Activities.MainActivity;
import com.esprit.chedliweldi.Activities.ProfilActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.BabySittersJSONParser;
import com.esprit.chedliweldi.Utils.BabysitterRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Feed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Feed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public List<Babysitter> babysitters = new ArrayList<>();
    public RecyclerView rv;
    public BabysitterRecyclerViewAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Feed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Feed.
     */
    // TODO: Rename and change types and number of parameters
    public static Feed newInstance(String param1, String param2) {
        Feed fragment = new Feed();
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
            // Instantiate the RequestQueue.

        }
        getBabysiiters();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        //setUpRecyclerView();
        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        /*adapter=new BabysitterRecyclerViewAdapter(MainActivity.bbySitters,getContext());
        rv.setAdapter(adapter);*/
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
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = AppController.TAHA_ADRESS+"getBabysitters";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        babysitters= BabySittersJSONParser.parseData(response);
                        MainActivity.bbySitters=babysitters;
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
        Log.d("Babysitters",babysitters.size()+"");
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
        Collections.sort(babysitters, new Comparator<Babysitter>() {
            @Override
            public int compare(Babysitter babysitter, Babysitter t1) {

                return Float.compare(babysitter.getDistance(),t1.getDistance());
            }
        });
        adapter=new BabysitterRecyclerViewAdapter(babysitters, getContext(), new BabysitterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Babysitter item) {
                Intent i = new Intent(getContext(), ProfilActivity.class);
                i.putExtra("user", (Parcelable) item);
                startActivity(i);
            }
        });
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);

    }

}
