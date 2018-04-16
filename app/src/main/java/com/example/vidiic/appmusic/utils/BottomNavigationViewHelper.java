package com.example.vidiic.appmusic.utils;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.vidiic.appmusic.MainActivity;
import com.example.vidiic.appmusic.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;



public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHe1";
    public void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setIconSize(25,25);

    }
    public void organizeBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx, final Context context){
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_folder:
                        item.getIcon().getCurrent().setAlpha(1);
                        break;
                    case R.id.action_music:
                        break;
                    case R.id.action_share:
                        break;
                    case R.id.action_user:
                        break;
                    case R.id.action_settings:
                        break;
                }
                return true;
            }
        });
    }
}

