package com.esprit.chedliweldi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.esprit.chedliweldi.R;

/**
 * Created by oussama_2 on 11/21/2017.
 */

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.CustomViewHolder> {
    private JSONArray feedItemList;

    private java.util.Calendar currentCalender = java.util.Calendar.getInstance(Locale.getDefault());
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    SimpleDateFormat customFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private Context mContext;
    public EventRecyclerViewAdapter(Context context, JSONArray feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        try {

        Date startDate ;
        Date endDate ;

            JSONObject feedItem = feedItemList.getJSONObject(i);
             startDate= dateFormatter.parse(feedItem.getString("start"));
             endDate= dateFormatter.parse(feedItem.getString("end"));
             customFormatter.applyPattern("EE");
             customViewHolder.day.setText(customFormatter.format(startDate));
             customFormatter.applyPattern("dd");
            customViewHolder.number.setText(customFormatter.format(startDate));
            customFormatter.applyPattern("h:m a");
            String strStart=customFormatter.format(startDate);
              String strEnd=customFormatter.format(endDate);

             customViewHolder.hours.setText("from "+strStart+" to "+strEnd);



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Render image using Picasso library
       /*
        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(customViewHolder.imageView);
        }

*/
        //Setting text view title
       // customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.length() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
      //  protected ImageView imageView;
        //protected TextView textView;

        @Bind(R.id.number)
        TextView number;

        @Bind(R.id.hours)
        TextView hours;
        @Bind(R.id.day)
        TextView day;
        @Bind(R.id.text)
        TextView text;








        public CustomViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);



        }
    }



}