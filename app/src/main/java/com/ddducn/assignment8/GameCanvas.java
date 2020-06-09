package com.ddducn.assignment8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameCanvas extends View {
    private Paint paint = new Paint();

    // objects
    private Circle ball;
    private Circle[] targets;
    private Rectangle[] obstacles;

    public GameCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // set paint color
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setColor(getResources().getColor(R.color.purple));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = (float) ball.getX();
        float y = (float) ball.getY();
        float r = (float) ball.getR();
        canvas.drawCircle(x, y, r, paint);


    canvas.drawCircle((float) targets[0].getX(), (float) targets[0].getY(), (float) targets[0].getR(), paint);
    canvas.drawCircle((float) targets[1].getX(), (float) targets[1].getY(), (float) targets[1].getR(), paint);
    canvas.drawCircle((float) targets[2].getX(), (float) targets[2].getY(), (float) targets[2].getR(), paint);
    canvas.drawRect((float) obstacles[0].getY(),(float) obstacles[0].getX(),(float) obstacles[0].getWidth(),(float) obstacles[0].getHeight(),paint);
    canvas.drawRect((float) obstacles[1].getY(),(float) obstacles[1].getX(),(float) obstacles[1].getWidth(),(float) obstacles[1].getHeight(),paint);
    canvas.drawRect((float) obstacles[2].getY(),(float) obstacles[2].getX(),(float) obstacles[2].getWidth(),(float) obstacles[2].getHeight(),paint);
    canvas.drawRect((float) obstacles[3].getY(),(float) obstacles[3].getX(),(float) obstacles[3].getWidth(),(float) obstacles[3].getHeight(),paint);
    canvas.drawRect((float) obstacles[4].getY(),(float) obstacles[4].getX(),(float) obstacles[4].getWidth(),(float) obstacles[4].getHeight(),paint);
    canvas.drawRect((float) obstacles[5].getY(),(float) obstacles[5].getX(),(float) obstacles[5].getWidth(),(float) obstacles[5].getHeight(),paint);
        // draw objects
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        ball = new Circle(750.0f, 1700.0f, 50.0f);
        ball.setColor(getResources().getColor(R.color.purple));
        Circle c1 = new Circle(350.0f, 200.0f, 50.0f);
        c1.setColor(getResources().getColor(R.color.colorPrimary));
        Circle c2 = new Circle(700.0f, 400.0f, 50.0f);
        c2.setColor(getResources().getColor(R.color.colorPrimary));
        Circle c3 = new Circle(150.0f, 800.0f, 50.0f);
        c3.setColor(getResources().getColor(R.color.colorPrimary));

        targets = new Circle[3];
        obstacles=new Rectangle[6];
        Rectangle r1 = new Rectangle(600, 400, 150, 630);
        Rectangle r2 = new Rectangle(100, 650, 900, 70);
        Rectangle r3 = new Rectangle(950, 700, 950, 980);
        Rectangle r4 = new Rectangle(1100, 400, 150, 1130);
        Rectangle r5 = new Rectangle(1400, 400, 150, 1430);
        Rectangle r6 = new Rectangle(1300, 500, 250, 1330);
        targets[0]=c1;
        targets[1]=c2;
        targets[2]=c3;
        obstacles[0]=r1;
        obstacles[1]=r2;
        obstacles[2]=r3;
        obstacles[3]=r4;
        obstacles[4]=r5;
        obstacles[5]=r6;
        // create objects
    }
}

