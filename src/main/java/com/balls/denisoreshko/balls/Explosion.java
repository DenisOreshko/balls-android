package com.balls.denisoreshko.balls;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Explosion {

    private Bitmap bitmapSource;
    private Bitmap subImage;
    private Paint paint;

    private int rowCount;
    private int colCount;

    private int WIDTH;
    private int HEIGHT;

    private int width;
    private int height;

    private int xI;
    private int yI;

    private int x;
    private int y;

    private int rowIndex = 0;
    private int colIndex = 0;
    private boolean finish = false;

    private Bitmap[] explosion;
    private Bitmap[] soldier;

    private int speedDrawExp = 0;
    private int speedAnimationEffect = 1;//чем меньше тем быстрее 1 самая быстрая

    private Matrix matrix;

    public Explosion(Context context){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        //bitmapSource = BitmapFactory.decodeResource(context.getResources() ,R.drawable.explosion);
        //bitmapSource = BitmapFactory.decodeResource(context.getResources() ,R.drawable.soldier);
        bitmapSource = BitmapFactory.decodeResource(context.getResources() ,R.drawable.ball_boom);

        rowCount = 4;
        colCount = 4;

        WIDTH = bitmapSource.getWidth();
        HEIGHT = bitmapSource.getHeight();

        width = WIDTH/colCount;
        height = HEIGHT/rowCount;

        explosion = new Bitmap[rowCount*colCount];
        for (int j = 0; j < rowCount; j++) {
            for (int i = 0; i < colCount; i++) {
                explosion[colIndex] = createSubImageAt(j, i);
                colIndex++;
            }
        }
        colIndex = 0;
    }

    protected Bitmap createSubImageAt(int row, int col)  {
        subImage = Bitmap.createBitmap(bitmapSource, col* width, row* height ,width,height);
        return subImage;
    }

    public void update()  {
        this.colIndex++;

        if(this.colIndex >= this.colCount)  {
            this.colIndex =0;
            this.rowIndex++;

            if(this.rowIndex>= this.rowCount)  {
                this.finish= true;
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Canvas canvas)  {
        if (!isFinish()) {
            speedDrawExp++;
            canvas.drawBitmap(explosion[colIndex], x - width / 2, y - height / 2, paint);
            if (speedDrawExp > speedAnimationEffect) {
                colIndex++;
                if (colIndex > explosion.length-1) {
                    colIndex = 0;
                    finish = true;
                }
                speedDrawExp = 0;
            }
        }
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean isFinish() {
        return finish;
    }
}
