package com.my_course_coen390.myapplication.Views;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.material.textfield.TextInputEditText;
import com.my_course_coen390.myapplication.R;

import java.util.Locale;

public class Login extends AbsRuntimePermission {

    TextInputEditText txt_username;
    TextInputEditText txt_password;

    TextView textView_change_language;
    Button btn_login;

    final String PREFS_NAME = "MyApp_Settings";
    SharedPreferences preferencesSettings;

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

        txt_username = findViewById(R.id.txt_userId);
        txt_password = findViewById(R.id.txt_password);
        textView_change_language = findViewById(R.id.txt_change_language);

        btn_login = findViewById(R.id.btn_login);

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

                if(user.equals("Shehab") && password.equals("Admin@12"))
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