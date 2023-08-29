package com.example.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageButton toggleButton;

    boolean hasCameraFlash = false;
    boolean flashon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.toggleButton);
        
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hasCameraFlash){
                    if(flashon) {
                        flashon = false;
                        toggleButton.setImageResource(R.drawable.power_off);
                        try {
                            flashLightOff();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else{
                        flashon = true;
                        toggleButton.setImageResource(R.drawable.power_on);
                        try {
                            flashlighton();
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "NO FLASH AVAILABLE ON THIS DEVICE", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void flashlighton() throws CameraAccessException {
        CameraManager cameramanager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameramanager = (CameraManager)getSystemService(Context.CAMERA_SERVICE);
        }
        String cameraId = cameramanager.getCameraIdList()[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameramanager.setTorchMode(cameraId,true);
        }
        Toast.makeText(MainActivity.this, "Flashlight is ON", Toast.LENGTH_SHORT).show();
    }
    private void flashLightOff() throws CameraAccessException {
        CameraManager cameramanager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameramanager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        }
        assert cameramanager != null;
        String cameraId = (cameramanager != null ? cameramanager.getCameraIdList() : new String[0])[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameramanager.setTorchMode(cameraId, false);
        }
        Toast.makeText(MainActivity.this, "Flashlight is OFF", Toast.LENGTH_SHORT).show();
    }
}