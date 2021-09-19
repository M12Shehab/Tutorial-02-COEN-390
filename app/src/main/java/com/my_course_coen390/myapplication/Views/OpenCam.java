package com.my_course_coen390.myapplication.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.my_course_coen390.myapplication.R;
import com.my_course_coen390.myapplication.Views.AbsRuntimePermission;

public class OpenCam extends AbsRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    final int CAMERA_INTENT_CALL = 1;
    Button btn_Cam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cam);

        requestAppPermissions(new String[]{
                        Manifest.permission.CAMERA
                },
                R.string.text_Enable, REQUEST_PERMISSION);

        btn_Cam = findViewById(R.id.btn_open_cam);

        btn_Cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(i);
//                startActivityForResult(i, CAMERA_INTENT_CALL);
            }
        });

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}