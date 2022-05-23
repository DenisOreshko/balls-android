package com.balls.denisoreshko.balls;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class UpdateThread extends Thread{
    private long time;
    private final int fps = 100;
    private boolean toRun = false;
    private HolstSurfaceView holstSurfaceView;
    private SurfaceHolder surfaceHolder;

    public UpdateThread(HolstSurfaceView h) {
        holstSurfaceView = h;
        surfaceHolder = holstSurfaceView.getHolder();
    }

    public void setRunning(boolean run) {
        toRun = run;
    }

    @Override
    public void run() {
        Canvas c;
        while (toRun) {
            long cTime = System.currentTimeMillis();

            //Log.d("TAG_cTime", "cTime = " + cTime + ", time: " + time + " " + (cTime - time));

            if ((cTime - time) > (1000/ fps)) {

                c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    //holstSurfaceView.draw(c);
                    holstSurfaceView.drawCircle(c);

                } finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }time = cTime;

            }

        }
    }
}
