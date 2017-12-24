package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Task;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.Utils.BabysitterRecyclerViewAdapter;
import carsapp.douirimohamedtaha.com.chedliweldi.adapters.TaskListAdapter;

public class CreateTaskListForJob extends AppCompatActivity {
    String taskName,taskDetails,taskTime;
    TextView txtDate;
    List<Task> taskList;
    Task task;
    RecyclerView rv ;
    TaskListAdapter adapter;
    FloatingActionButton fab ;
    FloatingActionButton fabDone ;
    private int id;
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        id=extras.getInt("id");
        date=extras.getString("date");
        setContentView(R.layout.activity_create_task_list_for_job);
        txtDate=(TextView)findViewById(R.id.txtDate);
        txtDate.setText(date);
        taskList = new ArrayList<>();
        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter=new TaskListAdapter(taskList,this);
        rv.setAdapter(adapter);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fabDone = (FloatingActionButton)findViewById(R.id.fabDone);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLangDialog();
            }
        });
        fabDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
            }
        });
    }

    private void goHome() {
        Intent i =new Intent(CreateTaskListForJob.this,Home.class);
        startActivity(i);
    }


    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.add_task_custom, null);
        dialogBuilder.setView(dialogView);

        final EditText name = (EditText) dialogView.findViewById(R.id.txtTaskTilte);
        final EditText details = (EditText) dialogView.findViewById(R.id.txtTaskDetail);
        final TextView time = (TextView) dialogView.findViewById(R.id.txtTaskTime);

        dialogBuilder.setTitle("Add task");
        dialogBuilder.setMessage("Enter task details below");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                taskName=name.getText().toString();
                taskDetails=details.getText().toString();
                taskTime=time.getText().toString();
                task=new Task(taskName,taskDetails,taskTime);
                addTaskToServer(id,taskName,taskDetails,taskTime);
                taskList.add(task);
                fabDone.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addTaskToServer(int id,String taskName, String taskDetails, String taskTime) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS + "AddTask.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);
                    task.setId(response);

                },
                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idJob", "" + id);
                params.put("task", taskName);
                params.put("details", taskDetails);
                params.put("time", taskTime);
                Log.d("Params", params.values().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }
}
