package com.esprit.chedliweldi.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.chedliweldi.Activities.Home;
import com.esprit.chedliweldi.Activities.OnGoing;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Task;
import com.esprit.chedliweldi.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.esprit.chedliweldi.AppController.getContext;
import static com.esprit.chedliweldi.Fragments.Login.PREFS_NAME;
import static com.esprit.chedliweldi.Fragments.Login.type;

/**
 * Created by Taha on 23/12/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> items;
    private Context mContext;
    public List<Drawable> lstImages = new ArrayList<>();
    private boolean a;

    public TaskListAdapter(List<Task> items, Context context) {
        this.items = items;
        this.mContext = context;
        lstImages.add(ContextCompat.getDrawable(mContext, R.drawable.ic_task_red));
        lstImages.add(ContextCompat.getDrawable(mContext, R.drawable.ic_task_green));
        lstImages.add(ContextCompat.getDrawable(mContext, R.drawable.ic_task_orange));
    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_item_task, parent, false);
        return new TaskListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.ViewHolder holder, int position) {
        Task item = items.get(position);
        holder.name.setText(item.getName());
        holder.details.setText(item.getDetails());
        holder.time.setText(item.getTime());
        Random r = new Random();
        int randomInt = r.nextInt(3);
        holder.image.setImageDrawable(lstImages.get(randomInt));
        if(item.getState().equals("0")){
            holder.avLoadingIndicatorView.show();
            holder.done.setVisibility(View.GONE);
        }else {
            holder.avLoadingIndicatorView.hide();
            holder.done.setVisibility(View.VISIBLE);
        }
        holder.itemView.setTag(item);
        holder.avLoadingIndicatorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!type.equals("Parent")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Have you done this task ?")
                            .setPositiveButton("Yes !", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                    if(UpdateTaskDone(item.getId())){
                                        holder.avLoadingIndicatorView.hide();
                                        holder.done.setVisibility(View.VISIBLE);
                                    }
                                }
                            })
                            .setNegativeButton("Not yet", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
            }
        });
    }

    private boolean UpdateTaskDone(String id) {
        a = false;
        SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        Log.d("ID", settings.getString("id", null));
        String url;
        url = AppController.TAHA_ADRESS + "updateTask?id=" + id;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JSONArray task_array = null;
                        Task task = null;
                        try {
                            JSONObject obj = new JSONObject(response);
                            Log.d("Job", obj.getString("status"));
                            if (obj.getString("status").equals("success")) {
                                a = true;
                            }
                        } catch (JSONException e) {

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
        return a;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(Task item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Task item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView done;
        public TextView name;
        public TextView details;
        public TextView time;
        public AVLoadingIndicatorView avLoadingIndicatorView;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imgTask);
            done=(ImageView)itemView.findViewById(R.id.imgDone);
            name = (TextView) itemView.findViewById(R.id.txtTaskname);
            details = (TextView) itemView.findViewById(R.id.txtTaskDetails);
            time = (TextView) itemView.findViewById(R.id.txtTaskTime);
            avLoadingIndicatorView = (AVLoadingIndicatorView) itemView.findViewById(R.id.avi);
        }
    }
}
