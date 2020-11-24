package com.example.user.jobsportal;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class UserManualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user_manual);

        WebView webManual = findViewById(R.id.webUserManual);
        webManual.loadUrl("file:///android_asset/JobsPortal.html");
		
    }
}
