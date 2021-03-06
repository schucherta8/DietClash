package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodDungeonContract;

public class BossRoom extends AppCompatActivity {

    private ProgressBar monsterHealthBar;
    private TextView status;
    private TextView progress;
    private TextView total;
    private ImageView imageView;

    private SQLiteDatabase db;

    private Monster monster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_room);
        monsterHealthBar = findViewById(R.id.monster_healthbar);
        status = findViewById(R.id.monster_status);
        progress = findViewById(R.id.monster_health_progress);
        total = findViewById(R.id.monster_health_total);
        imageView = findViewById(R.id.monster_picture);
        readMonsterFromDB();
        //If there exist at least a monster
        if(monster != null){
            switch (monster.getType()){
                case EASY:
                    imageView.setImageResource(R.drawable.bad_egg);
                    break;
                case MEDIUM:
                    imageView.setImageResource(R.drawable.bad_apple);
                    break;
                case HARD:
                    imageView.setImageResource(R.drawable.bad_cabbage);
            }
            //Check for expiration
            if(!monster.isDefeated()){
                monsterHealthBar.setMax(monster.getHealth());
                monsterHealthBar.setProgress(monster.getHealthRemainder());
                progress.setText(String.valueOf(monster.getHealthRemainder()));
                total.setText(String.valueOf(monster.getHealth()));
                status.setText(R.string.monster_alive);
            } else{
                monsterHealthBar.setMax(monster.getHealth());
                monsterHealthBar.setProgress(monster.getHealthRemainder());
                progress.setText(String.valueOf(monster.getHealthRemainder()));
                total.setText(String.valueOf(monster.getHealth()));
                status.setText(R.string.monster_dead);
            }
        } else {
            monsterHealthBar.setMax(0);
            monsterHealthBar.setProgress(0);
            progress.setText("");
            total.setText("");
            status.setText("I wonder what this place is.");
            imageView.setImageResource(R.drawable.question);
        }
    }

    /**
     * Read in the most recent monster added to the table.
     *
     */
   void readMonsterFromDB(){
       //Access the database
        FoodDBHelper helper = new FoodDBHelper(getApplicationContext());
        db = helper.getReadableDatabase();

        //Values needed to create the query
        String TABLE_NAME = FoodDungeonContract.FoodDungeon.TABLE_NAME;
        String[] COLUMNS = {FoodDungeonContract.FoodDungeon.COLUMN_NAME_MONSTER_TYPE,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_MAX_HEALTH,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION,
                FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED};

        //Most recent Monster
        String SORT_ORDER = FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION+ " DESC";

        //Query the table for the serving goal per food group.
        Cursor cursor = db.query(TABLE_NAME,COLUMNS,null,null,
                null,null,SORT_ORDER);

       //If there exist a record in the table. Get the all serving amounts per food group.
        if(cursor.moveToNext()){
            String monsterType = cursor.getString(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MONSTER_TYPE));
            int maxHealth = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MAX_HEALTH));
            int meatServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_MEAT_SERVINGS));
            int fruitServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_FRUIT_SERVINGS));
            int dairyServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_DAIRY_SERVINGS));
            int veggieServingGoal = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_VEGGIE_SERVINGS));
            String expiration = cursor.getString(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_EXPIRATION));
            int defeated = cursor.getInt(cursor.getColumnIndexOrThrow(
                    FoodDungeonContract.FoodDungeon.COLUMN_NAME_DEFEATED));
            switch (MONSTER_TYPE.valueOf(monsterType)){
                case EASY:
                    monster = new EasyMonster(MONSTER_TYPE.valueOf(monsterType),
                            maxHealth,meatServingGoal,fruitServingGoal,dairyServingGoal,veggieServingGoal,
                            expiration,defeated == 1);
                    break;
                case MEDIUM:
                    monster = new MediumMonster(MONSTER_TYPE.valueOf(monsterType),
                            maxHealth,meatServingGoal,fruitServingGoal,dairyServingGoal,veggieServingGoal,
                            expiration,defeated == 1);
                    break;
                case HARD:
                    monster = new HardMonster(MONSTER_TYPE.valueOf(monsterType),
                            maxHealth,meatServingGoal,fruitServingGoal,dairyServingGoal,veggieServingGoal,
                            expiration,defeated == 1);
            }
        }
        cursor.close();
    }

}
