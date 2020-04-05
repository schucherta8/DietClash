package com.diet.dietclash.ui.myprogress2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.FoodRepository;
import com.FoodServings;

import java.util.List;

public class MyProgress2ViewModel extends AndroidViewModel{

    private FoodRepository repository;
    private LiveData<List<FoodServings>> allFoodServings;

    public MyProgress2ViewModel(Application application){
        super(application);
        repository = new FoodRepository(application);
        allFoodServings = repository.getAllFoodServings();
    }

    public LiveData<List<FoodServings>> getAllFoodServings(){
        return allFoodServings;
    }

    public void insertWebsite(FoodServings f){
        repository.insertFoodServing(f);
    }

    public void deleteWebsite(String f){
        repository.deleteFoodServing(f);
    }

}
