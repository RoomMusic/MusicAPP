package com.example.vidiic.appmusic.classes;

import android.graphics.Bitmap;

/**
 * Created by Vidiic on 11/03/2018.
 */

public class Song {

    private String name;
    private String artist;
    private Bitmap imageSong;

    public Song(String name, String artist, Bitmap imageSong) {
        this.name = name;
        this.artist = artist;
        this.imageSong = imageSong;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public Bitmap getImageSong() {
        return imageSong;

    }

    @Override
    public String toString() {
        return "Song{" +
            "name='" + name + '\'' +
            ", artist='" + artist + '\'' +
            ", imageSong=" + imageSong +
            '}';
    }
}
