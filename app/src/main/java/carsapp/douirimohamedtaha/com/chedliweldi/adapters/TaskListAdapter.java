package carsapp.douirimohamedtaha.com.chedliweldi.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Task;
import carsapp.douirimohamedtaha.com.chedliweldi.R;

/**
 * Created by Taha on 23/12/2017.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> items;
    private Context mContext;
    public static List<Drawable> lstImages=new ArrayList<>();

    public TaskListAdapter(List<Task> items, Context context) {
        this.items = items;
        this.mContext=context;
        lstImages.add(ContextCompat.getDrawable(mContext,R.drawable.ic_task_red));
        lstImages.add(ContextCompat.getDrawable(mContext,R.drawable.ic_task_green));
        lstImages.add(ContextCompat.getDrawable(mContext,R.drawable.ic_task_orange));
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
        holder.details.setText(item.getTime());
        Random r = new Random();
        int randomInt = r.nextInt(3);
        holder.image.setImageDrawable(lstImages.get(randomInt));
        holder.itemView.setTag(item);
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
        public TextView name;
        public TextView details;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imgTask);
            name = (TextView) itemView.findViewById(R.id.txtTaskDetails);
            details =(TextView)itemView.findViewById(R.id.txtTaskDetails);
            time = (TextView)itemView.findViewById(R.id.txtTaskTime);
        }
    }
}
