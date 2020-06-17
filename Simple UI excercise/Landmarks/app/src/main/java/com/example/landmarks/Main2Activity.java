package com.example.landmarks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    private Button buttonMapIt;
    private Button buttonMoreInfo;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();

        final String dataMapIt = intent.getStringExtra("dataMap");
        final String dataMoreInfo = intent.getStringExtra("dataMoreInfo");

        buttonMapIt= findViewById(R.id.buttonMapIt);
        buttonMoreInfo = findViewById(R.id.buttonMoreInfo);

        buttonMapIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), dataMapIt, Toast.LENGTH_SHORT).show();
                String address = dataMapIt.replace(' ', '+');
                clickButtonMap(address);
            }
        });

        buttonMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://" + dataMoreInfo;
                Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                clickButtonBrowser(url);
            }
        });

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });
    }

    public void clickButtonBrowser(String url){
        //tạo intent cho web browser
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void clickButtonMap(String address){
        //tạo Intent cho Google Maps
        try{
            Intent geoIntent = new Intent(
                    android.content.Intent.ACTION_VIEW, Uri
                    .parse("geo:0,0?q=" + address));

            startActivity(geoIntent);
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"Google Map not found!", Toast.LENGTH_SHORT).show();
        }

    }
}
