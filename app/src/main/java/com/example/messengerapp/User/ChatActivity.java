package com.example.messengerapp.User;

import android.annotation.SuppressLint;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.messengerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ChatActivity extends AppCompatActivity {

    private RecyclerView mChat;
    private RecyclerView.Adapter mChatAdapter;
    private RecyclerView.LayoutManager mChatLayoutManager;

    ArrayList<MessageObject> messageList;

    ChatObject mChatObject;

    DatabaseReference mChatMessagesDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mChatObject = (ChatObject) getIntent().getSerializableExtra("chatObject");

        mChatMessagesDB = FirebaseDatabase.getInstance().getReference().child("chat").child(mChatObject.getChatID()).child("messages");

        Button mSend = findViewById(R.id.send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initializeMessage();
        getChatMessages();

    }//onCreate

    EditText mMessage;

    @SuppressLint("WrongConstant")
    private void initializeMessage() {
        messageList = new ArrayList<>();
        mChat = findViewById(R.id.messageList);
        mChat.setNestedScrollingEnabled(false);
        mChat.setHasFixedSize(false);
        mChatLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        mChat.setLayoutManager(mChatLayoutManager);
        mChatAdapter = new MessageAdapter(messageList);
        mChat.setAdapter(mChatAdapter);
    }

    private void getChatMessages() {
        mChatMessagesDB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    String text = "", creatorID = "";
                    ArrayList<String> mediaUrlList = new ArrayList<>();

                    if(snapshot.child("text").getValue() != null){
                        text = snapshot.child("text").getValue().toString();
                    }
                    if(snapshot.child("creator").getValue() != null){
                        creatorID = snapshot.child("creator").getValue().toString();
                    }

                    MessageObject mMessage = new MessageObject(snapshot.getKey(), creatorID, text);
                    messageList.add(mMessage);
                    mChatLayoutManager.scrollToPosition(messageList.size()-1);
                    mChatAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendMessage() {
        mMessage = findViewById(R.id.messageInput);

        String messageID = mChatMessagesDB.push().getKey();
        final DatabaseReference newMessageDB = mChatMessagesDB.child(messageID);

        final Map newMessageMap = new HashMap<>();

        newMessageMap.put("creator", FirebaseAuth.getInstance().getUid());

        if(!mMessage.getText().toString().isEmpty()){
            newMessageMap.put("text", mMessage.getText().toString());
            updateDatabaseWithNewMessage(newMessageDB, newMessageMap);
        }
    }

    private void updateDatabaseWithNewMessage(DatabaseReference newMessageDB, Map newMessageMap) {
        newMessageDB.updateChildren(newMessageMap);
        mMessage.setText(null);

        String message;

        if(newMessageMap.get("text") != null){
            message = newMessageMap.get("text").toString();
        }else{
            message = "Sent";
        }

        for(UserObject mUser : mChatObject.getUserObjectArrayList()){
            if(!mUser.getUid().equals(FirebaseAuth.getInstance().getUid())){

            }

        }
    }



















}//end class