package com.umbrella.noterecyclerview.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.umbrella.noterecyclerview.R;
import com.umbrella.noterecyclerview.RouterHolder;

public class MainActivity extends AppCompatActivity implements RouterHolder {

    private MainRouter router;

//    public MainRouter getRouter() {
//        return router;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        router = new MainRouter(getSupportFragmentManager());

        if(savedInstanceState == null) {
            router.showNotes();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_notes) {
                    router.showNotes();
                }

                if (item.getItemId() == R.id.action_info) {
                    router.showInfo();
                }
                return true;
            }
        });
    }

    @Override
    public MainRouter getMainRouter() {
        return router;
    }
}