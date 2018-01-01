package com.esprit.chedliweldi.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.esprit.chedliweldi.Entities.Task;
import com.esprit.chedliweldi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Taha on 23/12/2017.
 */

public class DisplayTaskListAdapter extends RecyclerView.Adapter<DisplayTaskListAdapter.ViewHolder> {

    private JSONArray items;


    public DisplayTaskListAdapter(JSONArray items) {
        this.items = items;


    }

    @Override
    public DisplayTaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_itm, parent, false);
        return new DisplayTaskListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DisplayTaskListAdapter.ViewHolder holder, int position) {
        JSONObject item = null;
        try {
            item = items.getJSONObject(position);
            holder.name.setText(item.getString("task"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return items.length();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imgTask);
            name = (TextView) itemView.findViewById(R.id.text);
            Log.i("sdf","sdf");
        }
    }
}
