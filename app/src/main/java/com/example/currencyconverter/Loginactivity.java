package com.example.currencyconverter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Loginactivity extends AppCompatActivity {
    EditText emailLoginedittext,passwordloginedittext;
    Button loginbutton;
    TextView newUsertextview;
    FirebaseAuth mAuth;
    ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        initializefields();
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();
            }
        });
        newUsertextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoregistrationpage();
            }
        });
    }

    private void userlogin() {
        String uEmail,uPassword;
        uEmail=emailLoginedittext.getText().toString();
        uPassword=passwordloginedittext.getText().toString();
        if(TextUtils.isEmpty(uEmail)||TextUtils.isEmpty(uPassword))
        {
            Toast.makeText(this,"Email-id or password field is empty",Toast.LENGTH_LONG).show();

        }
        else{
            loadingBar.setTitle("Logging in...");
            loadingBar.setMessage("Please wait... Logging In...");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(uEmail,uPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Loginactivity.this, "Log in Successful", Toast.LENGTH_SHORT).show();
                        gotohomescreen();
                        loadingBar.dismiss();
                    }
                    else{
                        String message=task.getException().toString();
                        Toast.makeText(Loginactivity.this,"Error is :"+ message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            }) ;


        }
    }

    private void gotoregistrationpage() {
        Intent gotoregistration = new Intent(Loginactivity.this,RegistrationActivity.class);
        startActivity(gotoregistration);
    }

    private void gotohomescreen() {
        Intent gotohomescreen = new Intent(Loginactivity.this,homesceen.class);
//        gotohomescreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(gotohomescreen);
//        finish();
    }

    private void initializefields() {
        mAuth=FirebaseAuth.getInstance();
        emailLoginedittext=findViewById(R.id.loginEmail);
        passwordloginedittext=findViewById(R.id.loginPassword);
        loginbutton=findViewById(R.id.loginButton);
        newUsertextview=findViewById(R.id.newusertextview);
        loadingBar=new ProgressDialog(this);
    }
}
