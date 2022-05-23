package com.balls.denisoreshko.balls;

import android.content.Context;
import android.graphics.Point;
import android.os.CountDownTimer;
import android.view.SurfaceView;

import java.util.TimerTask;

public class MyTimerTask extends CountDownTimer {
    HolstSurfaceView holstSurfaceView;
    Ball ball;

    public MyTimerTask(long millisInFuture, long countDownInterval, Ball ball) {
        super(millisInFuture, countDownInterval);
        this.ball = ball;
    }

    public MyTimerTask(long millisInFuture, long countDownInterval, HolstSurfaceView hsv) {
        super(millisInFuture, countDownInterval);
        holstSurfaceView = hsv;
    }

    @Override
    public void onTick(long l) {

    }

    @Override
    public void onFinish() {
        if (ball != null)
            ball.setDelayTimerFinish(true);
    }
}
