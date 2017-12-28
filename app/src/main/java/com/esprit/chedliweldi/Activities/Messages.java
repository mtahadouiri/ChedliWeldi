package com.esprit.chedliweldi.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.models.IDialog;
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
import java.util.Map;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Entities.Dialog;
import com.esprit.chedliweldi.Entities.Message;
import com.esprit.chedliweldi.R;

import static com.esprit.chedliweldi.AppController.IMAGE_SERVER_ADRESS;

public class Messages extends AppCompatActivity {
    private Dialog dialog;
    private Message message;
    private DialogsList dialogsListView;
    private List<Dialog> dialogs;
    private List<Message> messages;
    private ArrayList<Babysitter> babysitters;
    private DialogsListAdapter dialogsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle("Messages");
        setSupportActionBar(toolbar);
        dialogs=new ArrayList<>();
       // message = new Message("0",new Babysitter("Taha", "Douiri", "https://scontent.ftun5-1.fna.fbcdn.net/v/t1.0-9/16425772_10210635995528840_4542581271681303557_n.jpg?oh=412d980a5d28bff28ba8436d778df384&oe=5AC2463A", "tsaassou@gmail.com", "", null, 0,0, 5),"Hellp");

        dialogsListView = (DialogsList)findViewById(R.id.dialogsList);
        getDialogs(30);
    }

    public List<Dialog> getDialogs(int id) {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS + "getDialogs";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    try {
                        JSONArray dialogsJsonArray = new JSONArray(response);
                        for (int i = 0; i < dialogsJsonArray.length(); i++) {
                            babysitters=new ArrayList<>();
                            JSONObject obj = dialogsJsonArray.getJSONObject(i);
                            Dialog dialog = new Dialog();
                            dialog.setId(obj.getString("sender_id"));
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
                                Picasso.with(Messages.this).load(IMAGE_SERVER_ADRESS+url).into(imageView);

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
                        dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener() {
                            @Override
                            public void onDialogClick(IDialog dialog) {
                                Intent i = new Intent(Messages.this,ChatRoom.class);
                                i.putExtra("fullName",dialog.getDialogName());
                                i.putExtra("id",dialog.getId());
                                startActivity(i);
                            }
                        });
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
                return params;
            }
        };
        queue.add(postRequest);
        return dialogs;
    }
}
