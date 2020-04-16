package com.example.currencyconverter;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AssistantActivity extends AppCompatActivity {

    EditText userInput;
    RecyclerView recyclerView;
    List<ResponceMessage> responseMessagesList;
    MessageAdaptor messageAdaptor;
    FirebaseAuth mAuth;
    DatabaseReference rootref,reference;
    String username;
    TextToSpeech t1;
    String Reply,currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);
        Initializefields();
        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEND){
                 ResponceMessage message= new ResponceMessage(userInput.getText().toString(),true);
                 responseMessagesList.add(message);
                 ResponceMessage message1=null;
                 try {
                     message1 = new ResponceMessage(botResponse(userInput.getText().toString()),false);

                 }catch (InterruptedException e){
                     e.printStackTrace();
                 }
                 responseMessagesList.add(message1);
                 userInput.setText("");
                 messageAdaptor.notifyDataSetChanged();
                }
                return true;
            }

            private String botResponse(String toString) throws InterruptedException{
                if(toString.equalsIgnoreCase("hi")||toString.equalsIgnoreCase("hello")||toString.equalsIgnoreCase("hey")){
                    t1.speak("Hello "+username+" , I am your Assistant how can i help?",TextToSpeech.QUEUE_FLUSH,null);
                    return "hello "+" ,this is medusa";
                }
                if(toString.toLowerCase().contains("intro")){
                    Reply="I am your assistant, i was built by Harshitha S A,Harshitha K,Keerthi K & Shreya M";
                    t1.speak(Reply,TextToSpeech.QUEUE_FLUSH,null);
                    return Reply;
                }
                if(toString.toLowerCase().contains("how are you")||toString.toLowerCase().contains("how r u")){
                    Reply="I am doing good!,how would i help you?";
                    t1.speak(Reply,TextToSpeech.QUEUE_FLUSH,null);
                    return Reply;
                }
                if(toString.toLowerCase().contains("help")){
                    Reply= "Our app provides an option to convert two types of currencies: real currency and crypto currency," +
                            "you can read currency related news on our app,   "+"Or you could chat with peer app users to some insights from a real person,    "+"Or " +
                            "you can get some loan related insights by filling up the appropriate details from Loan help option     "+"Or you could chat with me!";
                    t1.speak(Reply,TextToSpeech.QUEUE_FLUSH,null);
                    return Reply;
                }
                Reply= "sorry i didn't get that";
                t1.speak(Reply,TextToSpeech.QUEUE_FLUSH,null);
                return Reply;
            }
        });

    }

    private void Initializefields() {
        userInput=findViewById(R.id.userInput);
        recyclerView=findViewById(R.id.recyclerview);
        responseMessagesList= new ArrayList<>();
        messageAdaptor=new MessageAdaptor(responseMessagesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
           if(status!=TextToSpeech.ERROR){
               t1.setLanguage(Locale.US);
           }
            }
        });
        mAuth=FirebaseAuth.getInstance();
        rootref= FirebaseDatabase.getInstance().getReference();
        currentUserId=mAuth.getInstance().getCurrentUser().getUid();
        reference=FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username=dataSnapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
