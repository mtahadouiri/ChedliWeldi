package com.esprit.chedliweldi.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myoffer_item, null);
        NotificationBadge  mBadge;
        TextView date;
        int k = data.length();
        try {
            JSONObject item=data.getJSONObject(i);
            int nbr = item.getInt("requests");
            mBadge = (NotificationBadge) v. findViewById(R.id.badge);
            date =(TextView) v.findViewById(R.id.date);
            Date d = dateFormatter.parse(item.getString("date"));
            dateFormatter.applyPattern("dd/MM/yyyy");
            date.setText(dateFormatter.format(d));
            dateFormatter.applyPattern("yyyy-MM-dd");
            mBadge.setNumber(nbr);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return v;
    }
}
