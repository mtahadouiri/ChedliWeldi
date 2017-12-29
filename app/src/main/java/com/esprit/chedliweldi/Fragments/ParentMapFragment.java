package com.esprit.chedliweldi.Fragments;

/**
 * Created by oussama_2 on 12/1/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.esprit.chedliweldi.R;

/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class ParentMapFragment extends Fragment {



Button choose;

Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.baby_sitter_map, container, false);



        return v;
    }








}
