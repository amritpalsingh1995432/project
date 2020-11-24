package com.example.user.jobsportal;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSignUp;
    EditText edtName;
    EditText edtEmail;
    EditText edtPassword;
    RadioGroup rdoAccountType;
    RadioButton rdoJobseeker, rdoEmployer;
    String profile_type = "";

    FirebaseFirestore db;
    DocumentReference ref;
    Log_Information data = new Log_Information();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName=findViewById(R.id.edtName);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);

        btnSignUp=findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        rdoJobseeker = findViewById(R.id.rdoJobseeker);
        rdoJobseeker.setOnClickListener(this);

        rdoEmployer = findViewById(R.id.rdoEmployer);
        rdoEmployer.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
        ref = db.collection("users").document();

    }

    @Override
    public void onClick(View view) {


        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if(rdoEmployer.isChecked()){
            profile_type = "Employer";
        }
        else if(rdoJobseeker.isChecked()){
            profile_type = "Jobseeker";
        }

        if(view.getId() == btnSignUp.getId()){

//            String data = edtName.getText().toString()+"\n"+
//            edtEmail.getText().toString()+"\n"+edtPassword.getText().toString()+"\n"+profile_type;
//            Toast.makeText(this,data,Toast.LENGTH_LONG).show();

            if(validate ()){
                data.setLogged_in_user(edtEmail.getText().toString());
                data.setUserType(profile_type);
                insertData(name, email, password, profile_type);
                //displaydata();
                if ("Jobseeker".equals(data.getUserType())) {
                    startActivity(new Intent(this, HomeActivity.class));
                } else if ("Employer".equals(data.getUserType())) {
                    startActivity(new Intent(this, EmployerHomeActivity.class));
                }

            }
            else {
                Toast.makeText(this,"Enter proper details.",Toast.LENGTH_LONG).show();
            }

        }
    }

    private void insertData(final String name, final String email, final String password, final String profile_type){
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "Sorry,this user exists", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> reg_entry = new HashMap<>();
                    reg_entry.put("Name", name);
                    reg_entry.put("Email", email);
                    reg_entry.put("Password", password);
                    reg_entry.put("Profile_type",profile_type);

                    //   String myId = ref.getId();
                    db.collection("users")
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

        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        if (name.isEmpty()) {
            edtName.setError("Enter Name");
            valid = false;
        } else {
            edtName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter a valid Email Address");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            edtPassword.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        if(!((rdoEmployer.isChecked ())||(rdoJobseeker.isChecked ()))){
            Toast.makeText(this, "Please Choose User Type.",
                    Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }


}
