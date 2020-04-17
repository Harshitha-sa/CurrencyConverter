package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class homesceen extends AppCompatActivity {
    Button realcurrencybutton,cryptocurrencybutton,AssistantButton,NewsButton,LoanButton,ChatButton;

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
        AssistantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAssistantActivity();
            }
        });
        NewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoNewsActivity();
            }
        });
        LoanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoanAnalizerActivity();
            }
        });
        ChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddContactsActivity();
            }
        });
    }

    private void gotoAddContactsActivity() {
        Intent gotoaddcontactsscreen = new Intent(homesceen.this,Addcontacts.class);
        startActivity(gotoaddcontactsscreen);
    }

    private void gotoLoanAnalizerActivity() {
        Intent gotoLoanAnalizerActivity= new Intent(homesceen.this,UserLoanDetails.class);
        startActivity(gotoLoanAnalizerActivity);
    }

    private void gotoNewsActivity() {
        Intent gotonewsactivity= new Intent(homesceen.this,NewsMainPage.class);
        startActivity(gotonewsactivity);
    }

    private void gotoAssistantActivity() {
        Intent gotoAsiistantActivity = new Intent(homesceen.this,AssistantActivity.class);
        startActivity(gotoAsiistantActivity);
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
        AssistantButton=findViewById(R.id.assistantButton);
        NewsButton=findViewById(R.id.newsButton);
        LoanButton=findViewById(R.id.LoanButton);
        ChatButton=findViewById(R.id.chatbutton);
    }
}
