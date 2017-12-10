package carsapp.douirimohamedtaha.com.chedliweldi.Activities;

import android.content.Intent;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.dd.morphingbutton.MorphingButton;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import carsapp.douirimohamedtaha.com.chedliweldi.Utils.BabySittersJSONParser;

public class AddJob extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private LinearLayout cardView1;
    private TextView timeFrom, timeTo;
    private EditText title, descr;
    private Button addJob;
    private AppBarLayout appBarLayout;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);
    private final SimpleDateFormat dateFormatSQL = new SimpleDateFormat("YYYY-MM-dd", /*Locale.getDefault()*/Locale.ENGLISH);


    private CompactCalendarView compactCalendarView;

    private MorphingButton btnMorph;
    private boolean isExpanded = false;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_app_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("New job");
        cardView1 = (LinearLayout) findViewById(R.id.linearLayoutTime);
        cardView1.setTag("cardView1");

        title = (EditText) findViewById(R.id.txtTitle);
        descr = (EditText) findViewById(R.id.txtDescription);
        timeFrom = (TextView) findViewById(R.id.txtFrom);
        timeTo = (TextView) findViewById(R.id.txtTo);
        addJob = (Button) findViewById(R.id.btnAddJob);
        btnMorph=(MorphingButton)findViewById(R.id.morphing);
        morphToSquare(btnMorph, 0);

        btnMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((title.getText().toString().trim().length())<=0){
                        morphToFailure(btnMorph,integer(R.integer.mb_animation));
                }
                else{
                    AddJobPost(4, selectedDate, title.getText().toString(), descr.getText().toString(), timeFrom.getText().toString(), timeTo.getText().toString());
                }

            }
        });

       /* addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morphToSquare(btnMorph, integer(R.integer.mb_animation));
                AddJobPost(4, selectedDate, title.getText().toString(), descr.getText().toString(), timeFrom.getText().toString(), timeTo.getText().toString());
            }
        });*/

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        AddJob.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        false
                );
                tpd.setStartTime(8, 00);
                tpd.setEndTime(18, 15);
                tpd.setCancelable(true);
                tpd.setUserVisibleHint(true);
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });


        appBarLayout = findViewById(R.id.app_bar_layout);

        // Set up the CompactCalendarView
        compactCalendarView = findViewById(R.id.compactcalendar_view);

        // Force English
        compactCalendarView.setLocale(TimeZone.getDefault(), /*Locale.getDefault()*/Locale.ENGLISH);

        compactCalendarView.setShouldDrawDaysHeader(true);

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                selectedDate = dateFormatSQL.format(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        final ImageView arrow = findViewById(R.id.date_picker_arrow);

        RelativeLayout datePickerButton = findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(v -> {
            float rotation = isExpanded ? 0 : 180;
            ViewCompat.animate(arrow).rotation(rotation).start();

            isExpanded = !isExpanded;
            appBarLayout.setExpanded(isExpanded, true);
        });

    }

    private void AddJobPost(int parentId, String date, String title, String descr, String from, String to) {
        Log.d("Date", "" + date);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AppController.TAHA_ADRESS+"AddJob.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);
                    morphToSuccess(btnMorph);

                },
                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                    morphToFailure(btnMorph,1);
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id",""+parentId);
                params.put("desc", descr);
                params.put("date", date);
                params.put("from", from);
                params.put("to", to);
                params.put("title", title);

                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String hourStringEnd = hourOfDayEnd < 10 ? "0" + hourOfDayEnd : "" + hourOfDayEnd;
        String minuteStringEnd = minuteEnd < 10 ? "0" + minuteEnd : "" + minuteEnd;
        timeFrom.setText(hourString + ":" + minuteString);
        timeTo.setText(hourStringEnd + ":" + minuteStringEnd);
    }

    private void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));
        selectedDate = dateFormatSQL.format(date);

        if (compactCalendarView != null) {
            compactCalendarView.setCurrentDate(date);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = findViewById(R.id.title);

        if (tvTitle != null) {
            tvTitle.setText(title);
        }
    }

    private void setSubtitle(String subtitle) {
        TextView datePickerTextView = findViewById(R.id.date_picker_text_view);

        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
        }
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_200))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_blue))
                .colorPressed(color(R.color.mb_blue_dark))
                .text("Add");
        btnMorph.morph(square);
    }

    private void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.green_primary))
                .colorPressed(color(R.color.green_primary_dark))
                .icon(R.drawable.ic_check);
        btnMorph.morph(circle);
        btnMorph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("morph","succes click");
            }
        });
    }

    private void morphToFailure(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.red_primary))
                .colorPressed(color(R.color.red_primary_dark))
                .icon(R.drawable.ic_close_white_24dp);
        btnMorph.morph(circle);
    }
    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }
}
