package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ActivityHome extends AppCompatActivity {
    EditText editText, rollEditText;
    FloatingActionButton fab;
    ImageView logo;
    int SPLASH_TIME = 1000;
    String UserName, UserRoll;
    public static String globalRoll;
    DatabaseReference reference;
    Helper userObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Requesting permissions...
        verifyStoragePermissions(ActivityHome.this);

        editText = findViewById(R.id.TextPersonName);
        rollEditText = findViewById(R.id.rollnumber);
        fab = findViewById(R.id.fab);
        logo = findViewById(R.id.logo);
        reference = FirebaseDatabase.getInstance().getReference("Users");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Send User name and roll to Database
                UserName = editText.getText().toString().toUpperCase();
                UserRoll = rollEditText.getText().toString();

                if (UserName.isEmpty() | UserRoll.isEmpty()) {
                    editText.setError("Fields cannot be empty");
                    rollEditText.setError("Fields cannot be empty");
                    Toast.makeText(ActivityHome.this, "Please go back and enter your name and rollno", Toast.LENGTH_SHORT).show();
                } else {
                    upload();
                }

                ScaleAnimation fade_in = new ScaleAnimation(1f, 2f, 1f, 2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                fade_in.setDuration(1500);
                fade_in.setFillAfter(false);
                logo.startAnimation(fade_in);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        //Do any action here. Now we are moving to next page
                        Intent myIntent = new Intent(ActivityHome.this, BasicFeature.class);
                        startActivity(myIntent);

                        Toast.makeText(ActivityHome.this, "Hey " + UserName, Toast.LENGTH_LONG).show();


                    }
                }, SPLASH_TIME);


            }
        });


    }

    private void upload() {

        globalRoll = UserRoll;
        userObj = new Helper(UserName, UserRoll);
        reference.child(UserRoll).setValue(userObj);
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}