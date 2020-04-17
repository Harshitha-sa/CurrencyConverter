package com.example.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Addcontacts extends AppCompatActivity {
    RecyclerView addContactRecyclerList;
   DatabaseReference rootref;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String currentUserId;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Contacts> options= new FirebaseRecyclerOptions.Builder<Contacts>().setQuery(rootref,Contacts.class).build();
        FirebaseRecyclerAdapter<Contacts,addContactView> adapter=new FirebaseRecyclerAdapter<Contacts, addContactView>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final addContactView holder, final int position, @NonNull Contacts model) {
                String uName,uPhoneNumber;


                uName=model.getName();
                uPhoneNumber=model.getPhoneNumber();

                holder.name.setText(model.getName());

                holder.phoneNumber.setText(model.getPhoneNumber());

                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String visitorUserId;
                        visitorUserId=getRef(position).getKey();
                           if(currentUserId.equals(visitorUserId)){
                            Toast.makeText(Addcontacts.this, "Can't text to yourself", Toast.LENGTH_SHORT).show();
                        }
                        else
                            gotochatsActivity(visitorUserId);

                    }
                });
  }
            @NonNull
            @Override
            public addContactView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display_layout,parent,false);
                addContactView viewHolder =new addContactView(view);
                return viewHolder;
            }
        };
        addContactRecyclerList.setAdapter(adapter);
        adapter.startListening();
    }

    private void gotochatsActivity(String visitorUserId) {
        Intent gotoChatsActivity= new Intent(Addcontacts.this,ChatActivity.class);
        gotoChatsActivity.putExtra("visitorUserId",visitorUserId);
        startActivity(gotoChatsActivity);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcontacts);
        initializeFields();
        addContactRecyclerList=findViewById(R.id.AddContactsRecyclerLayout);
        addContactRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        rootref= FirebaseDatabase.getInstance().getReference().child("users");
    }

    private void initializeFields() {
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        if(currentUser==null)
            gotohomeActivity();
        else
            currentUserId=currentUser.getUid();
    }
    public class addContactView extends RecyclerView.ViewHolder{
        public TextView name,phoneNumber;
        public addContactView(@NonNull View itemView){
            super(itemView);
            name=itemView.findViewById(R.id.userNameTextView);
            phoneNumber=itemView.findViewById(R.id.userPhoneNumberTextView);
        }
    }

    private void gotohomeActivity() {
        Intent gotohomeActivity= new Intent(Addcontacts.this,homesceen.class);
        startActivity(gotohomeActivity);
    }
}
