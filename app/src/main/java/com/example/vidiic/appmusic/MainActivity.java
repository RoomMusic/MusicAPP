package com.example.vidiic.appmusic;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.vidiic.appmusic.classes.Song;
import com.example.vidiic.appmusic.classes.User;
import com.example.vidiic.appmusic.login.LoginActivity;
import com.example.vidiic.appmusic.adapters.AdapterSong;
import com.example.vidiic.appmusic.songlist.AsyncTaskSong;
import com.example.vidiic.appmusic.utils.BottomNavigationViewHelper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.chatcamp.sdk.ChatCamp;
import io.chatcamp.sdk.ChatCampException;

public class MainActivity extends AppCompatActivity
        implements AsyncTaskSong.WeakReference {

    private static final int MY_PERMISSION_REQUEST = 1;

    List<Song> songs;
    RecyclerView rcSongs;
    AdapterSong adapter;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String userEmail;
    private User u;
    private String userKey;
    private User userAux = new User();
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private String url = "";

    /* ArrayList<String> arrayList;
     ListView listView;
     ArrayAdapter<String> adapter;
     MediaMetadataRetriever mediaMetadataRetriever;
     byte[] art;*/


    AsyncTaskSong asyncTaskSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        //obtenemos el email que hemos pasado desde la actividad login
        userEmail = getIntent().getExtras().getString("email");

        //obtenemos la clave de firebase para guardar el usuario con el mismo id en la bbdd del chat api
        userKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("sergio", userEmail + " " + FirebaseAuth.getInstance().getCurrentUser().getUid());

        firebaseFirestore.collection("users").document(userKey).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){
                userAux = documentSnapshot.toObject(User.class);
                Log.d("sergio", userAux.getUsername());
            }else{
                Log.d("sergio", "no existe");
            }
        });


        checkUser(userKey);

        asyncTaskSong = new AsyncTaskSong(this, userEmail);

        BottomNavigationViewEx bottomNavigationView = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper();
        bottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        bottomNavigationViewHelper.organizeBottomNavigationView(bottomNavigationView, MainActivity.this);


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.action_folder:


                    Toast.makeText(getContext(), "Action folder", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_music:
                    //conectamos con el servicio pasandole el id y a la vez actualizamos el nombre de usuario que se mostrará en pantalla
                    ChatCamp.connect(userKey, (user, e) ->
                            ChatCamp.updateUserDisplayName(userAux.getUsername(), (user1, e1) ->
                                    Toast.makeText(getContext(), "usuario actualizado", Toast.LENGTH_SHORT).show()));


                    Toast.makeText(getContext(), "Action music", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_share:


                    Toast.makeText(getContext(), "Action share", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_user:


                    Toast.makeText(getContext(), "Action user", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_settings:


                    Toast.makeText(getContext(), "Action settings", Toast.LENGTH_SHORT).show();
                    break;

            }
            return true;
        });


        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
            //Log.i("Main", "entramos 0 ");
        } else {

            Log.i("Main", "Entramos1");
            asyncTaskSong.execute();
        }


    }


    private void checkUser(String userid) {
        //con esta sentencia obtenemos de la coleccion de usuario el documento con el email del usuario el cual contiene los datos de este
        firebaseFirestore.collection("users").document(userid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                //obtenemos los datos del usurio en un map
                Map<String, Object> map = task.getResult().getData();

                //Log.d("sergio", "" + task.getResult().getData().get("firstIn"));


                //comprobamos si el usuario ya ha entrado antes o no
                boolean check = (boolean) map.get("firstIn");

                //Log.d("sergio", map.get("email").toString());

                //comprobar si el usuario ya habia entrado
                //actualizamos el usuario
                if (!check) {
                    //si no ha entrado obtenemos las canciones de su movil y las guardamos en la bbdd
                    Log.d("sergio", "no ha entrado");

                    //actualizamos el campo firstIn a TRUE
                    firebaseFirestore.collection("users").document(map.get("userid").toString()).
                            update("firstIn", true).
                            addOnSuccessListener(aVoid -> Log.d("sergio", "Campo actualizado")).
                            addOnFailureListener(e -> Log.d("sergio", "Error al actualizar"));

                } else {
                    //si ya ha entrado, obtenemos las canciones de la base de datos
                    Log.d("sergio", "si ha entrado");


                }
            }
        });
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finished(List<Song> list) {

        Log.i("Main", "EntramosFinsih");
        Log.d("Main", "finished: " + list.toString());


        adapter = new AdapterSong(list, this);
        rcSongs = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcSongs.setLayoutManager(layoutManager);
        rcSongs.setItemAnimator(new DefaultItemAnimator());
        Log.i("Main", "Adaptame ESTA jajajja");
        adapter.setOnClickListener(v -> Log.d("Main", "onClick: clicl"));

        rcSongs.setAdapter(adapter);

        //Log.d("sergio", "emial: " + userAux.getEmail());

        Map<String, Object> songs = new HashMap<>();

        for (Song s : list) {
            songs.put("song name", s.getName());
            songs.put("song artist", s.getArtist());
            //songs.put("image", s.getImageSong());

            //guardamos las canciones del usuario en la base de datos, aqui estamos guardando solamente datos
            //firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("songs").add(songs);
        }


    }



    /*public void doStuff(){
        listView = (ListView) findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    public void getMusic(){
        ContentResolver contentResolver = getContentResolver();
        Uri songuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor =contentResolver.query(songuri,null,null,null,null);

        if (songCursor != null && songCursor.moveToFirst()){
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            //MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            //mediaMetadataRetriever.setDataSource(MediaStore.Audio.Media.DATA);
            // byte[] art = mediaMetadataRetriever.getEmbeddedPicture();
            //Bitmap songimage = BitmapFactory.decodeByteArray(art,0,art.length);

            int imagen = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentImae = songCursor.getString(imagen);
                mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(currentImae);
                //Bitmap songimage = BitmapFactory.decodeByteArray(art,0,art.length);
                String currentImaes;
                try{
                    art = mediaMetadataRetriever.getEmbeddedPicture();
                    Bitmap songimage = BitmapFactory.decodeByteArray(art,0,art.length);
                    currentImaes = songimage.toString();
                    Log.i("vidiiiiii",currentImae);

                }catch (Exception e){
                    currentImaes="a";
                }
                arrayList.add(currentTitle +"\n"+currentImaes);

            }while (songCursor.moveToNext());
        }
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "PermissionGranted", Toast.LENGTH_SHORT).show();
                        Log.i("Main", "Entramos2");
                        asyncTaskSong.execute();

                        //cuando se da permiso para que acceda a nuestro contenido comprobamos si ha entrado ya a la app o no
                        /*if(){

                        }*/

                    }
                } else {
                    Toast.makeText(this, "PermissionDeneged", Toast.LENGTH_SHORT).show();

                    finish();
                }
                return;
            }
        }
    }

//    private User getUserByEmail(String userId) {
//
//        firebaseFirestore = FirebaseFirestore.getInstance();
//
//        Log.d("sergio", "USER ID: " + userId);
//
//        firebaseFirestore.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
//            if (documentSnapshot.exists()) {
//                u = documentSnapshot.toObject(User.class);
//                Log.d("sergio", "USER email: " + u.getEmail());
//            }
//        });
//
//
//        return u;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                Intent help = new Intent(this, AboutActivity.class);
                startActivity(help);
                break;
            case R.id.settings:
                break;
            case R.id.update:
                break;
            case R.id.log_out:
                if (firebaseAuth.getCurrentUser() != null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    Toast.makeText(MainActivity.this, "Sesion cerrada.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
