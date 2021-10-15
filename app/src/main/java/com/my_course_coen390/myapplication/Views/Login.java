package com.my_course_coen390.myapplication.Views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.my_course_coen390.myapplication.Controllers.SqlLiteUserAdapter;
import com.my_course_coen390.myapplication.Models.User;
import com.my_course_coen390.myapplication.R;

import java.util.List;
import java.util.Locale;

public class Login extends AbsRuntimePermission {

    TextInputEditText txt_username;
    TextInputEditText txt_password;

    TextView textView_change_language;
    Button btn_login;

    final String PREFS_NAME = "MyApp_Settings";
    SharedPreferences preferencesSettings;

    //database code. NOTE: This variable from Controllers
    SqlLiteUserAdapter db;
    final String db_name = "Class02DB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        preferencesSettings  = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        String Lang = preferencesSettings.getString("system_language","en");

        Locale locale = new Locale(Lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);

        // Link items on view with code
        txt_username = findViewById(R.id.txt_userId);
        txt_password = findViewById(R.id.txt_password);
        textView_change_language = findViewById(R.id.txt_change_language);
        btn_login = findViewById(R.id.btn_login);

        // check if the default user is exist or not
        // Note: the default user (email "shehab@live.com", password "Admin@12")
        // Step 01: Check if we create the database or not,
        // if the database not created, create new one and add default user
        init_database();
        // Step 02: now we need to use this database for login process.


        //change system language

        textView_change_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent language_activity = new Intent(getBaseContext(), Change_Languge.class);
                startActivity(language_activity);
                finish();

            }
        });

        // Login process
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                // Step 03: we need to match the email and password to complete the login process successfully
                // I update this code to use the SqlLite database rather than content strings
                // In this case we need to use select command
                String SqlCommand = "select email, password from users " +
                        "where email = '"+ user +"' AND password = '"+ password+"'";
                // be careful I used AND logic because I need both user and password to be correct.
                // This query may return multiple users you need to fix it.
                // We can discuss this point on class ;)
                List<User> users = db.Select(SqlCommand);

                if(users.size() > 0)
                {
                    Intent intent_cam = new Intent(getBaseContext(), OpenCam.class);
                    startActivity(intent_cam);
                }
                else
                {
                    Toast.makeText(getBaseContext(), R.string.text_error_user, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void init_database() {
        try {
            // init the database
            db = new SqlLiteUserAdapter(this, db_name);
            // check if the default user is exist or not
            String sql = "select email, password from users where email = 'shehab@live.com'";
            List<User> users = db.Select(sql);
            if(users.size() > 0)
            {
                // then the default user is created
                Toast.makeText(getBaseContext(),R.string.text_default_user_found,Toast.LENGTH_SHORT).show();
            }
            else
            {
                // let's create it
                Toast.makeText(getBaseContext(),R.string.text_default_user_not_found,Toast.LENGTH_LONG).show();
                User user = new User();
                user.setEmail("shehab@live.com");
                user.setPassword("Admin@12");
                boolean IsSuccess= db.Insert(user);
                if(IsSuccess)
                {
                    Toast.makeText(getBaseContext(),R.string.text_default_user_found,Toast.LENGTH_SHORT).show();
                }
            }
        }
       catch (Exception exception)
       {
           Log.i("Database error:", exception.getMessage());
       }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    @SuppressLint("LongLogTag")
    @Override
    protected void onResume() {
        super.onResume();
        String Lang = preferencesSettings.getString("system_language","en");
        Locale locale = new Locale(Lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Log.w("On Resume function System_Lang",Lang);

    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onPause() {
        super.onPause();
        String Lang = preferencesSettings.getString("system_language","en");
        Locale locale = new Locale(Lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Log.w("On pause function System_Lang",Lang);
    }


}