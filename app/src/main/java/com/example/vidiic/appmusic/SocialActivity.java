package com.example.vidiic.appmusic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import com.example.vidiic.appmusic.adapters.UserChatAdapter;
import com.example.vidiic.appmusic.chat.ChatActivity;
import com.example.vidiic.appmusic.classes.User;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sendbird.android.SendBird;


import java.util.ArrayList;
import java.util.List;

public class SocialActivity extends AppCompatActivity implements UserChatAdapter.OnItemClickListener {

    private static final String APP_ID = "60DA930F-248F-479A-B406-028DEF5060D7";

    private RecyclerView rv;
    private UserChatAdapter userChatAdapter;
    private FirebaseFirestore firebaseFirestore;
    private List<User> userList;
    private User appUser;
    private static final String ACCESS_TOKEN = "866684193dd70a96b701dff98180201f064eca21";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);


        rv = findViewById(R.id.rvChatUser);
        userList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();


        SendBird.init(APP_ID, this);


        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(documentSnapshot ->
        {
            if (documentSnapshot.exists()) {

                appUser = documentSnapshot.toObject(User.class);
                Log.d("sergio", "usuario creado joder " + appUser.getUserid());


                //conectamos el usuario alservicio de senbiird
                SendBird.connect(appUser.getUserid(), "", (user, e) -> {
                    if (e != null) {

                        Toast.makeText(SocialActivity.this, "ERROR: " + e.getCode(), Toast.LENGTH_SHORT).show();
                        Log.d("sergio", "CODIGO ERROR: " + e.getStackTrace()[0]);
                        return;
                    }

                    Toast.makeText(SocialActivity.this, "USUARIO CONECTADO", Toast.LENGTH_SHORT).show();

                    SendBird.updateCurrentUserInfo(appUser.getNickName(), null, e1 ->
                            Toast.makeText(SocialActivity.this, "USUARIO ACTUALIZADO" + appUser.getNickName(), Toast.LENGTH_SHORT).show());
                });

            }
        });

        firebaseFirestore.collection("users").get().addOnCompleteListener(task ->
        {
            //si la consulta se realiza con exito
            if (task.isSuccessful()) {
                //recorremos todos los objetos obtenidos de la base de datos
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    //condicion para evitar que el usuario que esta usando la app le aparezca su usuario en las listas de amigos
                    if (!documentSnapshot.toObject(User.class).getUserid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        userList.add(documentSnapshot.toObject(User.class));
                        Log.d("sergio", "USUARIO AÑADIDO " + documentSnapshot.toObject(User.class).getNickName());
                    }

                }

                userChatAdapter = new UserChatAdapter(userList);
                userChatAdapter.setOnItemClickListener(this);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                        getApplicationContext()
                );

                rv.setLayoutManager(layoutManager);
                rv.setItemAnimator(new DefaultItemAnimator());

                rv.setAdapter(userChatAdapter);
                userChatAdapter.setOnItemClickListener(this);

                //Log.d("sergio", "LLEGO AHORA");

            }
        });


    }

    @Override
    public void itemClicked(View view, User user) {
        Toast.makeText(view.getContext(), "User ID" + user.getUserid(), Toast.LENGTH_SHORT).show();

        //Log.d("sergio", "CLICK USUARIO");

        Intent chatIntent = new Intent(this, ChatActivity.class);

        String[] userIds = {appUser.getUserid(), user.getUserid()};

        chatIntent.putExtra("userids", userIds);

        startActivity(chatIntent);
    }


    //aqui pasaremos los datos que nos interese para abrir la conversacion del usuario

}
