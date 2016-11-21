package com.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.opengl.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSupportES2(this)) {
            setContentView(R.layout.activity_main);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.MainContainer, new MainFragment())
                    .commit();
        } else {
            Toast.makeText(this, "不支持OpenGL ES 2.0", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkSupportES2(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportsES2 = deviceConfigurationInfo.reqGlEsVersion >= 0x20000
                || Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86");
        return supportsES2;
    }


}
