package com.example.vidiic.appmusic.login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vidiic.appmusic.R;
import com.example.vidiic.appmusic.classes.User;
import com.example.vidiic.appmusic.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText userEmail, userPassword, userPassRepetition;
    private Button btnSignup;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPass);
        btnSignup = findViewById(R.id.btnSignUp);
        userPassRepetition = findViewById(R.id.userPassReptition);
        firebaseAuth = FirebaseAuth.getInstance();

        userEmail.setText("stucomtest@gmail.com");
        userPassword.setText("ssoo++");
        userPassRepetition.setText("ssoo++");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
    }



    private void registerUser() {

        final String userMail = userEmail.getText().toString();
        final String userPass = userPassword.getText().toString();
        String userPassRep = userPassRepetition.getText().toString();

        if (TextUtils.isEmpty(userMail) && TextUtils.isEmpty(userPass) && TextUtils.isEmpty(userPassRep)) {
            Toast.makeText(SignUpActivity.this, "There are empty fields", Toast.LENGTH_SHORT).show();
        } else if (!userPass.equals(userPassRep)) {
            Toast.makeText(SignUpActivity.this, "Password fields does not match.", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(userMail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    //los usuarios pueden registrarse con una contraseña
                    if (task.isSuccessful()) {
                        //usuario registrado completamente
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);

                        saveUser(userMail, userPass);

                        Toast.makeText(SignUpActivity.this, "User Registered Succesfully", Toast.LENGTH_SHORT).show();
                    } else {
                        //usuario ya registrado
                        Toast.makeText(SignUpActivity.this, "User is already registered.", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

    }

    private void saveUser(String email, String pass){
        /*Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("pass", pass);
        user.put("register date", new Date());
        user.put("has registered", false);*/

        User user = new User(email, pass, new Date(), false);

        firebaseFirestore.collection("users").document(user.getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(SignUpActivity.this, "Usuario registrado", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Fallo", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
