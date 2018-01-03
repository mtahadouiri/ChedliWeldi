package com.esprit.chedliweldi.adapters;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esprit.chedliweldi.AppController;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.esprit.chedliweldi.R;

/**
 * Created by oussama_2 on 12/21/2017.
 */

public class OfferAdapter  extends BaseAdapter {

JSONArray data;
Date now = new Date();
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
    SimpleDateFormat dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);

public OfferAdapter(JSONArray data){
    this.data=data;
}


    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return data.getJSONObject(i);
        } catch (JSONException e) {
            return  null;
        }
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


      ViewHolder viewHolder;
        if (view == null) {
           view =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myoffer_item, null);
            viewHolder = new ViewHolder();
            viewHolder. mBadge = (NotificationBadge) view. findViewById(R.id.badge);
            viewHolder.date =(TextView) view.findViewById(R.id.date);
            viewHolder.  status =(TextView) view.findViewById(R.id.status);
            viewHolder. badgePrivate=(LinearLayout) view.findViewById(R.id.badgePrivate);

            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }


        int k = data.length();

        try {
            JSONObject item=data.getJSONObject(i);
            int nbr = item.getInt("requests");


               boolean isPrivate=  item.isNull("requested_babysitter");
            Date start = dateFormatter.parse(item.getString("start"));
            Date end = dateFormatter.parse(item.getString("end"));
            if(!isPrivate){
                viewHolder.badgePrivate.setVisibility(View.VISIBLE);
            }
            if( now.after(start) && now.before(end)){
               viewHolder. status.setText("On going");
               item.put("status","On going");
                viewHolder.   status.setTextColor(ContextCompat.getColor(AppController.getContext(),R.color.mtlcgreen));
            }
            if(item.getString("status").equals("scheduled")){
                viewHolder.   status.setText("Scheduled");
                viewHolder.  status.setTextColor(ContextCompat.getColor(AppController.getContext(),R.color.mtlcOrange));
            }


            if(item.getString("status").equals("pending")){
                viewHolder.   status.setText("pending");
                viewHolder.  status.setTextColor(ContextCompat.getColor(AppController.getContext(),R.color.mtlcyellow));
                viewHolder.mBadge.clear();
                viewHolder.  mBadge.setNumber(nbr);

            }

            dateFormatter.applyPattern("dd/MM/yyyy");
            viewHolder.date.setText(printDifference(now,start));
            dateFormatter.applyPattern("yyyy-MM-dd hh:mm:ss");

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return view;
    }

    class ViewHolder{
        NotificationBadge  mBadge;
        TextView date;
        TextView status;

        LinearLayout badgePrivate;
    }

    public String printDifference(Date startDate, Date endDate) {
        //milliseconds

        SimpleDateFormat dateFormatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        long different = endDate.getTime() - startDate.getTime();
        String result=dateFormatter2.format(endDate);


        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;



        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);

        if(elapsedDays==0&& elapsedHours==0){
            return +elapsedMinutes+" min left"  ;
        }

        if(elapsedDays==0){
            return elapsedHours+" hr left"  ;
        }

        return result;


    }




}
