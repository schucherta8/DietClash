package com.diet.dietclash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BossRoom extends AppCompatActivity {

    ProgressBar progressBar;
    Button startTimer, stopTimer;
    MyCountDownTimer myCountDownTimer;
    TextView timeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss_room);

        progressBar=findViewById(R.id.progressBar);
        startTimer=findViewById(R.id.buttonStart);
        stopTimer=findViewById(R.id.buttonStop);
        timeView=findViewById(R.id.timeView);

        startTimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                myCountDownTimer = new MyCountDownTimer(10000,1000);
                myCountDownTimer.start();
            }
        });

        stopTimer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                myCountDownTimer.cancel();
            }
        });
    }

    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval){
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished){
            int progress = (int) (millisUntilFinished/1000);
            timeView.setText("Time Remaining: "+progress);
            progressBar.setProgress(progressBar.getMax()-progress);
        }

        @Override
        public void onFinish(){
            finish();
        }
    }
}
