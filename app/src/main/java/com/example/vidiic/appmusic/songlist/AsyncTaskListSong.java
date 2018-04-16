package com.example.vidiic.appmusic.songlist;


import android.content.Context;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.preference.PreferenceManager;


import com.example.vidiic.appmusic.classes.Song;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vidiic on 17/03/2018.
 */

public class AsyncTaskListSong extends AsyncTask<String,Integer,List<Song>> {

    private static String data="";
    public List<Song> songs;

    public interface WeakReference{
        Context getContext();
        void finished(List<Song> list);
    }
    private WeakReference ref;

    public AsyncTaskListSong(WeakReference ref){
        super();
        this.ref=ref;
    }
    @Override
    protected List<Song> doInBackground(String... strings) {
        songs = new ArrayList<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ref.getContext());

        Gson gson = new Gson();

        String savedList = preferences.getString("MyListMusic","");

        List<Song> objects = gson.fromJson(savedList,List.class);

        return objects;
    }
    @Override
    public void onPostExecute(List<Song> result) {

        ref.finished(result);
    }
}
