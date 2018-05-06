package com.example.vidiic.appmusic;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

public class AboutActivity extends AppCompatActivity {

    private static final String APP_ID = "EAF2EAA0-1F14-4264-AE27-A5ECF7A3C6A6";
    private FirebaseAuth firebaseAuth;
    private Button button;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        firebaseAuth = FirebaseAuth.getInstance();
        //button = findViewById(R.id.button2);

        //SendBird.init(APP_ID, this);


//        //connectar usuario si el id no existe se creara uno
//        SendBird.connect(firebaseAuth.getCurrentUser().getUid(), (user, e) -> {
//            if (e != null) {
//
//                return;
//            }
//        });


        //se desconecta el usuario de la sesion
        /*button.setOnClickListener(v ->
                SendBird.disconnect(() ->
                        Toast.makeText(this, "Desconectado", Toast.LENGTH_SHORT).show()));*/




    }
}
