package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.net.Uri;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Babysitter;
import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Dialog;
import carsapp.douirimohamedtaha.com.chedliweldi.Entities.Message;
import carsapp.douirimohamedtaha.com.chedliweldi.Fragments.Chat;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.Utils.BabySittersJSONParser;
import carsapp.douirimohamedtaha.com.chedliweldi.Utils.BabysitterRecyclerViewAdapter;

public class Messages extends AppCompatActivity implements Chat.OnFragmentInteractionListener{
    private Dialog dialog;
    private Message message;
    private Chat.OnFragmentInteractionListener mListener;
    private DialogsList dialogsListView;
    private List<Dialog> dialogs;
    private List<Message> messages;
    private ArrayList<Babysitter> babysitters;
    private DialogsListAdapter dialogsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        dialogs=new ArrayList<>();
        message = new Message("0",new Babysitter("Taha", "Douiri", "https://scontent.ftun5-1.fna.fbcdn.net/v/t1.0-9/16425772_10210635995528840_4542581271681303557_n.jpg?oh=412d980a5d28bff28ba8436d778df384&oe=5AC2463A", "tsaassou@gmail.com", "", null, 0,0, 5),"Hellp");

        dialogsListView = (DialogsList)findViewById(R.id.dialogsList);
        getDialogs(30);
    }

    public List<Dialog> getDialogs(int id) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS + "getDialogs.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);
                    try {
                        JSONArray dialogsJsonArray = new JSONArray(response);
                        for (int i = 0; i < dialogsJsonArray.length(); i++) {
                            babysitters=new ArrayList<>();
                            JSONObject obj = dialogsJsonArray.getJSONObject(i);
                            Dialog dialog = new Dialog();
                            dialog.setId(obj.getString("id"));
                            dialog.setDialogName(obj.getString("fullname"));
                            dialog.setDialogPhoto(obj.getString("photoUrl"));
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //format.setTimeZone(TimeZone.getTimeZone("UTC"));

                            Date date = format.parse(obj.getString("date"));
                            long millis = date.getTime();
                            dialog.setLastMessage(new Message("0",new Babysitter(),obj.getString("body"),date));
                            dialog.setUnreadCount(obj.getInt("count"));
                            babysitters.add(new Babysitter("Taha", "Douiri", obj.getString("photoUrl"), "tsaassou@gmail.com", "", null, 0,0, obj.getInt("id")));
                            dialog.setUsers(babysitters);

                            dialogs.add(dialog);
                        }
                        dialogsListAdapter = new DialogsListAdapter(new com.stfalcon.chatkit.commons.ImageLoader() {
                            @Override
                            public void loadImage(ImageView imageView, String url) {
                                Picasso.with(Messages.this).load("http://192.168.1.5/images/"+url).into(imageView);

                            }
                        });
                        dialogsListAdapter.setDatesFormatter(new DateFormatter.Formatter() {
                            @Override
                            public String format(java.util.Date date) {
                                if (DateFormatter.isToday(date)) {
                                    return DateFormatter.format(date, DateFormatter.Template.TIME);
                                } else if (DateFormatter.isYesterday(date)) {
                                    return "Yesterday";
                                } else {
                                    return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
                                }
                            }
                        });
                        dialogsListAdapter.setItems(dialogs);
                        dialogsListView.setAdapter(dialogsListAdapter,false);
                        dialogsListAdapter.notifyDataSetChanged();
                        Log.d("Dialogs",""+dialogs.size());
                    } catch (JSONException e) {

                    } catch (ParseException e) {

                    }


                },
                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "" + id);
                Log.d("Params", params.values().toString());
                return params;
            }
        };
        queue.add(postRequest);
        return dialogs;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
