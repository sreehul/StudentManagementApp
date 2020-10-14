package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Time_Activity extends AppCompatActivity {

    Button ttbtn, etbtn;
    String pathSDCard;
    ImageView bckImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_);
//        Online class Time table
        ttbtn = findViewById(R.id.ttbutton);
        ttbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyFiletoExternalStorage(R.raw.onclass, "onclass.xls");
                Uri data = FileProvider.getUriForFile(Time_Activity.this, "com.example.provider", new File(pathSDCard));
                Time_Activity.this.grantUriPermission(Time_Activity.this.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                final Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setDataAndType(data, "application/vnd.ms-excel")
                        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Time_Activity.this.startActivity(intent);
            }
        });

//        Exam Time table...
        etbtn = findViewById(R.id.etbutton);
        etbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Time_Activity.this, "Relax...No exams right now", Toast.LENGTH_LONG).show();
            }
        });
//        Back btnn...
        bckImg = findViewById(R.id.back_pressed);
        bckImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Time_Activity.this, BasicFeature.class));
            }
        });

    }

    private void copyFiletoExternalStorage(int resourceId, String resourceName) {

        pathSDCard = Environment.getExternalStorageDirectory() + "/Android/data/" + resourceName;
        try {
            InputStream in = getResources().openRawResource(resourceId);
            FileOutputStream out = new FileOutputStream(pathSDCard);

            byte[] buff = new byte[1024];
            int read = 0;

            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);

                }
            } finally {
                in.close();
                out.close();

            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}