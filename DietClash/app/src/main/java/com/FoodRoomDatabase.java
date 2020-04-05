package com;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FoodServings.class}, version=1, exportSchema = false)
public abstract class FoodRoomDatabase extends RoomDatabase {

    public abstract FoodDao foodDao();

    private static FoodRoomDatabase INSTANCE;

    static FoodRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized(FoodRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FoodRoomDatabase.class, "food_database").build();
                }
            }
        }
        return INSTANCE;
    }

}
