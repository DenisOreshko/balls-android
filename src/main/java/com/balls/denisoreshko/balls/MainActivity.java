package com.balls.denisoreshko.balls;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private com.balls.denisoreshko.balls.HolstSurfaceView holstSurfaceView;
    private TextView twScore;
    private TextView twTimer;
    private TextView twGameOver;
    private int xTouch, yTouch;
    private int timeInSecond;
    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeInSecond = 20;

        holstSurfaceView = findViewById(R.id.holstSurfaceView);
        holstSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                xTouch = (int)motionEvent.getX();
                yTouch = (int)motionEvent.getY();
                if(holstSurfaceView.isKillBall(xTouch, yTouch)) {
                    twScore.setText("" + holstSurfaceView.getScore());
                }
                return false;
            }
        });

        twScore = findViewById(R.id.twScore);
        twTimer = findViewById(R.id.twTimer);
        twGameOver = findViewById(R.id.game_over_textView);
        twGameOver.setVisibility(View.INVISIBLE);

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeInSecond--;
                        twTimer.setText("" + timeInSecond / 60 + ":" + timeInSecond % 60);
                        if (timeInSecond == 0){
                            mTimer.cancel();
                            mTimer = null;
                            holstSurfaceView.setGameOverState();
                            twGameOver.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }, 0,1000);

    }
}
