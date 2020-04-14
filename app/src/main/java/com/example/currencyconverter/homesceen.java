package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class homesceen extends AppCompatActivity {
    Button realcurrencybutton,cryptocurrencybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homesceen);
        initializefields();
        realcurrencybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotorealcurrencypage();
            }
        });
        cryptocurrencybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotocryptocurrencypage();
            }
        });
    }

    private void gotorealcurrencypage() {
        Intent gotorealcurrency = new Intent(homesceen.this,MainActivity.class);
        startActivity(gotorealcurrency);
    }
    private void gotocryptocurrencypage(){
        Intent gotocryptocurrency = new Intent(homesceen.this,cryptocurrencypage.class);
        startActivity(gotocryptocurrency);
    }

    private void initializefields() {
        realcurrencybutton=findViewById(R.id.realcurrency);
        cryptocurrencybutton=findViewById(R.id.cryptocurrency);
    }
}
