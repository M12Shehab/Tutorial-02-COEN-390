package com.my_course_coen390.myapplication.Views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.my_course_coen390.myapplication.R;

import java.util.Locale;

public class Change_Languge extends AbsRuntimePermission {

    RadioButton rdb_English, rdb_French;
    Button btn_save;
    public static final String PREFS_NAME = "MyApp_Settings";
    SharedPreferences preferencesSettings;
    SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_languge);

        preferencesSettings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        preferencesEditor = preferencesSettings.edit();

        rdb_English = findViewById(R.id.rdb_English);
        rdb_French = findViewById(R.id.rdb_French);

        String Lang = preferencesSettings.getString("system_language","fr");

        if(Lang.equals("en"))
        {
            rdb_English.setChecked(true);
            rdb_French.setChecked(false);
        }
        else if (Lang.equals("fr"))
        {
            rdb_English.setChecked(false);
            rdb_French.setChecked(true);
        }

        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_language();
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void change_language() {
        String Lang;

        if (rdb_English.isChecked()) {
            Lang = "en";
            preferencesEditor.putString("system_language", Lang);
            preferencesEditor.commit();

            Locale locale = new Locale(Lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        } else if (rdb_French.isChecked()) {
            Lang = "fr";
            preferencesEditor.putString("system_language", Lang);
            preferencesEditor.commit();

            Locale locale = new Locale(Lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        Toast.makeText(getBaseContext(), R.string.text_error_user, Toast.LENGTH_LONG).show();
        finish();
        restartApp();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        int mPendingIntentId = 13;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, mPendingIntent);
        System.exit(0);
    }
}