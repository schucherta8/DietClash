package com.diet.dietclash;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //achivementsButton => MyAchievements(view)
    //getStartedButton => GetStarted(view)
    //letsEatButton => LetsEat(view)
    //myProgressButton => MyProgress(view)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * On click of the user icon, launches the MyAchievements Activity.
     * @param view
     */
    public void myAchievements(View view) {
        Intent i = new Intent(this, MyAchievements.class);
        startActivity(i);
    }

    /**
     * On click of the Get Started Button, launches the GetStarted Activity.
     * @param view
     */
    public void getStarted(View view) {
        Intent i = new Intent(this,GetStarted.class);
        startActivity(i);
    }

    /**
     * On click of the Let's Eat Button, launches the LetsEat Activity.
     * @param view
     */
    public void letsEat(View view) {
        Intent i = new Intent(this, LetsEat.class);
        startActivity(i);
    }

    /**
     * On click of the My Progress Button, launches the MyProgress Activity.
     * @param view
     */
    public void myProgress(View view) {
        Intent i = new Intent(this, MyProgress.class);
        startActivity(i);
    }

    /**
     * On click of the Stores Near Me Button, launches the StoreLocator Activity.
     * @param view
     */
    public void storesNearMe(View view) {
        Intent i = new Intent(this, StoreLocator.class);
        startActivity(i);
    }

    /**
     * On click of the Boss Room Button, launches the BossRoom Activity.
     * @param view
     */
    public void bossRoom(View view) {
        Intent i = new Intent(this, BossRoom.class);
        startActivity(i);
    }
}
