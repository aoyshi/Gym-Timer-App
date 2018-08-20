package com.arunika.timer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.media.MediaPlayer.OnCompletionListener;

public class TimerActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private Button cancelBtn;
    private CountDownTimer timer;
    private Chronometer simpleChronometer;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //store this context
        final Context context = this;

        // Get the Intent that started this activity and extract the time in seconds
        Intent intent = getIntent();
        int seconds = intent.getIntExtra(MainActivity.SELECTED_TIME, 0);

        //change color of button based on time selection
        changeButtonColor(seconds);

        seconds++; //plus one so initial time shows on screen first before countdown

        //capture the layout's TextView
        timeDisplay = findViewById(R.id.timeDisplay);

        //initiate the chronometer
        simpleChronometer = (Chronometer) findViewById(R.id.overtimeDisplay);
        simpleChronometer.setText(""); //hide chronometer until countDownTimer finishes

        //get reference to button
        cancelBtn = findViewById(R.id.cancelBtn);

        //start timer
        timer = new CountDownTimer(seconds * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeDisplay.setText("" + millisUntilFinished / 1000); //display reverse timer in seconds
            }

            public void onFinish() {

                //play sound twice when timer done
                mp = MediaPlayer.create(context, R.raw.timersound); //initiate sound file

                //play sound 2nd time after completion of first time
                mp.setOnCompletionListener(new OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.start(); //2nd playback
                        mp.setOnCompletionListener(null); //dont loop forever
                    }
                });

                mp.start(); //first play

                timeDisplay.setText("DONE");
                cancelBtn.setText("Go Back");
                simpleChronometer.start(); // start countUp chronometer (includes time already elapsed from count down)
            }

        }.start(); //start the countdown
    }

    public void onCancelEvent (View view) {
        if(timer!=null) //memory clean-up
            timer.cancel();

        if(simpleChronometer!=null) //stop the chronometer
            simpleChronometer.stop();

        if(mp!=null) //stop music player (if on)
            mp.stop();

        finish(); //go back to main activity (home page)
    }


     private void changeButtonColor(int seconds) {

        cancelBtn = findViewById(R.id.cancelBtn);
        switch(seconds) {
            case 15: cancelBtn.setBackgroundColor(getResources().getColor(R.color.color15));
                break;
            case 20: cancelBtn.setBackgroundColor(getResources().getColor(R.color.color20));
                break;
            case 30: cancelBtn.setBackgroundColor(getResources().getColor(R.color.color30));
                break;
            case 45: cancelBtn.setBackgroundColor(getResources().getColor(R.color.color45));
                break;
            default: cancelBtn.setBackgroundColor(getResources().getColor(R.color.colorCancelDefault));

        }

     }

     //do housekeeping if user presses back button during timer
     @Override
     public void onBackPressed() {
         if(timer!=null) //memory clean-up
             timer.cancel();

         if(simpleChronometer!=null) //stop the chronometer
             simpleChronometer.stop();

         if(mp!=null) //stop music player (if on)
             mp.stop();

         finish();
     }


}
