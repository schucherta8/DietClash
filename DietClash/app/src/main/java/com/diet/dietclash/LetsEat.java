package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class LetsEat extends AppCompatActivity {

    private TextView meatText;
    private TextView veggieText;
    private TextView fruitText;
    private TextView dairyText;
    private int meatCount;
    private int veggieCount;
    private int fruitCount;
    private int dairyCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lets_eat);

        //initialize text and counts
        meatText = findViewById(R.id.currentMeatServing);
        veggieText = findViewById(R.id.currentVeggiesServing);
        fruitText = findViewById(R.id.currentFruitServing);
        dairyText = findViewById(R.id.currentDairyServing);
        meatCount = veggieCount = fruitCount = dairyCount = 0;
        this.updateMeat();
        this.updateVeggie();
        this.updateFruit();
        this.updateDairy();
    }

    private void updateMeat() {
        meatText.setText(String.valueOf(meatCount));
    }
    private void updateVeggie() {
        veggieText.setText(String.valueOf(veggieCount));
    }
    private void updateFruit() {
        fruitText.setText(String.valueOf(fruitCount));
    }
    private void updateDairy() {
        dairyText.setText(String.valueOf(dairyCount));
    }

    public void AddMeat(View view) {
        ++meatCount;
        updateMeat();
    }

    public void AddVeggie(View view) {
        ++veggieCount;
        updateVeggie();
    }

    public void AddFruit(View view) {
        ++fruitCount;
        updateFruit();
    }

    public void AddDairy(View view) {
        ++dairyCount;
        updateDairy();
    }

    public void Submit(View view) {
        if(meatCount == 0 && veggieCount == 0 && fruitCount == 0 && dairyCount == 0) {
            Snackbar.make(view, "Please enter food information", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        //do save stuff
        meatCount = veggieCount = fruitCount = dairyCount = 0;
        this.updateMeat();
        this.updateVeggie();
        this.updateFruit();
        this.updateDairy();
        Snackbar.make(view, "Saved food information", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void Reset(View view) {
        meatCount = veggieCount = fruitCount = dairyCount = 0;
        this.updateMeat();
        this.updateVeggie();
        this.updateFruit();
        this.updateDairy();
        Snackbar.make(view, "Food information reset", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
