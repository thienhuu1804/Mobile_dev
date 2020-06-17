package com.example.cookingebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webView extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);

        //lấy dữ liệu từ Intent
        Intent intent = getIntent();
        String url = intent.getStringExtra("dataURL");

        //gọi Webview
        webView.getSettings().setJavaScriptEnabled(true); // bật javascript
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

}
