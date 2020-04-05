package com;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insertFoodServing(FoodServings f);

    @Query("DELETE FROM food WHERE id = :id")
    void delete(String id);

    @Query("SELECT * FROM food")
    LiveData<List<FoodServings>> getAllFoodServings();
}
