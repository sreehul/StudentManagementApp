package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BasicFeature extends AppCompatActivity {
    ImageView backimg;
    Button morebtn;
    CardView attendance, grade, timetbl, workshop, report;
    String Sname = "", Sroll = "", Scgpa = "", Sworkshop = "";
    String[] Ssem = new String[]{"", "", "", ""};
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_feature);
        reference = FirebaseDatabase.getInstance().getReference("Users");
//        BACK IMAGE
        backimg = findViewById(R.id.back_pressed);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BasicFeature.this, ActivityHome.class));
            }
        });
        morebtn = findViewById(R.id.morebtn);
        morebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BasicFeature.this, MoreActivity.class));
            }
        });

//        OnClick for card views....
        attendance = findViewById(R.id.attendance);
        grade = findViewById(R.id.grade);
        timetbl = findViewById(R.id.timetable);
        workshop = findViewById(R.id.workshop);
        report = findViewById(R.id.report);
 //        Getting data from Database for report generation...
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    Sname = "Name                : " + snapshot.child(ActivityHome.globalRoll).child("userName").getValue().toString() + "\n";
                    Sroll = "Roll No             : " + snapshot.child(ActivityHome.globalRoll).child("userRoll").getValue().toString() + "\n";
                    Ssem[0] = "1st semester        : " + snapshot.child(ActivityHome.globalRoll).child("1st semester").getValue().toString() + "\n";
                    Ssem[1] = "2nd semester        : " + snapshot.child(ActivityHome.globalRoll).child("2nd semester").getValue().toString() + "\n";
                    Ssem[2] = "3rd semester        : " + snapshot.child(ActivityHome.globalRoll).child("3rd semester").getValue().toString() + "\n";
                    Ssem[3] = "4th semester        : " + snapshot.child(ActivityHome.globalRoll).child("4th semester").getValue().toString() + "\n";
                    Scgpa = "CGPA                : " + snapshot.child(ActivityHome.globalRoll).child("CGPA").getValue().toString() + "\n";
                    Sworkshop = "Workshop Registered : " + snapshot.child(ActivityHome.globalRoll).child("Workshop").getValue().toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BasicFeature.this, Attendance_activity.class));
            }
        });
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(BasicFeature.this, Grade_Activity.class));
            }
        });
        timetbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(BasicFeature.this, Time_Activity.class));
            }
        });
        workshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(BasicFeature.this, Workshop_Activity.class));
            }
        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display the Report
                String message = "";
                message = message.concat(Sname + Sroll + Ssem[0] + Ssem[1] + Ssem[2] + Ssem[3] + Scgpa + Sworkshop);
                new AlertDialog.Builder(BasicFeature.this)
                        .setTitle("Your report")
                        .setMessage(message)
                        .setPositiveButton("Done", null)
                        .show();
            }
        });


    }
}