package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Babysitter;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.Login;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.SettingsFragment;
import carsapp.douirimohamedtaha.com.chedliweldi.R;

public class SettingActivity extends AppCompatActivity  {
    private FragmentManager fragmentManager;
    private Fragment fragment;
    public static List<Babysitter> bbySitters;
  public   Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
