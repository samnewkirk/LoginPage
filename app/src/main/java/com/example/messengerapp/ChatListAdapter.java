package com.example.messengerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder> {

    ArrayList<UserObject> ChatList;

    public ChatListAdapter(ArrayList<UserObject> ChatList){
        this.ChatList = ChatList;
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        ChatListViewHolder rcv = new ChatListViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListViewHolder holder, int position) {
        holder.mName.setText(ChatList.get(position).getName());
        holder.mPhone.setText(ChatList.get(position).getPhone());

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = FirebaseDatabase.getInstance().getReference().child("chat").push().getKey();

                FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat").child(key).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("user").child(ChatList.get(position).getUid()).child("chat").child(key).setValue(true);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ChatList.size();
    }

    public class ChatListViewHolder extends RecyclerView.ViewHolder{
        public TextView mName, mPhone;
        public LinearLayout mLayout;
        public ChatListViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.name);
            mPhone = view.findViewById(R.id.phone);
            mLayout = view.findViewById(R.id.layout);
        }
    }

}//end class
