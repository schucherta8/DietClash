package com.diet.dietclash;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.diet.dietclash.FoodDB.FoodDBHelper;
import com.diet.dietclash.FoodDB.FoodEntryContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //achivementsButton => MyAchievements(view)
    //getStartedButton => GetStarted(view)
    //letsEatButton => LetsEat(view)
    //myProgressButton => MyProgress(view)

    private static final String CHANNEL = "com.dietclash.diet.eat";

    public static NotificationManager nm = null;

    //SQL Database
    private FoodDBHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(nm == null) {
            nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        //db
        helper = new FoodDBHelper(getApplicationContext());
        db = helper.getWritableDatabase();

        if(!foodEaten()) {
            createNotificationChannel();
            createNotification();
        }
    }

    private boolean foodEaten() {
        String[] projection = {FoodEntryContract.FoodEntry.COLUMN_NAME_CATEGORY, FoodEntryContract.FoodEntry.COLUMN_NAME_AMOUNT};
        String[] args = {new SimpleDateFormat("yyyy-MM-dd").format(new Date())};
        Cursor cursor = db.query(FoodEntryContract.FoodEntry.TABLE_NAME, projection, FoodEntryContract.FoodEntry.COLUMN_NAME_DATE+"=?", args, null, null, null);
        return cursor.getCount() > 0;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL, "Diet Clash", NotificationManager.IMPORTANCE_LOW);
        channel.setDescription("Diet Clash");
        channel.enableVibration(true);
        nm.createNotificationChannel(channel);
    }

    private void createNotification() {
        Intent launch = new Intent(this, MainActivity.class);
        PendingIntent pending = PendingIntent.getActivity(this, 0, launch, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(MainActivity.this, CHANNEL)
                .setContentTitle("Diet Clash").setContentText("Remember to log what you've eaten!")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(CHANNEL).setContentIntent(pending).build();

        Intent notificationIntent = new Intent(this, NotificationReminder.class);
        notificationIntent.putExtra(NotificationReminder.NOTIFICATION, notification);
        notificationIntent.putExtra(NotificationReminder.ID, 101);
        PendingIntent notificationPending = PendingIntent.getBroadcast(this, 101, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), notificationPending);

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
