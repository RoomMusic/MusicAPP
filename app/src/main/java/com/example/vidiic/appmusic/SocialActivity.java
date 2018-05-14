package com.example.vidiic.appmusic;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.vidiic.appmusic.adapters.UserChatAdapter;
import com.example.vidiic.appmusic.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SocialActivity extends AppCompatActivity {

    private RecyclerView rv;
    private UserChatAdapter userChatAdapter;
    private FirebaseFirestore firebaseFirestore;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        rv = findViewById(R.id.rvChatUser);
        userList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users").get().addOnCompleteListener(task ->
        {
            //si la consulta se realiza con exito
            if (task.isSuccessful()) {
                //recorremos todos los objetos obtenidos de la base de datos
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    userList.add(new User((String) documentSnapshot.get("username")));
                    Log.d("sergio", "USUARIO AÑADIDO");
                }

                userChatAdapter = new UserChatAdapter(SocialActivity.this, userList);


                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                );

                rv.setLayoutManager(layoutManager);
                rv.setItemAnimator(new DefaultItemAnimator());

                rv.setAdapter(userChatAdapter);
                Log.d("sergio", "LLEGO AHORA");

            }
        });


    }
}
