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

import com.example.vidiic.appmusic.MainActivity;
import com.example.vidiic.appmusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;



public class LoginActivity extends AppCompatActivity {


    private static final String APP_ID = "";
    private Button btnLogin, btnLogOut;
    private EditText userText, passwordText;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        /*btnSignUp = findViewById(R.id.btnSignUp);*/
        userText = findViewById(R.id.userText);
        passwordText = findViewById(R.id.passwordText);

        //iniciamos la conexion con la app que controla los mensajes


        userText.setText("stucomtest@gmail.com");
        passwordText.setText("ssoo++");

        btnLogin.setOnClickListener(view -> registerUser());


    }

    private void logOut(){
        if (firebaseAuth.getCurrentUser() != null){
            firebaseAuth.signOut();
            Toast.makeText(LoginActivity.this, "Log Out", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(){

        final String email = userText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();

            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();

            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                //el usuario existe en la bbbd, correcto
                Toast.makeText(LoginActivity.this, "Success.", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                mainIntent.putExtra("email", email);
                startActivity(mainIntent);
            }else{
                //usuario no registrado, redirigir al signup
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

                Toast.makeText(LoginActivity.this, "User not registered. SignUp First", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
