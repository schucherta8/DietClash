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
     * Updated Monster Kill Achievements
     * 
     */
    private void updateMonsterAchievements(){
        //Get all monsters that were defeated
        long monstersDefeated = DatabaseUtils.queryNumEntries(db,
                FoodDungeonContract.FoodDungeon.TABLE_NAME,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED + "=1");

        //Create a list of all Monster Kill Achievement
        List<Achievement> list = new ArrayList<>();
        if(!map.isEmpty()){
            list.add(map.get("FIRST BLOOD!"));
            list.add(map.get("Monster Slayer!"));
            list.add(map.get("The hero we need, but don't deserve!"));
        }

        //Update information needed to update achievement record
        String TABLE_NAME = FoodAchievementsContract.FoodAchievements.TABLE_NAME;
        String WHERE = FoodAchievementsContract.FoodAchievements.COLUMN_NAME_TITLE +"=?";

        //Update achievement records
        for(Achievement achievement : list){
            //Only update achievements if progress does not equal goal
            if(achievement.getProgress() != achievement.getGoal()){
                //If the monsters defeated is less than goal
                if(monstersDefeated <= achievement.getGoal()){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS,monstersDefeated);
                    db.update(TABLE_NAME,contentValues,WHERE,new String[]{achievement.getTitle()});
                } else{//Monsters defeated is greater than the goal
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FoodAchievementsContract.FoodAchievements.COLUMN_NAME_PROGRESS,achievement.getGoal());
                    db.update(TABLE_NAME,contentValues,WHERE+achievement.getTitle(),null);
                }
            }
        }
    }
}
