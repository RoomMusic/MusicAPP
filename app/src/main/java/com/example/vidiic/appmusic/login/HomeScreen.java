package com.example.vidiic.appmusic.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.vidiic.appmusic.R;

/**
 * Created by alu2015018 on 19/03/2018.
 */

public class HomeScreen extends AppCompatActivity {

    private Button btnLogin, btnRegistro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        btnLogin = findViewById(R.id.btnInitSession);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnLogin.setOnClickListener(view -> startActivity(new Intent(HomeScreen.this, LoginActivity.class)));

        btnRegistro.setOnClickListener(view -> startActivity(new Intent(HomeScreen.this, SignUpActivity.class)));

    }
}
