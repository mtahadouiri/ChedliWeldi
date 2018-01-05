package com.esprit.chedliweldi.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.Entities.Babysitter;
import com.esprit.chedliweldi.Entities.Message;
import com.esprit.chedliweldi.R;

import static com.esprit.chedliweldi.AppController.IMAGE_SERVER_ADRESS;
import static com.esprit.chedliweldi.Fragments.Login.PREFS_NAME;

public class ChatRoom extends AppCompatActivity {

    private MessagesList messagesList;
    private Date lastLoadedDate;
    private MessagesListAdapter<Message> adapter;
    private ArrayList<Message> messages;
    private ImageView callBtn;
    private SharedPreferences settings;

    private String otherId;


    private static final int PERMISSION_REQUEST_CODE = 1;
    private String phoneNumber;
    private MessageInput inputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(getIntent().getStringExtra("fullName"));
        setSupportActionBar(toolbar);
        settings = this.getSharedPreferences(PREFS_NAME, 0);
        otherId = getIntent().getStringExtra("id");
        callBtn = (ImageView) findViewById(R.id.callBtn);
        messages = new ArrayList<>();
        messagesList = (MessagesList) findViewById(R.id.messagesList);
        adapter = new MessagesListAdapter<Message>(settings.getString("id", null), new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(ChatRoom.this).load(IMAGE_SERVER_ADRESS + url).into(imageView);
            }
        });

        getRecievedMessages(otherId,settings.getString("id", null));

        callBtn.setActivated(false);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (ActivityCompat.checkSelfPermission(ChatRoom.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(ChatRoom.this, new String[]{Manifest.permission.CALL_PHONE},
                            PERMISSION_REQUEST_CODE);
                    return;
                }
                startActivity(intent);
            }
        });
        inputView = (MessageInput) findViewById(R.id.input);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                Message message = new Message();
                message.setUser(new Babysitter(settings.getString("firstname", null),
                        settings.getString("lastname", null),
                        settings.getString("image", null),
                        Integer.parseInt(settings.getString("id", null))));
                message.setText(String.valueOf(input));
                message.setCreatedAt(Calendar.getInstance().getTime());
                insertMessage(message);
                //messages.add(message);

                return true;
            }
        });
    }

    private void insertMessage(Message message) {
        messages = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS + "newMessage";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);
                    ArrayList<Message>msgs = new ArrayList<>();
                    msgs.add(message);
                    adapter.addToEnd(msgs, false);
                },
                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sender_id", "" + settings.getString("id", null));
                params.put("reciever_id", "" + otherId);
                params.put("body", "" + message.getText());
                Log.d("Params", params.values().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

    /*protected void loadMessages() {
        new Handler().postDelayed(new Runnable() { //imitation of internet connection
            @Override
            public void run() {
                ArrayList<Message> messages = getRecievedMessages();
                lastLoadedDate = messages.get(messages.size() - 1).getCreatedAt();
                adapter.addToEnd(messages, false);
            }
        }, 1000);
    }*/
    private void getRecievedMessages(String sender_id, String reciever_id) {
        messages = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS + "getMessages";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);
                    try {
                        JSONArray dialogsJsonArray = new JSONArray(response);
                        phoneNumber = dialogsJsonArray.getJSONObject(0).getString("phoneNumber");
                        callBtn.setActivated(true);
                        for (int i = 0; i < dialogsJsonArray.length(); i++) {
                            JSONObject obj = dialogsJsonArray.getJSONObject(i);
                            Message message = new Message();
                            Babysitter b = new Babysitter();
                            b.setId(2);
                            b.setFirstName("Taha");
                            b.setFirstName("T");

                            b.setImgURL(obj.getString("photoUrl"));
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //format.setTimeZone(TimeZone.getTimeZone("UTC"));
                            Date date = format.parse(obj.getString("date"));
                            message.setCreatedAt(date);
                            message.setText(obj.getString("body"));
                            message.setUser(b);
                            //adapter.addToStart(message, true);
                            messages.add(message);
                        }
                        getSentMessages(reciever_id, sender_id);

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
                params.put("sender_id", "" + sender_id);
                params.put("reciever_id", "" + reciever_id);
                Log.d("Params", params.values().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void getSentMessages(String sender_id, String reciever_id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS + "getMessages";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);
                    try {
                        JSONArray dialogsJsonArray = new JSONArray(response);
                        for (int i = 0; i < dialogsJsonArray.length(); i++) {
                            //  messages=new ArrayList<>();
                            JSONObject obj = dialogsJsonArray.getJSONObject(i);
                            Message message = new Message();
                            Babysitter b = new Babysitter();
                            b.setId(30);
                            b.setFirstName("Taha");
                            b.setFirstName("T");
                            b.setImgURL("qsd");
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //format.setTimeZone(TimeZone.getTimeZone("UTC"));
                            Date date = format.parse(obj.getString("date"));
                            message.setCreatedAt(date);
                            message.setText(obj.getString("body"));
                            message.setUser(b);
                            //adapter.addToStart(message, true);
                            messages.add(message);
                        }
                        Log.i("Messages", "" + messages.size());
                        Collections.sort(messages);
                        adapter.addToEnd(messages, false);
                        adapter.setDateHeadersFormatter(new DateFormatter.Formatter() {
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
                        adapter.notifyDataSetChanged();
                        messagesList.setAdapter(adapter, false);

                      /*  adapter = new MessagesListAdapter<Message>("2", new ImageLoader() {
                            @Override
                            public void loadImage(ImageView imageView, String url) {
                                Picasso.with(ChatRoom.this).load("http://192.168.1.8/images/"+url).into(imageView);
                            }
                        });
                        adapter.setDateHeadersFormatter(new DateFormatter.Formatter() {
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
                        });*/
                        // adapter.addToEnd(messages,false);
                        //messagesList.setAdapter(adapter,false);
                    } catch (JSONException e) {
                        Log.e("JSON Except", e.toString());
                        Collections.sort(messages);
                        adapter.addToEnd(messages, false);
                        adapter.setDateHeadersFormatter(new DateFormatter.Formatter() {
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
                        adapter.notifyDataSetChanged();
                        messagesList.setAdapter(adapter, false);
                    } catch (ParseException e) {
                        Log.e("ParseException", e.toString());
                        Collections.sort(messages);
                        adapter.addToEnd(messages, false);
                        adapter.setDateHeadersFormatter(new DateFormatter.Formatter() {
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
                        adapter.notifyDataSetChanged();
                        messagesList.setAdapter(adapter, false);

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
                params.put("sender_id", "" + sender_id);
                params.put("reciever_id", "" + reciever_id);
                Log.d("Params", params.values().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

}
