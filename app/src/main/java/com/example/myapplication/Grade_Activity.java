package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Grade_Activity extends AppCompatActivity {
    Button gradeother, calculate, cgpaBtn;
    String str;
    ImageView bckImg;
    Spinner spinner, firstspin, seconfspin, thirdspin, fourthdpin, fifthspin, sixthspin, seventhspin, eighthspin;
    TextView firstsub, secondsub, thirdsub, fourthsub, fifthsub, sixthsub, seventhsub, eightsub;
    int semester, n, currentSemester;
    float GPA, temp;
    int[] Ci, GPi;
    float[] sem = new float[]{0, 0, 0, 0};
    DatabaseReference reference;
    String strCGPA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_);
        reference = FirebaseDatabase.getInstance().getReference("Users");
//        Bck btn
        bckImg = findViewById(R.id.back_pressed);
        bckImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Grade_Activity.this, BasicFeature.class));
            }
        });
//        Grade for others
        gradeother = findViewById(R.id.gradeothers);
        gradeother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://techietet.com/gpa/reg2017.php"));
                startActivity(viewIntent);
            }
        });


//        Spinner for grade
        List grade = new ArrayList();
        grade.add("O");
        grade.add("A+");
        grade.add("A");
        grade.add("B+");
        grade.add("B");
        grade.add("RA");

        firstspin = findViewById(R.id.firstspin);
        seconfspin = findViewById(R.id.secondspin);
        thirdspin = findViewById(R.id.thirdspin);
        fourthdpin = findViewById(R.id.fourthspin);
        fifthspin = findViewById(R.id.fifthspin);
        sixthspin = findViewById(R.id.sixthspin);
        seventhspin = findViewById(R.id.seventhspin);
        eighthspin = findViewById(R.id.eighthspin);


        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, grade);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        firstspin.setAdapter(arrayAdapter2);
        seconfspin.setAdapter(arrayAdapter2);
        thirdspin.setAdapter(arrayAdapter2);
        fourthdpin.setAdapter(arrayAdapter2);
        fifthspin.setAdapter(arrayAdapter2);
        sixthspin.setAdapter(arrayAdapter2);
        seventhspin.setAdapter(arrayAdapter2);
        eighthspin.setAdapter(arrayAdapter2);


        //        Spinner for semester
        final List sem = new ArrayList();
        sem.add(1);
        sem.add(2);
        sem.add(3);
        sem.add(4);

        spinner = findViewById(R.id.semspinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sem);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_SHORT).show();
                semester = (int) parent.getItemAtPosition(position);
                display(semester);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        Calculate button
        calculate = findViewById(R.id.calculate);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSemester = (int) spinner.getSelectedItem();
                calculateCredit(currentSemester);
                GPi = new int[]{
                        calculateGrade(firstspin), calculateGrade(seconfspin), calculateGrade(thirdspin)
                        , calculateGrade(fourthdpin), calculateGrade(fifthspin), calculateGrade(sixthspin)
                        , calculateGrade(seventhspin), calculateGrade(eighthspin)
                };

//                Calculations goes here....

                calculateN(currentSemester);
                GPA = calculateMethod(n, Ci, GPi);
                str = String.format("%.02f", GPA);
