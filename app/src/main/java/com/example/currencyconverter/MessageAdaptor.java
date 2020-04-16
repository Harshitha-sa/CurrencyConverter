package com.example.currencyconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdaptor extends RecyclerView.Adapter<MessageAdaptor.CustomViewHolder> {
    class CustomViewHolder extends  RecyclerView.ViewHolder{
        TextView textView;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textMessage);
        }
    }

    List<ResponceMessage> responseMessageList;
    public MessageAdaptor(List<ResponceMessage> responseMessageList) {
        this.responseMessageList=responseMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        if(responseMessageList.get(position).isMe()){
            return R.layout.user_bubble;
        }
        return R.layout.assistant_bubble;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdaptor.CustomViewHolder holder, int position) {
        holder.textView.setText(responseMessageList.get(position).getTextMessage());
    }

    @Override
    public int getItemCount() {
        return responseMessageList.size();
    }
}
