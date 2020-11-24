package com.example.user.jobsportal;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnLogin;
    Button btnRegister;
    EditText edtEmail;
    EditText edtPassword;
    
    Log_Information data = new Log_Information();

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        btnRegister=findViewById(R.id.btnSignUp);
        btnRegister.setOnClickListener(this);

        edtEmail= findViewById(R.id.edtEmail);
        edtPassword= findViewById(R.id.edtPassword);

        db = FirebaseFirestore.getInstance();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btnLogin.getId()) {
            String username = edtEmail.getText().toString();
            String password = edtPassword.getText().toString();

            if (validate()) {
                getUser();
                Toast.makeText(this, "login Successful", Toast.LENGTH_LONG).show();
                data.setLogged_in_user(edtEmail.getText().toString());
                finish();
                if ("Jobseeker".equals(data.getUserType())) {
                    startActivity(new Intent(this, HomeActivity.class));
                } else if ("Employer".equals(data.getUserType())) {
                    startActivity(new Intent(this, EmployerHomeActivity.class));
                }
            } else {
                Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_LONG).show();
            }
            //Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
            }
                else if (view.getId() == btnRegister.getId()) {
                Toast.makeText(this, "Creating an account", Toast.LENGTH_LONG).show();

                finish();
                Intent signUpIntent=new Intent(this,SignUpActivity.class);
                startActivity(signUpIntent);
            }
    }

    public void getUser(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            /*String resultStr = "";
                            for(DocumentSnapshot document : task.getResult()){
                                Users user = document.toObject(Users.class);
                                resultStr += "Name " + user.getName()+
                                                "\nEmail " + user.getEmail()+
                                                "\nPassword " + user.getPassword()+
                                                "\nProfile-Type " + user.getProfile_type() + "\n\n";
                            }
                            //edtEmail.setText(resultStr);
                            Toast.makeText(getApplicationContext(), resultStr, Toast.LENGTH_LONG).show();*/
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                String a1=edtEmail.getText().toString().trim();
                                String b1=edtPassword.getText().toString().trim();
                                if(edtEmail.getText().toString().equalsIgnoreCase(a1) & edtPassword.getText().toString().equalsIgnoreCase(b1)) {
                                    Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();

                                }else
                                    Toast.makeText(getApplicationContext(), "Cannot login,incorrect Email and Password", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "An error occured!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public boolean validate() {
        boolean valid = true;

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Enter valid Email Address");
            valid = false;
        } else {
            edtEmail.setError(null);
        }

        if (password.isEmpty()) {
            edtPassword.setError("Enter Password");
            valid = false;
        } else {
            edtPassword.setError(null);
        }

        return valid;
    }
}