//                Showing result...
                new MaterialAlertDialogBuilder(Grade_Activity.this)
                        .setTitle("Your result")
                        .setMessage("Your GPA is " + str)
                        .setPositiveButton("Thanks", null)
                        .show();
                //Show CGPA
                cgpaBtn = findViewById(R.id.cgpa);
                strCGPA = String.format("%.02f", calculateCGPA(GPA));
                cgpaBtn.setText("CGPA = " + strCGPA);

                reference.child(ActivityHome.globalRoll).child("CGPA").setValue(strCGPA);

            }
        });


    }

    private float calculateCGPA(float GPA) {
        temp = 0;
        String SGPA = String.format("%.02f", GPA);
        switch (currentSemester) {
            case 1:
                sem[0] = GPA;
                reference.child(ActivityHome.globalRoll).child("1st semester").setValue(SGPA);
                break;
            case 2:
                sem[1] = GPA;
                reference.child(ActivityHome.globalRoll).child("2nd semester").setValue(SGPA);
                break;
            case 3:
                sem[2] = GPA;
                reference.child(ActivityHome.globalRoll).child("3rd semester").setValue(SGPA);
                break;
            case 4:
                sem[3] = GPA;
                reference.child(ActivityHome.globalRoll).child("4th semester").setValue(SGPA);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentSemester);
        }
        for (int i = 0; i < sem.length; i++) {
            temp += sem[i];
        }
        temp = temp / currentSemester;

        return temp;
    }

    private float calculateMethod(int n, int[] ci, int[] gPi) {
        float answer = 0;
        if (n == 1) {
            answer = (Ci[0] * GPi[0]) / Ci[0];


        } else {
            int divisor = 0, divider = 0;
            for (int i = 0; i < Ci.length; i++) {
                divisor += Ci[i] * GPi[i];
                divider += Ci[i];
            }
            answer = (float) divisor / divider;

        }
        return answer;
    }


    private void calculateN(int currentSemester) {
        switch (currentSemester) {
            case 1:
            case 2:
            case 3:
                n = 8;
                break;
            case 4:
                n = 1;
                break;

        }

    }


    private int calculateGrade(Spinner spin) {
//        TODO calculate and set the value to the variable GPi
        int answer = 0;
        String temp;
        temp = spin.getSelectedItem().toString();
        if (temp.equals("O")) {
            answer += 10;
        } else if (temp.equals("A+")) {
            answer += 9;
        } else if (temp.equals("A")) {
            answer += 8;
        } else if (temp.equals("B+")) {
            answer += 7;
        } else if (temp.equals("B")) {
            answer += 6;
        } else if (temp.equals("RA")) {
            answer += 0;
        }

        return answer;

    }

    private void calculateCredit(int currentSemester) {
        //        TODO calculate and set the value to the variable Ci
        switch (currentSemester) {
            case 1:
                Ci = new int[]{3, 3, 3, 3, 4, 2, 2, 2};
                break;
            case 2:
                Ci = new int[]{3, 3, 3, 4, 3, 2, 2, 1};
                break;
            case 3:
                Ci = new int[]{3, 3, 3, 3, 3, 2, 2, 2};
                break;
            case 4:
                Ci = new int[]{12};
                break;
        }
    }


    public void display(int semester) {
//        Initializing TextViews...

        firstsub = findViewById(R.id.firstsub);
        secondsub = findViewById(R.id.secondsub);
        thirdsub = findViewById(R.id.thirdsub);
        fourthsub = findViewById(R.id.fourthsub);
        fifthsub = findViewById(R.id.fifthsub);
        sixthsub = findViewById(R.id.sixthsub);
        seventhsub = findViewById(R.id.seventhsub);
        eightsub = findViewById(R.id.eighthsub);

//      Making spinners visible
        seconfspin.setVisibility(View.VISIBLE);
        thirdspin.setVisibility(View.VISIBLE);
        fourthdpin.setVisibility(View.VISIBLE);
        fifthspin.setVisibility(View.VISIBLE);
        sixthspin.setVisibility(View.VISIBLE);
        seventhspin.setVisibility(View.VISIBLE);
        eighthspin.setVisibility(View.VISIBLE);

        switch (semester) {
            case 1:
                firstsub.setText("1 Advanced Data Structures & Algorithms - MC5301");
                secondsub.setText("2 Computer Networks - MC5302");
                thirdsub.setText("3 Web Programming - MC5303");
                fourthsub.setText("4 Programming with Java - MC5304");
                fifthsub.setText("5 Object Oriented Analysis and Design - MC5305");
                sixthsub.setText("6 Data Structures and Algorithms Lab - MC5311");
                seventhsub.setText("7 Web Programming Lab - MC5312");
                eightsub.setText("8 Programming with Java Lab - MC5313");
                break;
            case 2:
                firstsub.setText("1 Resource Management Techniques - MC5401");
                secondsub.setText("2 Mobile Computing - MC5402");
                thirdsub.setText("3 Advanced Databases & Data Mining - MC5403");
                fourthsub.setText("4 Web Application Development - MC5404");
                fifthsub.setText("5 Professional Elective I");
                sixthsub.setText("6 Mobile Application Development Lab - MP5411");
                seventhsub.setText("7 Web Application Development Lab - MC5412");
                eightsub.setText("8 Technical Seminar & Report Writing - MC5413");
                break;
            case 3:
                firstsub.setText("1 Cloud Computing - MC5501");
                secondsub.setText("2 Big Data Analytics - MC5502");
                thirdsub.setText("3 Software Testing & Quality Assurance - MC5503");
                fourthsub.setText("4 Professional Elective II");
                fifthsub.setText("5 Professional Elective III");
                sixthsub.setText("6 Cloud and Big Data Lab - MC5511");
                seventhsub.setText("7 Software Testing Lab - MC5512");
                eightsub.setText("8 Mini Project - MC5513");
                break;
            case 4:
                firstsub.setText("1 Project Work - MC5611");
                secondsub.setText("");
                thirdsub.setText("");
                fourthsub.setText("");
                fifthsub.setText("");
                sixthsub.setText("");
                seventhsub.setText("");
                eightsub.setText("");

//                Hiding all other spinners
                seconfspin.setVisibility(View.GONE);
                thirdspin.setVisibility(View.GONE);
                fourthdpin.setVisibility(View.GONE);
                fifthspin.setVisibility(View.GONE);
                sixthspin.setVisibility(View.GONE);
                seventhspin.setVisibility(View.GONE);
                eighthspin.setVisibility(View.GONE);
                break;
        }

    }


}
