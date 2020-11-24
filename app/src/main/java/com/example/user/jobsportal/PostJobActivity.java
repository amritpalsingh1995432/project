package com.example.user.jobsportal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostJobActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmitJob;
    EditText edtJobTitle;
    EditText edtJobDescription;
    EditText edtJobTimings;
    EditText edtJobAddress;

    Log_Information data = new Log_Information();
    String employer = data.getLogged_in_user ();

    FirebaseFirestore db;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_post_job);

        edtJobTitle = findViewById(R.id.edtJobTitle);
        edtJobDescription = findViewById (R.id.edtJobDescription);
        edtJobTimings = findViewById (R.id.edtJobTimings);
        edtJobAddress = findViewById (R.id.edtJobAddress);

        btnSubmitJob = findViewById (R.id.btnSubmitJob);
        btnSubmitJob.setOnClickListener (this);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("users").document();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnSubmitJob.getId()){

            //String data = edtJobTitle.getText().toString()+"\n"+
            //edtJobDescription.getText().toString()+"\n"+edtJobTimings.getText().toString()+"\n"+edtJobAddress.getText ().toString ();
            //Toast.makeText(this,data,Toast.LENGTH_LONG).show();

            String job_title = edtJobTitle.getText().toString();
            String job_description = edtJobDescription.getText().toString();
            String job_type = edtJobTimings.getText().toString();
            String job_address = edtJobAddress.getText ().toString();


            if(validate ()) {
                insertData (job_title,job_description,job_type,job_address,employer);
                //displaydata ( );
                Intent homeIntent = new Intent (this, EmployerHomeActivity.class);
                startActivity (homeIntent);
            }
            else {
                Toast.makeText(this,"Enter proper details.",Toast.LENGTH_LONG).show();
            }

        }

    }

    private void insertData(final String job_title, final String job_description, final String job_type, final String job_address, final  String employer){
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("Job_Title", job_title);
                    reg_entry.put("Job_Description", job_description);
                    reg_entry.put("Type", job_type);
                    reg_entry.put("Address",job_address);
                    reg_entry.put("Posted_by",employer);

                    db.collection("jobs")
                            .add(reg_entry)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("Error", e.getMessage());
                                }
                            });
                }
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String job_title = edtJobTitle.getText().toString();
        String job_description = edtJobDescription.getText().toString();
        String job_type = edtJobTimings.getText().toString();
        String job_address = edtJobAddress.getText ().toString();

        if (job_title.isEmpty()) {
            edtJobTitle.setError("Enter Job Title");
            valid = false;
        } else {
            edtJobTitle.setError(null);
        }

        if (job_description.isEmpty()) {
            edtJobDescription.setError("Enter Job Description");
            valid = false;
        } else {
            edtJobDescription.setError(null);
        }

        if (job_type.isEmpty()) {
            edtJobTimings.setError("Enter Job Type");
            valid = false;
        } else {
            edtJobTimings.setError(null);
        }

        if (job_address.isEmpty()) {
            edtJobAddress.setError("Enter Job Address");
            valid = false;
        } else {
            edtJobAddress.setError(null);
        }

        return valid;
    }
}
