package com.example.currencyconverter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


import java.util.List;

public class chatAdaptor extends RecyclerView.Adapter<chatAdaptor.chatViewHolder> {
    DatabaseReference userRef;
    FirebaseAuth mAuth;
    FirebaseUser currentuser;
    private List<messages> userMessageList;
    public chatAdaptor (List<messages> userMessageList){
        this.userMessageList=userMessageList;
    }
    public class chatViewHolder extends RecyclerView.ViewHolder{
        public TextView senderMsgTxt,recievermsgTxt;

        public  chatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsgTxt=itemView.findViewById(R.id.senderMessageTextView);
            recievermsgTxt=itemView.findViewById(R.id.recieverMessageTextView);
        }
    }
    @NonNull
    @Override
    public chatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custommessagetextview,parent,false);
        mAuth=FirebaseAuth.getInstance();

        return new chatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chatViewHolder holder, int position) {
        String messageSenderid=mAuth.getCurrentUser().getUid();
        messages varMessage=userMessageList.get(position);
        String fromUserID=varMessage.getFrom();
        if(fromUserID.equals(messageSenderid)){
            holder.senderMsgTxt.setVisibility(View.VISIBLE);
            holder.recievermsgTxt.setVisibility(View.INVISIBLE);
            holder.senderMsgTxt.setBackgroundResource(R.drawable.sendermessagelayout);
            holder.senderMsgTxt.setTextColor(Color.BLACK);
            holder.senderMsgTxt.setText(varMessage.getMessage());
        }
        else{
            holder.senderMsgTxt.setVisibility(View.INVISIBLE);
            holder.recievermsgTxt.setVisibility(View.VISIBLE);
            holder.recievermsgTxt.setBackgroundResource(R.drawable.recievermessagelayout);
            holder.recievermsgTxt.setTextColor(Color.BLACK);
            holder.recievermsgTxt.setText(varMessage.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }


}
