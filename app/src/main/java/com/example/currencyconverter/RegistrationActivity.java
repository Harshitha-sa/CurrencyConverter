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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    String regUserEmail,regUserPassword,name,phoneNumber;
    EditText registerEmail,registerPhoneNumber,registerName,registerPassword;
    TextView haveanaccount;
    Button registerButton;
    FirebaseAuth mAuth;
    ProgressDialog loadingBar;
    DatabaseReference rootref;
    String currentuserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initializefields();
        haveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoLoginactivity();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        regUserEmail=registerEmail.getText().toString();
        name=registerName.getText().toString();
        phoneNumber=registerPhoneNumber.getText().toString();
        regUserPassword=registerPassword.getText().toString();
        if(TextUtils.isEmpty(regUserEmail)||TextUtils.isEmpty(regUserPassword)||TextUtils.isEmpty(name)||TextUtils.isEmpty(phoneNumber))
            Toast.makeText(this,"please fill all the details",Toast.LENGTH_SHORT).show();
        else {
            loadingBar.setTitle("Creating new Account");
            loadingBar.setMessage("please wait,while we are creating new account for you");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(regUserEmail,regUserPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){ Toast.makeText(RegistrationActivity.this, "Account Created Successfully", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    createProfile();
                    gotohomescreen();
                    }else{
                        String message = task.getException().toString();
                        Toast.makeText(RegistrationActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }



    private void createProfile() {
        currentuserId=mAuth.getCurrentUser().getUid();
        rootref.child("users").child(currentuserId).setValue("");
        HashMap<String,String> profileMap= new HashMap<>();
        profileMap.put("userId",currentuserId);
        profileMap.put("name",name);
        profileMap.put("phoneNumber",phoneNumber);
        profileMap.put("zPassword",regUserPassword);
        rootref.child("users").child(currentuserId).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Profile Created", Toast.LENGTH_SHORT).show();
                }else{
                    String Message=task.getException().toString();
                    Toast.makeText(RegistrationActivity.this, "Error "+Message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void gotohomescreen() {
        Intent gotohomescreen = new Intent(RegistrationActivity.this,homesceen.class);
        gotohomescreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(gotohomescreen);
        finish();
    }

    private void gotoLoginactivity() {
        Intent gotologinactivity = new Intent(RegistrationActivity.this,Loginactivity.class);
        startActivity(gotologinactivity);

    }

    private void initializefields() {
        registerEmail=findViewById(R.id.registerEmail);
        registerName=findViewById(R.id.registerName);
        registerPassword=findViewById(R.id.registerPassword);
        registerPhoneNumber=findViewById(R.id.registerPhoneNumber);
        registerButton=findViewById(R.id.registerButton);
        haveanaccount=findViewById(R.id.haveanccounttextview);
        loadingBar = new ProgressDialog(this);
        rootref= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
    }
}
