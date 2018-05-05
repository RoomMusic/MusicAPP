package com.example.vidiic.appmusic.classes;

import java.util.Date;
import java.util.List;

/**
 * Created by Vidiic on 11/03/2018.
 */

public class User {

    private String userid;
    private String username;
    private String email;
    private String password;
    private String userName;
    private String userSecondName;
    private boolean firstIn;
    private Date registerDate;
    private List<Song> songList;

    //constructor para cuando el usuario esta registrandose
    public User(String userId, String email, String password, Date registerDate, boolean firstIn) {
        this.userid = userId;
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
        this.firstIn = firstIn;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isFirstIn() {
        return firstIn;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }
}
