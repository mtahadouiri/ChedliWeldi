package com.esprit.chedliweldi.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import butterknife.Bind;
import com.esprit.chedliweldi.Fragments.Login;
import com.esprit.chedliweldi.Fragments.ParentProfil;
import com.esprit.chedliweldi.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.esprit.chedliweldi.Fragments.Login.PREFS_NAME;

public class LoginActivity extends AppCompatActivity implements ParentProfil.OnFragmentInteractionListener{

    @Bind(R.id.btnNext)
    Button btnSignIn ;
    @Bind(R.id.etEmail)
    EditText email ;
    @Bind(R.id.etPassword)
    EditText password ;
    @Bind(R.id.txtSignUp)
    TextView txtSignUp ;


   public static  String connectedUser="4";


    public void validateLogin(String email ,String password){




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_holder);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()

                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String id =settings.getString("id",null);
        if(id==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new Login()).commit();
        }
        else{

        }







    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
