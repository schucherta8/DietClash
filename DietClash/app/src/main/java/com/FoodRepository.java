package com;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodRepository {

    private FoodDao foodDao;
    private LiveData<List<FoodServings>> allFoodServings;

    public FoodRepository(Application application){
        FoodRoomDatabase db;
        db = FoodRoomDatabase.getDatabase(application);
        foodDao = db.foodDao();
        allFoodServings = foodDao.getAllFoodServings();
    }

    public LiveData<List<FoodServings>> getAllFoodServings(){
        return allFoodServings;
    }

    public void insertFoodServing(FoodServings f){
        InsertAsyncTask task = new InsertAsyncTask(foodDao);
        task.execute(f);
    }

    public void deleteFoodServing(String f){
        DeleteAsyncTask task = new DeleteAsyncTask(foodDao);
        task.execute(f);
    }

    private static class InsertAsyncTask extends AsyncTask<FoodServings,Void,Void> {

        private FoodDao asyncTaskDao;

        InsertAsyncTask(FoodDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FoodServings... params){
            asyncTaskDao.insertFoodServing(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<String,Void,Void>{

        private FoodDao asyncTaskDao;

        DeleteAsyncTask(FoodDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params){
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
