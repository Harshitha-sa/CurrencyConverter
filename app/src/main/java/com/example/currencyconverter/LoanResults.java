package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoanResults extends AppCompatActivity {
    String age;
    String monthlyIncome;
    String workExperience;
    private FirebaseAuth mAuth;
    DatabaseReference incomesref;
    String currentUserId;
    ImageView sbi,hdfc,icici,axis,kotak;
    int a,m,w;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_results);
        initializefields();

    }

    private void calculatefunction() {
        Log.d("insidecalculate","a="+a+" w="+w+" m="+m);
            if(a>=21&&w>=1){
            if(a<=76&&m>=5000){
                Log.d("bank","sbi");
                sbi.setVisibility(View.VISIBLE);
            }
            if(a<=60&&w>2&&m>=15000){
                Log.d("bank","hdfc");
                hdfc.setVisibility(View.VISIBLE);
            }
            if((a>=23 && a<=65)&&(w>=2)&&(m>17500)){
                Log.d("bank","icici");
                icici.setVisibility(View.VISIBLE);
            }
            if((a>=21 && a<=58)&&m>2000){
                Log.d("bank","kotak mahindra");
                kotak.setVisibility(View.VISIBLE);
            }
            if((a<=60)&&m>15000){
                Log.d("bank","axis");
                axis.setVisibility(View.VISIBLE);
            }}else {
                Toast.makeText(LoanResults.this,"Coudnt find any bank",Toast.LENGTH_LONG).show();
                gotoHomeActivity();
            }

    }


    private void initializefields() {
        sbi=findViewById(R.id.sbitick);
        hdfc=findViewById(R.id.hdfctick);
        icici=findViewById(R.id.icicitick);
        kotak=findViewById(R.id.kotaktick);
        axis=findViewById(R.id.axistick);

    mAuth=FirebaseAuth.getInstance();
    currentUserId=mAuth.getInstance().getCurrentUser().getUid();
    incomesref= FirebaseDatabase.getInstance().getReference().child("incomes").child(currentUserId);

    incomesref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                age=dataSnapshot.child("age").getValue().toString();
                workExperience=dataSnapshot.child("workExperience").getValue().toString();
                monthlyIncome=dataSnapshot.child("monthlyIncome").getValue().toString();
                a=Integer.parseInt(age);
                w=Integer.parseInt(workExperience);
                m=Integer.parseInt(monthlyIncome);
                Log.d("values","a="+a+" w="+w+" m="+m);
                calculatefunction();
            }else {
                Toast.makeText(LoanResults.this, "Error : Check your internet" , Toast.LENGTH_SHORT).show();
                gotoHomeActivity();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }

    private void gotoHomeActivity() {
        Intent gohome = new Intent(LoanResults.this,homesceen.class);
        startActivity(gohome);
    }
}

