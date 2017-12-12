package carsapp.douirimohamedtaha.com.chedliweldi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;

/**
 * Created by oussama_2 on 11/21/2017.
 */

public class RequestRecyclerViewAdapter extends RecyclerView.Adapter<RequestRecyclerViewAdapter.CustomViewHolder> {
    private JSONArray feedItemList;
    private Context mContext;

    public RequestRecyclerViewAdapter(Context context, JSONArray feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        JSONObject feedItem=null;
        try {
             feedItem = feedItemList.getJSONObject(i);
           customViewHolder.request.setText(feedItem.getString("firstName")+" "+feedItem.getString("lastName") +" Send you a request");
           // customViewHolder.description.setText(feedItem.getString("description"));
           Glide.with(mContext).load(AppController.IMAGE_SERVER_ADRESS+feedItem.getString("photo")).into(customViewHolder.image);
           /*
            final String id=feedItem.getString("idOffer");
            customViewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendRequest(LoginActivity.connectedUser,id);
                }
            });
*/

        } catch (JSONException e) {
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


        @Bind(R.id.txtOffer)
        TextView request;


        @Bind(R.id.photo)
        ImageView image;







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
                        Toast.makeText(mContext, "request sent successfully ", Toast.LENGTH_SHORT).show();
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