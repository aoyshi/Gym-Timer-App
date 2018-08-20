package com.arunika.timer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    public static final String SELECTED_TIME = "arunika.com.timer.MESSAGE";
    private Button fifteenBtn;
    private Button twentyBtn;
    private Button thirtyBtn;
    private Button fortyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Context context = this; //store main activity context

        //detect double tap & send appropriate time intent to timer activity
        final GestureDetector gd = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Intent intent = new Intent(context, TimerActivity.class);
                intent.putExtra(SELECTED_TIME, seconds); //store seconds selected in intent
                startActivity(intent); //send intent to timer activity
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });

        //get reference to buttons
        fifteenBtn = findViewById(R.id.fifteenBtn);
        twentyBtn = findViewById(R.id.twentyBtn);
        thirtyBtn = findViewById(R.id.thirtyBtn);
        fortyBtn = findViewById(R.id.fortyBtn);

        //give functionaility to each button
        fifteenBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seconds = 15;
                return gd.onTouchEvent(event); //calls onDoubleTap in gd instance
            }
        });
        twentyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seconds = 20;
                return gd.onTouchEvent(event);
            }
        });
        thirtyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seconds = 30;
                return gd.onTouchEvent(event);
            }
        });
        fortyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                seconds = 45;
                return gd.onTouchEvent(event);
            }
        });

    }
}
