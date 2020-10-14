package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class Attendance_activity extends AppCompatActivity {
    WebView webView;
    ProgressDialog progressDialog;
    String url;
    ImageView bckImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_activity);
        url = "https://www.icampuz.in/ngi/index.php";
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

//        Back bttn

        bckImg = findViewById(R.id.back_pressed);
        bckImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Attendance_activity.this, BasicFeature.class));
            }
        });

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        //Show loader on url load
        public void onLoadResource(WebView view, String url) {
            if (progressDialog == null) {
                // in standard case YourActivity.this
                progressDialog = new ProgressDialog(Attendance_activity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }
        }

        public void onPageFinished(WebView view, String url) {
            try {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}