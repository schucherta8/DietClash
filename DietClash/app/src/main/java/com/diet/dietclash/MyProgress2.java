package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.diet.dietclash.ui.myprogress2.MyProgress2Fragment;

public class MyProgress2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_progress2_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MyProgress2Fragment.newInstance())
                    .commitNow();
        }
    }
}
