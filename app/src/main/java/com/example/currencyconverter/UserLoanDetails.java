package com.example.currencyconverter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class UserLoanDetails extends AppCompatActivity {
    EditText Age,WorkExperience,MonthlyIncome;
    Button SubmitButton;
    FirebaseAuth mAuth;
    DatabaseReference rootref;
    String userAge,userWorkExperience,userMonthlyIncome;
    ProgressDialog loadingBar;
    String currentuserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loan_details);
        initializefields();
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdetails();
            }
        });
    }

    private void getdetails() {
        userAge=Age.getText().toString();
        userMonthlyIncome=MonthlyIncome.getText().toString();
        userWorkExperience=WorkExperience.getText().toString();

        if(TextUtils.isEmpty(userAge)||TextUtils.isEmpty(userMonthlyIncome)||TextUtils.isEmpty(userWorkExperience)){
            Toast.makeText(this,"please fill all the details", LENGTH_SHORT).show();
        }else{
            currentuserId=mAuth.getCurrentUser().getUid();
        rootref.child("incomes").child(currentuserId).setValue("");
        HashMap<String,String> profileMap= new HashMap<>();
        profileMap.put("userId",currentuserId);
        profileMap.put("age",userAge);
        profileMap.put("workExperience",userWorkExperience);
        profileMap.put("monthlyIncome",userMonthlyIncome);
        rootref.child("incomes").child(currentuserId).setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if(task.isSuccessful()){
              gotoLoanresults();
           }else {
               String message = task.getException().toString();
               Toast.makeText(UserLoanDetails.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                gotoHomeScreen();
           }
            }
        });
        }
    }

    private void gotoHomeScreen() {
        Intent gotohomescreen= new Intent(UserLoanDetails.this,homesceen.class);
        startActivity(gotohomescreen);
    }

    private void gotoLoanresults() {
        Intent gotoloanresults=new Intent(UserLoanDetails.this,LoanResults.class);
        startActivity(gotoloanresults);
    }


    private void initializefields() {
        Age=findViewById(R.id.ageLimitTextView);
        WorkExperience=findViewById(R.id.workExperiencetextView);
        MonthlyIncome=findViewById(R.id.incomeTextView);
        SubmitButton=findViewById(R.id.submitButton);
        rootref= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);

    }
}

