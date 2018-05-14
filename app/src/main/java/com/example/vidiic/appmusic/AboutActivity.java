package com.example.vidiic.appmusic;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;


public class AboutActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button button;
    private String url;
    private RecyclerView mMessageRecycler;
    private String CHANNEL_URL;
    private EditText messageBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        firebaseAuth = FirebaseAuth.getInstance();
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        messageBox = findViewById(R.id.edittext_chatbox);

        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));

        button = findViewById(R.id.button_chatbox_send);



        button.setOnClickListener(v -> {

        });

        //button = findViewById(R.id.button2);




    }
}
