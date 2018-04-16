package com.example.vidiic.appmusic.classes;

import java.util.Date;

/**
 * Created by Vidiic on 11/03/2018.
 */

public class User {

    private int userid;
    private String username;
    private String email;
    private String password;
    private String userName;
    private String userSecondName;
    private boolean firstIn;
    private Date registerDate;

    public User(String email, String password, Date registerDate, boolean firstIn) {
        this.email = email;
        this.password = password;
        this.registerDate = registerDate;
        this.firstIn = firstIn;
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
}
