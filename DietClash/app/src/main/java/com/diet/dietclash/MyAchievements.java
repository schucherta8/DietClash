package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MyAchievements extends AppCompatActivity {

    private List<Achievement> achievements;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievements);
        recyclerView = findViewById(R.id.recycler_view);

        //This may change in the future
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //This will read in achievements
        achievements = generateAchievements();

        mAdapter = new AchievementAdapter(achievements);
        recyclerView.setAdapter(mAdapter);
    }

    //Dummy achievements
    private List<Achievement> generateAchievements(){
        List<Achievement> items = new ArrayList<>();
        Achievement item1 = new Achievement("Monster Slayer!",
                "Defeat 10 monsters.",3,10);
        items.add(item1);
        Achievement item2 = new Achievement("Balance Diet!",
                "Eat a serving for each food group.",4,5);
        items.add(item2);
        return items;
    }
}
