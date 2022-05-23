package com.balls.denisoreshko.balls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Ball {
    private int x;
    private int y;
    private int radius;
    private int radiusMax;
    private int id;
    private Paint paintCircle;
    private Random random;
    private int width, height;
    private boolean radiusUp = false;
    private MyTimerTask myTimerTask;
    private boolean delayTimerFinish = false;
    private boolean delayTimerIsStarted = false;
    private Paint textPaint;
    private int textSize = 30;
    private Paint roundPaint;
    private boolean visible = true;
    private int delayAppearBall = 2000;
    private int speedRadius = 2;
    private int counterCallDraw = 0;
    private boolean destroy = false;
    private Explosion explosion;
    private int destroyX;
    private int destroyY;

    public Ball(int widthCanvas, int heightCanvas, Context context){
        this.width = widthCanvas;
        this.height = heightCanvas;
        radiusUp = true;
        paintCircle = new Paint();
        textPaint = new Paint();
        textPaint.setColor(Color.LTGRAY);
        textPaint.setTextSize(textSize);
        roundPaint = new Paint();
        roundPaint.setColor(Color.LTGRAY);
        roundPaint.setStyle(Paint.Style.STROKE);
        random = new Random();
        radiusMax = 25 + random.nextInt(50);
        updateCordRandom();
        setRandomColorToPaint(paintCircle);
        delayAppearBall = random.nextInt(4000);
        speedRadius = 1 + random.nextInt(3);
        myTimerTask = new MyTimerTask(delayAppearBall,100, this);
        explosion = new Explosion(context);
    }

    public void draw(Canvas canvas){
        if(visible) {
            if (!delayTimerIsStarted) {
                setDelayTimerStart();
            }
            canvas.drawCircle(x, y, radius, roundPaint);
            canvas.drawCircle(x, y, radius, paintCircle);
            //
            if (delayTimerFinish || id == 1) {
                updateRadius();
            }
        }
        if (destroy){
            //canvas.drawText("Booom!!!", destroyX, destroyY, textPaint);
            explosion.draw(canvas);
        }
    }

    private void setDelayTimerStart(){
        myTimerTask.start();
        delayTimerIsStarted = true;
        Log.d("TAG_BALL", "SET_DELAY_START Ball id: " + id);
    }

    private void setRandomColorToPaint(Paint paint){
        int red = random.nextInt(220);
        int green = random.nextInt(255);
        int blue = random.nextInt(250);
        int alpha = 30 + random.nextInt(255);
        paint.setColor(Color.argb(alpha, red, green, blue));
    }

    private void updateSpeedRadiusRandom(){
        speedRadius = 1 + random.nextInt(3);
        counterCallDraw = 0;
    }

    private void updateCordRandom(){
        radius = 0;
        radiusMax = 20 + random.nextInt(50);
        x = radiusMax + random.nextInt(width - 2*radiusMax);
        y = radiusMax + random.nextInt(height - 2*radiusMax);
        radiusUp = true;
    }

    private void updateRadius(){
        counterCallDraw++;
        if (counterCallDraw >= speedRadius) {
            if (radiusUp) {
                radius++;
            } else {
                radius--;
            }
            if (radius < 0) {
                radiusUp = true;
                updateCordRandom();
            }
            if (radius > radiusMax)
                radiusUp = false;
            counterCallDraw = 0;
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getRadius(){
        return radius;
    }

    public void destroy(){
        destroyX = x;
        destroyY = y;
        explosion.setX(destroyX);
        explosion.setY(destroyY);
        explosion.setFinish(false);
        visible = false;
        destroy = true;
        setRandomColorToPaint(paintCircle);
        updateCordRandom();
        updateSpeedRadiusRandom();
        delayTimerIsStarted = false;
        delayTimerFinish = false;
        Log.d("TAG_BALL", "destroy Ball id: " + id);
    }

    public void setVisible(boolean v){
        visible = v;
        //destroy = false;
    }



    public void setDelayTimerFinish(boolean b) {
        delayTimerFinish = b;
    }

    public void setId(int id) {
        this.id = id;
    }
}
