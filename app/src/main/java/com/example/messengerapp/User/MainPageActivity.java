package com.example.messengerapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.messengerapp.Chat.ChatListAdapter;
import com.example.messengerapp.Chat.ChatObject;
import com.example.messengerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView mChatList;
    private RecyclerView.Adapter mChatListAdapter;
    private RecyclerView.LayoutManager mChatListLayoutManager;

    ArrayList<ChatObject> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Button mLogout = findViewById(R.id.logout);
        Button mFindUser = findViewById(R.id.findUser);

        mFindUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FindUserActivity.class));
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() { //firebase functionality
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //page only for logged in user
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
            }
        });

        getPermissions();
        initializeRecyclerView();
        getUserChatList();

    }//end create

    private void getUserChatList(){
        DatabaseReference mUserChatDB = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getUid()).child("chat");

        mUserChatDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //adding unique chat to DB
                if(snapshot.exists()){
                    for(DataSnapshot childSnapshot : snapshot.getChildren()){
                        ChatObject mChat = new ChatObject(childSnapshot.getKey());
                        chatList.add(mChat);
                        mChatListAdapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS}, 1);
        }
    }//end Permissions

    @SuppressLint("WrongConstant")
    private void initializeRecyclerView() {
        mChatList = findViewById(R.id.chatList);
        mChatList.setNestedScrollingEnabled(false);
        mChatList.setHasFixedSize(false);
        mChatListLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mChatList.setLayoutManager(mChatListLayoutManager);
        mChatListAdapter = new ChatListAdapter(chatList);
        mChatList.setAdapter(mChatListAdapter);
    }

}//end class
