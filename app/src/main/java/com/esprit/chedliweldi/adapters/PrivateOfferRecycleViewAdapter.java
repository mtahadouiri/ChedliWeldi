package com.esprit.chedliweldi.adapters;

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
import com.esprit.chedliweldi.Activities.LoginActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.DateUtility;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by oussama_2 on 11/21/2017.
 */

public class PrivateOfferRecycleViewAdapter extends RecyclerView.Adapter<PrivateOfferRecycleViewAdapter.CustomViewHolder> {
    private JSONArray feedItemList;
    private Context mContext;

    public PrivateOfferRecycleViewAdapter(Context ctx ,JSONArray feedItemList) {
        this.feedItemList = feedItemList;
        mContext=ctx;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.private_offer_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        JSONObject feedItem=null;
        try {
             feedItem = feedItemList.getJSONObject(i);
             Date d  = DateUtility.TimeStampFormatter.parse(feedItem.getString("createDate"));
            customViewHolder.createDate.setText(DateUtility.printDifference(d,new Date()));
           customViewHolder.fullName.setText(feedItem.getString("firstName")+" "+feedItem.getString("lastName"));
           //customViewHolder.distance.setText(feedItem.getInt("distance")+" km");
            customViewHolder.description.setText(feedItem.getString("description"));
            Glide.with(mContext).load(AppController.IMAGE_SERVER_ADRESS+feedItem.getString("photo")).transform(new AppController.CircleTransform(mContext)).into(customViewHolder.image);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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
        @Bind(R.id.txtDesc)
        TextView description;
        @Bind(R.id.txtCreateDate)
        TextView createDate;




        public CustomViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);



        }
    }
    private void sendRequest(final String idUser, final String idOffer ) {


        Log.e("sdf", "uploadUser:  near volley new request ");



        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"sendRequest";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){
                        Log.i("etat","failed");
                        Toast.makeText(mContext, "error request ", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.i("etat","success");
                        MaterialDialog dialog = new MaterialDialog.Builder(mContext)
                                .title("request send")
                                .content("the request has been sent successfully")
                                .positiveText("ok")
                                .show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
                Log.d("", ""+error.getMessage()+","+error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", idUser);
                params.put("id_offer", idOffer);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                //  headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }


}