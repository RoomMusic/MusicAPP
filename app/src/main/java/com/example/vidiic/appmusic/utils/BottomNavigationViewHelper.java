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
        bottomNavigationViewEx.setTextVisibility(false);

    }
    public void organizeBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx, final Context context){
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_folder:
                        Toast.makeText(context, "Action muisc",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_music:
                        Toast.makeText(context, "Action muisc",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_share:
                        Toast.makeText(context, "Action share",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_user:
                        Toast.makeText(context, "Action user",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_settings:
                        Toast.makeText(context, "Action user",Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
    }
}
