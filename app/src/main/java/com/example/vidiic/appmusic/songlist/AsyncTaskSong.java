package com.example.vidiic.appmusic.songlist;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.vidiic.appmusic.MainActivity;
import com.example.vidiic.appmusic.classes.Song;
import com.example.vidiic.appmusic.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vidiic on 17/03/2018.
 */

public class AsyncTaskSong extends AsyncTask<String, Integer, List<Song>> {

    private static String data = "";
    public List<Song> songs;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private byte[] art;
    private Bitmap songimage;
    private static final int MY_PERMISSION_REQUEST = 1;
    private StorageReference storageReference, songReference;
    private String email;
    private FirebaseStorage firebaseStorage;
    private StorageReference songFileReference;


    public interface WeakReference {
        Context getContext();

        void finished(List<Song> list);
    }

    private WeakReference ref;

    public AsyncTaskSong(WeakReference ref, String email) {
        super();
        this.ref = ref;
        this.email = email;
    }

    @Override
    protected List<Song> doInBackground(String... strings) {

        songs = new ArrayList<>();

        firebaseStorage = FirebaseStorage.getInstance();

        Log.i("Main", "Entramos Async");

        ContentResolver contentResolver = ref.getContext().getContentResolver();

        Uri songuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor songCursor = contentResolver.query(songuri, null, null, null, null);

        Log.d("sergio", "PATH: " + songuri.getPath());

        if (songCursor != null && songCursor.moveToFirst()) {

            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int imagen = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);


            storageReference = firebaseStorage.getReference();



            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                String currentImae = songCursor.getString(imagen);

                Log.d("sergio","DATA " + currentTitle);


                String[] fileName = currentImae.split("/");

                //en la posicion 5 tenemos el string del titulo mas la extension .mp3
                Log.d("sergio","DATA NAME " + fileName[5]);

//                songReference = storageReference.child(fileName[5]);
//
//                songFileReference = storageReference.child(email + "/" + fileName[5]);
//
//                //true
//                songReference.getName().equals(songFileReference.getName());
//
//                //false
//                songReference.getPath().equals(songFileReference.getPath());

                storageReference = firebaseStorage.getReference().child(email + "/" + fileName[5]);

                Uri songFile = Uri.fromFile(new File(currentImae));

                UploadTask uploadTask = storageReference.putFile(songFile);

                uploadTask.addOnCompleteListener(task -> Log.d("sergio", "file subido"));

                mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(currentImae);
                try {
                    art = mediaMetadataRetriever.getEmbeddedPicture();
                    songimage = BitmapFactory.decodeByteArray(art, 0, art.length);

                } catch (Exception e) {
                    songimage = null;
                }

                Song song = new Song(currentTitle, currentArtist, songimage);
                Log.d("Main", "doInBackground: " + song.toString());
                songs.add(song);

            } while (songCursor.moveToNext());



        }

        return songs;
    }

    @Override
    public void onPostExecute(List<Song> result) {
        ref.finished(result);
    }


}
