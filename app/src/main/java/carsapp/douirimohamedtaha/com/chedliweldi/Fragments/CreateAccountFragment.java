package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import carsapp.douirimohamedtaha.com.chedliweldi.Activities.SignUpActivity;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CreateAccountFragment() {
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
    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new CreateAccountFragment();
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

    @Bind(R.id.btnNext)
    Button next ;
    @Bind(R.id.txtEmail)
    EditText email;

    @Bind(R.id.txtPassword)
    EditText password;

    @Bind(R.id.txtRetypePassword)
    EditText retypePassword;



    AwesomeValidation mAwesomeValidation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.ssign_up2, container, false);
        ButterKnife.bind(this,v);
        mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(email, RegexTemplate.NOT_EMPTY,"please enter an email adress");
        mAwesomeValidation.addValidation(email, Patterns.EMAIL_ADDRESS,"please enter a valid email adress");
        mAwesomeValidation.addValidation(password, RegexTemplate.NOT_EMPTY,"please enter a password");
        mAwesomeValidation.addValidation(retypePassword, RegexTemplate.NOT_EMPTY,"please verify your password");
        mAwesomeValidation.addValidation(password,retypePassword,"password mismatch");
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAwesomeValidation.validate()){
                    validateEmail(email.getText().toString());
                }



            }
        });

        return v;


    }

    private void validateEmail(final String email ) {

        Log.e("sdf", "uploadUser:  near volley new request ");

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "sdfsd");
        params.put("password", "test2");
        //  JSONObject jsonObj = new JSONObject(params);


        String url = AppController.SERVER_ADRESS+"validateEmail";
        StringRequest sr = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean d= jsonObject.getBoolean("error");
                    if (d){
                        Log.i("etat","failed");

                        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                                .title("Email Error")
                                .content("email already exist choose another one")
                                .positiveText("ok")
                                .show();
                     //   Toast.makeText(getActivity(), "email already exist", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Log.i("etat","success");



                        SignUpActivity act = (SignUpActivity) getActivity() ;
                        act.email=email;
                        act.password=password.getText().toString();
                        getFragmentManager().beginTransaction().setCustomAnimations(R.animator.enter_from_right,R.animator.exit_to_left,R.animator.enter_from_left,R.animator.exit_to_right).addToBackStack(null).replace(R.id.Fragcontainer,new CreateProfilFragment()).commit();
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




        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);


    }

}
