package com.sjsingh101.resumebuddy.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sjsingh101.resumebuddy.Adapter.ChatAdapter;
import com.sjsingh101.resumebuddy.Class.ChatDataType;
import com.sjsingh101.resumebuddy.Class.ChatDataViewModel;
import com.sjsingh101.resumebuddy.R;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    // view model
    private ChatDataViewModel chatDataViewModel;

    // firebase
    private FirebaseAuth mAuth;

    // android layout
    private Toolbar mToolBar;
    private RecyclerView chatRecyclerView;
    private ChatAdapter mChatAdapter;
    private EditText sendMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // recycler view setup
        chatRecyclerView = findViewById(R.id.rv_chats);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        chatRecyclerView.setLayoutManager(linearLayoutManager);
        mChatAdapter = new ChatAdapter(getApplicationContext());
        chatRecyclerView.setAdapter(mChatAdapter);

        // view model setup
        chatDataViewModel = ViewModelProviders.of(this).get(ChatDataViewModel.class);
        chatDataViewModel.getChats().observe(this, new Observer<List<ChatDataType>>() {
            @Override
            public void onChanged(List<ChatDataType> chatData) {
                // update the recycler view
                mChatAdapter.setChatData(chatData);
            }
        });
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // toolbar setup
        mToolBar = findViewById(R.id.profile_activity_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(R.string.app_name);

        sendMessage=findViewById(R.id.send_message_text);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToMainActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.profile_activity_menu, menu);
        return true;
    }

    void sendToMainActivity() {
        Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int selectedItemId = item.getItemId();

        switch (selectedItemId) {
            case (R.id.profile_activity_toolbar_logOut):
                FirebaseAuth.getInstance().signOut();
                sendToMainActivity();
                break;

            case (R.id.profile_activity_toolbar_clearAll):
                chatDataViewModel.deleteAllChats();
                chatDataViewModel.insert(new ChatDataType("Bot","Hi there!"));
                break;
        }

        return true;
    }

    // sends the message to the bot
    public void sendMessage(View view) {
        String message=sendMessage.getText().toString();
        if (message.trim().isEmpty()) {
            Toast.makeText(this,"Please Enter Valid Answer",Toast.LENGTH_SHORT).show();
            return;
        }

        ChatDataType userMessage=new ChatDataType("You",message);
        chatDataViewModel.insert(userMessage);

        sendMessage.setText("");

        botReply(); // method for generating  further bot queries


    }

    private void botReply() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
            }
        }, 500000);

        ChatDataType botQuery=new ChatDataType("Bot","OK What's your name");
        chatDataViewModel.insert(botQuery);
    }
}
