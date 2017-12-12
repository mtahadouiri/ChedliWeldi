package carsapp.douirimohamedtaha.com.chedliweldi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.LoginActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;

/**
 * Created by oussama_2 on 11/21/2017.
 */

public class OnGoingOfferRecycleViewAdapter extends RecyclerView.Adapter<OnGoingOfferRecycleViewAdapter.CustomViewHolder> {
    private JSONArray feedItemList;
    private Context mContext;

    public OnGoingOfferRecycleViewAdapter(Context context, JSONArray feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ongoing_offer_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        JSONObject feedItem=null;
        try {
             feedItem = feedItemList.getJSONObject(i);
           customViewHolder.fullName.setText("mission by"+feedItem.getString("firstName")+" "+feedItem.getString("lastName"));
            Calendar startTime = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime.setTime(sdf.parse(feedItem.getString("start")));

            sdf = new SimpleDateFormat("E,M dd");

            customViewHolder.date.setText( sdf.format(startTime.getTime()));


            customViewHolder.hours.setText(startTime.get(Calendar.HOUR)+" "+startTime.get(Calendar.AM_PM));


         //   customViewHolder.description.setText(feedItem.getString("description"));
            Glide.with(mContext).load(AppController.IMAGE_SERVER_ADRESS+feedItem.getString("photo")).transform(new AppController.CircleTransform(mContext)).into(customViewHolder.image);



        } catch (Exception e) {
            e.printStackTrace();
        }


        final JSONObject finalFeedItem = feedItem;
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

        @Bind(R.id.photo)
        ImageView image ;

        @Bind(R.id.txtFullName)
        TextView fullName;

        @Bind(R.id.hours)
        TextView hours;

        @Bind(R.id.date)
        TextView date;







        public CustomViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);



        }
    }



}