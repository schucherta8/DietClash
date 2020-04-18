package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.diet.dietclash.FoodDB.FoodAchievementsContract;
import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodDungeonContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAchievements extends AppCompatActivity {

    private List<Achievement> achievements;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FoodDBHelper helper;
    private SQLiteDatabase db;
    private HashMap<String,Achievement> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievements);
        recyclerView = findViewById(R.id.recycler_view);

        //This may change in the future
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing Database
        helper = new FoodDBHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        //Read achievements from database
        achievements = getAchievementsFromDatabase();
        //achievements = createAchievements();
        //If no achievements exist in the database
        //Create and insert achievements
        if(achievements.isEmpty()){
            achievements = createAchievements();
            insertAchievements();
        }
        updateMonsterAchievements();
        mAdapter = new AchievementAdapter(achievements);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Get the achievements that are stored in the database.
     *
     * @return an achievement list if it exist in the database, else null
     */
    private List<Achievement> getAchievementsFromDatabase(){
        //Return all columns
        String TABLE_NAME = FoodAchievementsContract.FoodAchievements.TABLE_NAME;
        String[] COLUMNS = {FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_DESCRIPTION,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_GOAL,
                FoodAchievementsContract.FoodAchievements.COLUMN_NAME_COMPLETED};
        Cursor cursor = db.query(TABLE_NAME,COLUMNS,null,null,
                null,null,null);

        //Populate our achievement list from database
        List<Achievement> achievements = new ArrayList<>();
        map = new HashMap<>();
        while(cursor.moveToNext()){
            System.out.println("Getting record from achievements...");
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE));
            String description = cursor.getString(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_DESCRIPTION));
            int progress = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS));
            int goal = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_GOAL));
            int completed = cursor.getInt(
                    cursor.getColumnIndexOrThrow(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_COMPLETED));
            achievements.add(new Achievement(title,description,progress,goal,completed == 1));
            map.put(title,new Achievement(title,description,progress,goal,completed == 1));
        }
        cursor.close();
        return achievements;
    }

    /**
     * Insert our achievements into the database.
     */
    private void insertAchievements(){
        for (Achievement achievement : achievements){
            ContentValues contentValues = new ContentValues();
            contentValues.put(
                    FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE,
                    achievement.getTitle()
            );
            contentValues.put(
                    FoodAchievementsContract.FoodAchievements.COLUMN_NAME_DESCRIPTION,
                    achievement.getDescription()
            );
            contentValues.put(
                    FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS,
                    achievement.getProgress()
            );
            contentValues.put(
                    FoodAchievementsContract.FoodAchievements.COLUMN_NAME_GOAL,
                    achievement.getGoal()
            );
            contentValues.put(
                    FoodAchievementsContract.FoodAchievements.COLUMN_NAME_COMPLETED,
                    achievement.getIsCompleted()
            );
            db.insert(FoodAchievementsContract.FoodAchievements.TABLE_NAME,
                    null,contentValues);
        }
    }

    /**
     * Create the achievements for the database.
     * TODO: FIND A WAY TO TELL IF SOMEONE DEFEATED A MONSTER
     * TODO: FIND A WAY TO TELL IF SOMEONE COMPLETED THEIR GOAL
     *
     * @return an achievement list
     */
    private List<Achievement> createAchievements(){
        List<Achievement> achievements = new ArrayList<>();
        achievements.add(new Achievement("FIRST BLOOD!",
                "Defeat 1 monster.",0,1,false));
        achievements.add(new Achievement("Monster Slayer!",
                "Defeat 10 monsters.",0,10,false));
        achievements.add(new Achievement("The hero we need, but don't deserve!",
                "Defeat 30 monsters.",0,30,false));
        achievements.add(new Achievement("Gotta start somewhere!",
                "Complete a personal goal.",0,1,false));
        achievements.add(new Achievement("Slow and steady wins the race!",
                "Complete 4 weekly goals.",0,4,false));
        achievements.add(new Achievement("Look at me now!",
                "Complete 12 weekly goals",0,12,false));
        achievements.add(new Achievement("Balance Diet!",
                "Eat the recommended daily diet provided by the America Heart Association.",
                0,20,false));
        return achievements;
    }

    /**
     * SELECT COUNT(id)
     * FROM dungeon
     * WHERE dungeon.defeated == 1
     *
     * Returns int?
     */
    private void updateMonsterAchievements(){
        long count = DatabaseUtils.queryNumEntries(db,
                FoodDungeonContract.FoodDungeon.TABLE_NAME,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED + "=1");
        if(count < 30){
            Achievement firstBlood = map.get("Defeat 1 monster.");
        }
    }
}
