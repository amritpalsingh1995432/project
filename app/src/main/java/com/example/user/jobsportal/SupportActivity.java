package com.example.user.jobsportal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnCall, btnSMS, btnEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);

        btnSMS = findViewById(R.id.btnSMS);
        btnSMS.setOnClickListener(this);

        btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCall:
                makeCall();
                break;
            case R.id.btnSMS:
                sendSMS();
                break;
            case R.id.btnEmail:
                sendEmail();
                break;
        }
    }

    private void makeCall(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:1234567890"));

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),
                    "Call Permission Denied", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(callIntent);
    }

    private void sendSMS(){
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:1234567890"));
        smsIntent.putExtra("sms_body", "This is test message");

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),
                    "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(smsIntent);

    }

    private void sendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"amritdhaliwal707@gmail.com"});

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Test Email");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "This is a test message from  DATABASE DESIGN II section 00415");

        emailIntent.setType("*/*");

        startActivity(Intent.createChooser(emailIntent, "Select email account"));
    }
}