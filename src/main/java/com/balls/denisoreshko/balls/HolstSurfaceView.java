package com.balls.denisoreshko.balls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

public class HolstSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private int xPos = 100;
    private int yPos = 100;

    private int xVel;
    private int yVel;

    private int width;
    private int height;

    private int circleRadius = 0;
    private int circleRadiusMax = 30;
    private boolean circleRadiusUp = true;
    private Paint circlePaint;

    private ArrayList<Ball> balls;
    private Ball ball;
    private int score = 0;
    private int scoreSession = 0;
    private int ballsCount = 1;

    private MyTimerTask myTimerTask;

    UpdateThread updateThread;

    private Random random;
    private boolean ballCountUP = true;
    private boolean game_over = false;

    private Context context;

    public HolstSurfaceView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        myTimerTask = new MyTimerTask(30000,2000, this);
        random = new Random();
        balls = new ArrayList<Ball>();
    }

    public HolstSurfaceView(Context context,AttributeSet attributeSet) {
        super(context,attributeSet);
        this.context = context;
        getHolder().addCallback(this);
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        myTimerTask = new MyTimerTask(60000,2000, this);
        random = new Random();
        balls = new ArrayList<Ball>();
    }

    public HolstSurfaceView(Context context,AttributeSet attributeSet, int defStyle) {
        super(context,attributeSet,defStyle);
        this.context = context;
        getHolder().addCallback(this);
        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);
        myTimerTask = new MyTimerTask(6000,2000, this);
        random = new Random();
        balls = new ArrayList<Ball>();
    }

    private void initBallsList(int width, int height){
        Ball ball = new Ball(width, height,context);
        for (int i = 1; i <= 20; i++) {
            ball = new Ball(width, height,context);
            ball.setId(i);
            balls.add(ball);
        }
    }
    /*public void updatePhysics() {
        xPos += xVel;
        yPos += yVel;

        if (yPos - circleRadius < 0 || yPos + circleRadius > height) {
            if (yPos - circleRadius < 0) {
                yPos = circleRadius;
            }else{
                yPos = height - circleRadius;
            }
            yVel *= -1;
        }
        if (xPos - circleRadius < 0 || xPos + circleRadius > width) {
            if (xPos - circleRadius < 0) {
                xPos = circleRadius;
            } else {
                xPos = width - circleRadius;
            }
            xVel *= -1;
        }
    }*/

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
        width = surfaceFrame.width();
        height = surfaceFrame.height();
        ball = new Ball(width, height, context);
        initBallsList(width,height);
        updateThread = new UpdateThread(this);
        updateThread.setRunning(true);
        updateThread.start();
        myTimerTask.start();
        /*Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.GRAY);
        canvas.drawCircle(100,100,20, circlePaint);
        getHolder().unlockCanvasAndPost(canvas);*/
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        updateThread.setRunning(false);
        while (retry) {
            try {
                updateThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        myTimerTask.onFinish();
        myTimerTask.cancel();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*canvas.drawColor(Color.WHITE);
        for (int i = 0; i < ballsCount; i++) {
            balls.get(i).draw(canvas);
        }*/
    }

    private void setAllBallsVisible(){
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).setVisible(true);
        }
    }

    public boolean isKillBall(int xT, int yT){
        for (int i = 0; i < ballsCount; i++) {
            Ball ball = balls.get(i);
            if (((xT < ball.getX() + ball.getRadius() + 20) && (xT > ball.getX() - ball.getRadius() - 20)) && ((yT < ball.getY() + ball.getRadius() + 20) && (yT > ball.getY() - ball.getRadius() - 20))) {
                ball.destroy();

                if (!game_over)
                    score++;

                scoreSession++;

                if (ballCountUP == true) {
                    if (scoreSession == ballsCount) {
                        ballsCount++;
                        scoreSession = 0;
                        setAllBallsVisible();
                    }
                }
                if (ballCountUP == false) {
                    if (scoreSession == ballsCount) {
                        ballsCount--;
                        scoreSession = 0;
                        setAllBallsVisible();
                    }
                }

                if (ballsCount > balls.size()){
                    ballsCount = balls.size();
                    ballCountUP = false;
                }
                if (ballsCount < 1){
                    ballsCount = 1;
                    ballCountUP = true;
                }
                return true;
            }
        }
        return false;
    }

    public void drawCircle(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < ballsCount; i++) {
            balls.get(i).draw(canvas);
        }
    }

    public int getScore() {
        return score;
    }

    public void setGameOverState() {
        game_over = true;
    }
}
