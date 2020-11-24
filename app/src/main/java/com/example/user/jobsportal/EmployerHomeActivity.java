package com.example.user.jobsportal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EmployerHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Log_Information data = new Log_Information();
    String user_email = data.getLogged_in_user();
    String user_type = data.getUserType();

    FirebaseFirestore db;

    //ListView lstJobList;
    TextView listJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_employer_home);

        //lstJobList = findViewById(R.id.lstJobList);
        //lstJobList.setAdapter(new ListJobsAdapter (getApplicationContext()));
        db = FirebaseFirestore.getInstance();
        listJobs = findViewById(R.id.alljobs);

        getJobs();

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                Snackbar.make (view, "Post Job", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ( );

                startActivity(new Intent(getApplicationContext(), PostJobActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener (toggle);
        toggle.syncState ( );

        NavigationView navigationView = (NavigationView) findViewById (R.id.nav_view);
        navigationView.setNavigationItemSelectedListener (this);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        getJobs();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        getJobs();
    }

    public void getJobs(){
            db.collection("jobs")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                String resultStr = "";
                                for(DocumentSnapshot document : task.getResult()){
                                    Jobs job = document.toObject(Jobs.class);
                                    resultStr += "Job Title: " + job.getJob_Title()+
                                            "\nJob Description: " + job.getJob_Description()+
                                            "\nJob Location: " + job.getAddress()+
                                            "\nJob Type: " + job.getType() +
                                            "\nPosted By: " + job.getPosted_by() + "\n\n";
                                }
                                listJobs.setText(resultStr);
                                //Toast.makeText(getApplicationContext(), resultStr, Toast.LENGTH_LONG).show();
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        if (drawer.isDrawerOpen (GravityCompat.START)) {
            drawer.closeDrawer (GravityCompat.START);
        } else {
            super.onBackPressed ( );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ( ).inflate (R.menu.employer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ( );

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected (item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId ( );

         if (id == R.id.nav_location) {
            String geoUri;

            geoUri = "http://maps.google.com/maps?q=loc:" +
                    13.7733 + "," + 70.3360 + " (" + "Job Fair on 12/12/2020" + ")";

            Intent locationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));

            startActivity(locationIntent);

        }else if (id == R.id.nav_user_manual) {
            startActivity(new Intent (this,UserManualActivity.class));

        } else if (id == R.id.nav_support) {
            startActivity(new Intent (this,SupportActivity.class));

        } else if (id == R.id.nav_logout) {
            data.setLogged_in_user("");
            data.setUserType ("");
            Intent loginIntent=new Intent(this,SignUpActivity.class);
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById (R.id.drawer_layout);
        drawer.closeDrawer (GravityCompat.START);
        return true;
    }
}
