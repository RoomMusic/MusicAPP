package com.example.vidiic.appmusic;

import android.Manifest;
import android.content.ContentResolver;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.vidiic.appmusic.classes.Song;
import com.example.vidiic.appmusic.songlist.AdapterSong;
import com.example.vidiic.appmusic.songlist.AsyncTaskListSong;
import com.example.vidiic.appmusic.songlist.AsyncTaskSong;
import com.example.vidiic.appmusic.utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements AsyncTaskSong.WeakReference{

    private int value = 0;
    private static final int MY_PERMISSION_REQUEST = 1;

    List<Song> songs;
    RecyclerView rcSongs;
    AdapterSong adapter;

    AsyncTaskSong asyncTaskSong = new AsyncTaskSong(this);
    //AsyncTaskListSong asyncTaskListSong = new AsyncTaskListSong((AsyncTaskListSong.WeakReference) this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Live Music");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper bottomNavigationViewHelper = new BottomNavigationViewHelper();
        bottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        bottomNavigationViewHelper.organizeBottomNavigationView(bottomNavigationView,MainActivity.this);


        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
        }else {

            Log.i("Main","Entramos1");
//            if (value == 0){
//                asyncTaskSong.execute();
//                value = 1;
//            }else {
//                asyncTaskListSong.execute();
//            }
                asyncTaskSong.execute();
        }
    }
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finished(List<Song> list) {
        Log.i("Main","EntramosFinsih");
        Log.d("Main", "finished: "+list.size());

        adapter = new AdapterSong(list,this);
        rcSongs = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager =
            new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcSongs.setLayoutManager(layoutManager);
        rcSongs.setItemAnimator(new DefaultItemAnimator());
        Log.i("Main","Adaptame ESTA");
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Main", "onClick: clicl");
            }
        });
        rcSongs.setAdapter(adapter);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSION_REQUEST:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"PermissionGranted",Toast.LENGTH_SHORT).show();
                        Log.i("Main","Entramos2");
                        asyncTaskSong.execute( );
                    }
                }else {
                    Toast.makeText(this,"PermissionDeneged",Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_icon:
                break;
            case R.id.user_icon:
                break;
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
