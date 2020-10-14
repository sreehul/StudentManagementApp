package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MoreActivity extends AppCompatActivity {
    ImageView bckImg;
    Button notification, about, sourcecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        bckImg = findViewById(R.id.moreBack);
        bckImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MoreActivity.this, BasicFeature.class));
            }
        });

//        Actions for buttons
        notification = findViewById(R.id.notification);
        about = findViewById(R.id.about);
        sourcecode = findViewById(R.id.sourcecode);


        //For Notification button
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.this);
                builder.setTitle("Notifications control");
// add a checkbox list
                String[] animals = {"Exam Notifications", "Event Notification", "Circulars"};
                final boolean[] checkedItems = {false, false, false};
                builder.setMultiChoiceItems(animals, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // user checked or unchecked a box
                        if (checkedItems[which]) {
                            checkedItems[which] = isChecked;
                        } else {
                            checkedItems[which] = isChecked;
                        }
                    }
                });
// add OK and Cancel buttons
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked DONE
                        Toast.makeText(MoreActivity.this, "Notifications updated", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", null);
// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        //For About Button
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(MoreActivity.this)
                        .setTitle("About")
                        .setMessage(
                                "This app is developed for/as Mini Project, by MCA students of NIITM" +
                                        " but doesn't belongs to Nehru group of institutions. \n" +
                                        "And this is an open source software, so feel free to checkout the source code.")
                        .setPositiveButton("Understood", null)
                        .show();
            }
        });

        //For SouceCode Button
        sourcecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(MoreActivity.this)
                        .setTitle("Sharing is caring")
                        .setMessage("We <3 those who code.\nSo that we are sharing our source code with you.\n" +
                                "You can either view or download the code, and customize the app the way you want.\n" +
                                "Happy coding :) ")
                        .setPositiveButton("view code", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent viewIntent =
                                        new Intent("android.intent.action.VIEW",
                                                Uri.parse("http://www.stackoverflow.com/"));
                                startActivity(viewIntent);
                            }
                        })
                        .setNegativeButton("Maybe later", null)
                        .show();
            }
        });

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}