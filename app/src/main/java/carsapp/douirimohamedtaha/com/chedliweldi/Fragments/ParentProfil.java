package carsapp.douirimohamedtaha.com.chedliweldi.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import carsapp.douirimohamedtaha.com.chedliweldi.Activities.Home;
import carsapp.douirimohamedtaha.com.chedliweldi.AppController;
import carsapp.douirimohamedtaha.com.chedliweldi.R;
import io.ghyeok.stickyswitch.widget.StickySwitch;

import static carsapp.douirimohamedtaha.com.chedliweldi.Fragments.Login.PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParentProfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParentProfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParentProfil extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "First time";
    private static final String ARG_PARAM2 = "param2";
    TextView firstName;
    TextView lastName;
    TextView CIN;
    TextView bDate;
    TextView eMail;
    TextView phoneNumber;
    ImageView profilePic;
    private GoogleMap mMap;
    private Toolbar toolbar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Marker m;
    private SharedPreferences settings;
    private Calendar myCalendar;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParentProfil.
     */
    // TODO: Rename and change types and number of parameters
    public static ParentProfil newInstance(String param1, String param2) {
        ParentProfil fragment = new ParentProfil();
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
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_parent_profil, container, false);
        toolbar =(Toolbar)v.findViewById(R.id.tool_bar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");

// Set Selected Change Listener
        StickySwitch stickySwitch = (StickySwitch)v.findViewById(R.id.sticky_switch);
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                Log.d("ParentProfile", "Now Selected : " + direction.name() + ", Current Text : " + text);
            }
        });
        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        firstName=(TextView)v.findViewById(R.id.txtFirstName);
        lastName=(TextView)v.findViewById(R.id.txtLastName);
        CIN=(TextView)v.findViewById(R.id.txtCIN);
        bDate=(TextView)v.findViewById(R.id.txtDate);
        eMail=(TextView)v.findViewById(R.id.txtEmail);
        phoneNumber=(TextView)v.findViewById(R.id.txtPhoneNumber);
        profilePic =(ImageView)v.findViewById(R.id.profImg);

        settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        firstName.setText(settings.getString("name",null));
        lastName.setText(settings.getString("lastname",null));
        eMail.setText(settings.getString("email",null));
        if(mParam1!=null){
            phoneNumber.setText(settings.getString("phoneNumber",null));
            bDate.setText(settings.getString("birthday",null));
        }

        myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker,int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        bDate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), dateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        Glide.with(getContext()).load(settings.getString("imageUrl",null))
                //.thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilePic);       // holder.image.setImageBitmap(null);





        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    if(m!=null){
                        m.setPosition(latLng);
                    }else
                    {
                        m = mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Home"));
                    }
                }
            });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            updateProfil();
            Intent main = new Intent(getActivity(),Home.class);
            startActivity(main);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateProfil() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = AppController.TAHA_ADRESS + "updateProfile.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    // response
                    Log.d("Response", response);

                },
                error -> {
                    // error
                    Log.d("Error.Response", error.toString());
                }
        ) {
            @Override
            protected java.util.Map<String, String> getParams() {
                java.util.Map<String, String> params = new HashMap<String, String>();
                    params.put("nom",firstName.getText().toString());
                    params.put("prenom", lastName.getText().toString());
                    params.put("email", eMail.getText().toString());
                    params.put("date", bDate.getText().toString());
                    params.put("numtel", phoneNumber.getText().toString());
                 //   params.put("cin", CIN.getText().toString());
                    params.put("imageUrl", settings.getString("imageUrl",null));
                    Log.d("Params", params.values().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        bDate.setText(sdf.format(myCalendar.getTime()));
    }
}
