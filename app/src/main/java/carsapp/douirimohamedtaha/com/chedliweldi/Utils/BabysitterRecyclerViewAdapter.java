package carsapp.douirimohamedtaha.com.chedliweldi.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.Messages;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Babysitter;
import carsapp.douirimohamedtaha.com.chedliweldi.R;

import static carsapp.douirimohamedtaha.com.chedliweldi.AppController.IMAGE_SERVER_ADRESS;

/**
 * Created by PC on 16/11/2017.
 */

public class BabysitterRecyclerViewAdapter extends RecyclerView.Adapter<BabysitterRecyclerViewAdapter.ViewHolder> {

    private List<Babysitter> items;
    private Context mContext;

    public BabysitterRecyclerViewAdapter(List<Babysitter> items, Context context) {
        this.items = items;
        this.mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main_3, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Babysitter item = items.get(position);
        holder.name.setText(item.getFirstName()+" "+item.getLastName());
        holder.location.setText("10 km");
        holder.desc.setText(item.getDescr());
        Picasso.with(mContext).load(item.getImgURL()).into(holder.image);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(Babysitter item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Babysitter item) {
        int position = items.indexOf(item);
        items.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView location;
        public TextView desc;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profImg);
            name = (TextView) itemView.findViewById(R.id.tv_card_main_3_title);
            location=(TextView)itemView.findViewById(R.id.tv_card_main3_subtitle);
            desc = (TextView)itemView.findViewById(R.id.tv_card_main3_details);

        }
    }
}