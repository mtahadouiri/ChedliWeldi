package com.esprit.chedliweldi.Fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.esprit.chedliweldi.Activities.BabySitterMainActivity;
import com.esprit.chedliweldi.Activities.Home;
import com.esprit.chedliweldi.Activities.MainActivity;
import com.esprit.chedliweldi.Activities.MyOfferActivity;
import com.esprit.chedliweldi.Activities.ParentMainActivity;
import com.esprit.chedliweldi.Activities.SignUpActivity;
import com.esprit.chedliweldi.AppController;
import com.esprit.chedliweldi.R;
import com.esprit.chedliweldi.Utils.BabySittersJSONParser;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateProfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CreateProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateProfilFragment newInstance(String param1, String param2) {
        CreateProfilFragment fragment = new CreateProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Bind(R.id.txtFirstName)
    EditText firstName;
    @Bind(R.id.txtLastName)
    EditText lastName;
    @Bind(R.id.txtPhoneNumber)
    EditText phoneNumber;
    @Bind(R.id.adress)
    EditText adress;
    @Bind(R.id.txtBirthDate)
    EditText birthDate;
   
    @Bind(R.id.btnNext)
    Button create ;

    @Bind(R.id.imgMale)
    ImageView imgMale ;

    @Bind(R.id.imgFemale)
    ImageView imgFemale ;

boolean maleChecked=false;
    AwesomeValidation mAwesomeValidation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.sign_up3, container, false);
        ButterKnife.bind(this,v);
        mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(phoneNumber,RegexTemplate.NOT_EMPTY,"please enter a phone number");
        mAwesomeValidation.addValidation(adress,RegexTemplate.NOT_EMPTY,"please enter an adress");
        mAwesomeValidation.addValidation(phoneNumber,RegexTemplate.TELEPHONE,"please enter a valid phone number");
        mAwesomeValidation.addValidation(firstName,"[a-zA-Z\\s]+","please enter a valid name");
        mAwesomeValidation.addValidation(lastName,"[a-zA-Z\\s]+","please enter a valid name");
        mAwesomeValidation.addValidation(lastName,"[a-zA-Z\\s]+","please enter a valid name");
        mAwesomeValidation.addValidation(birthDate,"[0-9]{2}\\[0-9]{2}\\[0-9]{4}","please enter a valid date");


        //
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*




*/
          if(   mAwesomeValidation.validate()){
              SignUpActivity s = (SignUpActivity) getActivity();
              signUp(s.email,s.password,Integer.parseInt(phoneNumber.getText().toString()),firstName.getText().toString(),lastName.getText().toString(),adress.getText().toString(),birthDate.getText().toString(),s.type);

          };
            }
        });


      birthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          @Override
          public void onFocusChange(View view, boolean b) {
              if(b){
                  datePicker();

              }
          }
      });

        imgMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgMale.setImageResource(R.drawable.male_selected);
                imgFemale.setImageResource(R.drawable.female);
            }
        });


        imgFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgMale.setImageResource(R.drawable.male);
                imgFemale.setImageResource(R.drawable.female_selected);
            }
        });
        return v;


    }


    private void signUp(final String email , String password, int phoneNumber, String firstName, String lastName , String adress , String birthDate, String type ) {


        Log.e("sdf", "uploadUser:  near volley new request ");

        final Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("adress", adress);
        params.put("birthDate", birthDate);
        params.put("type", type);
        params.put("phoneNumber", String.valueOf(phoneNumber));
        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"register";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){
                        //oops T
                        Toast.makeText(getActivity(), "error ", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        getUserId(email);


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


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                headers.put("abc", "value");
                return headers;
            }
        };




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }


    private void getUserId(final String email ) {


        Log.e("sdf", "uploadUser:  near volley new request ");

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "sdfsd");
        params.put("password", "test2");
        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"getUserId";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){




                    }
                    else{
                        Log.i("etat","success");
                        String ff=jsonObject.getString("user_id");



                        Login.connectedUser=ff;
                        SignUpActivity s = (SignUpActivity) getActivity();
                        Login.type=s.type;
                        if(Login.type.equals("Babysitter")){
                            showListoffers();
                            //  showParentMain();
                        }
                        else {
                            getBabysiiters();
                        }



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
                params.put("email", email);


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

        AppController.getInstance().addToRequestQueue(sr);

    }


    private void datePicker(){

         // Get Current Date
         final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int  mDay = c.get(Calendar.DAY_OF_MONTH);

         DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                 new DatePickerDialog.OnDateSetListener() {

                     @Override
                     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                      String  date_time = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                         birthDate.setText(date_time);
                         //*************Call Time Picker Here ********************

                     }
                 }, mYear, mMonth, mDay);
         datePickerDialog.show();
     }

    void showMyoffer(){
        Intent i = new Intent(getActivity(),MyOfferActivity.class);
        startActivity(i);
    }

    void showListoffers(){
        Intent i = new Intent(getActivity(),BabySitterMainActivity.class);
        startActivity(i);
    }

    private void getBabysiiters() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url =  AppController.TAHA_ADRESS+"getBabysitters";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        MainActivity.bbySitters= BabySittersJSONParser.parseData(response);
                        Intent i = new Intent(getContext(), Home.class);
                        startActivity(i);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",""+ error.getMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public void showParentMain(){
        Intent i = new Intent(getActivity(),ParentMainActivity.class);
        startActivity(i);
    }
}
