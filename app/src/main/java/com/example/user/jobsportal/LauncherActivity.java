package com.example.user.jobsportal;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        final Context context=this;


        Thread timer=new Thread()
        {
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }
                catch (Exception ex){
                    Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                }
                finally {
                    finish();
                    startActivity(new Intent(context,SignUpActivity.class));
                }
            }
        };
        timer.start();
    }
}
