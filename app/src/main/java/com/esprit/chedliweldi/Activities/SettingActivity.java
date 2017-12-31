package com.esprit.chedliweldi.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Fragments.SettingsFragment;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.DrawerInitializer;

public class SettingActivity extends AppCompatActivity  {
    private FragmentManager fragmentManager;
    private Fragment fragment;
    public static List<Babysitter> bbySitters;
  public   Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
     //   DrawerInitializer.initView(this);



        // Making notification bar transparent

        fragmentManager = getSupportFragmentManager();
        fragment = new SettingsFragment();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
