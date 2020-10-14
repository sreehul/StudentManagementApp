package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Workshop_Activity extends AppCompatActivity {
    MaterialCardView cardone, cardtwo;
    ImageView bckImg;
    TextView userName, userRoll;
    Button androidbtn, javabtn, stbtn, showbtn;
    String details;
    DatabaseReference reference;
    public static String[] nameArr, rollArr, workshopArr, gradeArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_);
        userName = findViewById(R.id.username);
        userRoll = findViewById(R.id.rollnumber);
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//        Initialize global variables to use throughout the app
                nameArr = new String[(int) snapshot.getChildrenCount()];
                rollArr = new String[(int) snapshot.getChildrenCount()];
                workshopArr = new String[(int) snapshot.getChildrenCount()];
                gradeArr = new String[(int) snapshot.getChildrenCount()];
                //        Display users name and roll...
                userName.setText(snapshot.child(ActivityHome.globalRoll).child("userName").getValue().toString());
                userRoll.setText(snapshot.child(ActivityHome.globalRoll).child("userRoll").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        showbtn = findViewById(R.id.databasebtn);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    TODO Show details here...
                int i = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    nameArr[i] = ds.child("userName").getValue().toString();
                    rollArr[i] = ds.child("userRoll").getValue().toString();
                    try {
                        workshopArr[i] = ds.child("Workshop").getValue().toString();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Code to show details of those who have registered
                String dispDetails = "   Name - Workshop Registered \n\n";
                int serialNo;
                for (int i = 0; i < nameArr.length; i++) {
                    serialNo = i + 1;
                    dispDetails = dispDetails.concat(serialNo + ". " + nameArr[i] + " - " + workshopArr[i] + "\n");
                }
                new MaterialAlertDialogBuilder(Workshop_Activity.this)
                        .setTitle("Know the workshop your friend has registered to")
                        .setMessage(dispDetails)
                        .setPositiveButton("Ok", null)
                        .show();
            }
        });


//        Card action here....
        cardone = findViewById(R.id.materialCardView);
        cardtwo = findViewById(R.id.materialCardView2);
//Back bttn...
        bckImg = findViewById(R.id.back_pressed);
        bckImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Workshop_Activity.this, BasicFeature.class));
            }
        });

//Register Buttons
        androidbtn = findViewById(R.id.androidbtn);
        javabtn = findViewById(R.id.javabtn);
        stbtn = findViewById(R.id.testbtn);

        androidbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidbtn.setText("Registered");
                javabtn.setText("Register");
                stbtn.setText("Register");
                details = "Android";
                reference.child(ActivityHome.globalRoll).child("Workshop").setValue(details);
                Toast.makeText(Workshop_Activity.this, userRoll.getText().toString() + " registered to " + details, Toast.LENGTH_LONG).show();
            }
        });
        javabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                javabtn.setText("Registered");
                stbtn.setText("Register");
                androidbtn.setText("Register");
                details = "Java";
                reference.child(ActivityHome.globalRoll).child("Workshop").setValue(details);
                Toast.makeText(Workshop_Activity.this, userRoll.getText().toString() + " registered to " + details, Toast.LENGTH_LONG).show();
            }
        });
        stbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stbtn.setText("Registered");
                javabtn.setText("Register");
                androidbtn.setText("Register");
                details = "Testing";
                reference.child(ActivityHome.globalRoll).child("Workshop").setValue(details);
                Toast.makeText(Workshop_Activity.this, userRoll.getText().toString() + " registered to " + details, Toast.LENGTH_LONG).show();
            }
        });


    }


}
