package com.ddducn.assignment8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class GameCanvas extends View {
    // paint
    private Paint paint = new Paint();

    // objects
    private Circle ball;
    private Circle[] targets = new Circle[3];
    private Rectangle[] obstacles = new Rectangle[6];

    private GestureDetector gestureDetector;

    private double ballAccX = 0;
    private double ballAccY = 0;

    private double canvasW = 0;
    private double canvasH = 0;

    // const time value, t * t / 2
    private final double STEP_TIMES = 0.04 * 0.04 * 0.5;

    public GameCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        // create ball
        ball = new Circle(750.0, 1500.0, 50.0);
        ball.setColor(getResources().getColor(R.color.purple));

        // create targets
        int[][] targetsCoors = {{350, 200}, {700, 400}, {150, 800}};
        for (int i = 0; i < targets.length; i++) {
            targets[i] = new Circle(targetsCoors[i][0], targetsCoors[i][1], 50);
            targets[i].setColor(getResources().getColor(R.color.colorPrimary));
        }

        // create obstacles
        int[][] obstaclesCoors = {{400, 600}, {650, 100}, {700, 950}, {400, 1100}, {400, 1400}, {500, 1300}};
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Rectangle(obstaclesCoors[i][0], obstaclesCoors[i][1], 250, 25);
            obstacles[i].setColor(getResources().getColor(R.color.black));
        }

        gestureDetector = new GestureDetector(context, new FlingGestureListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        flingBall();

        // draw ball
        paint.setColor(ball.getColor());
        canvas.drawCircle((float) ball.getX(), (float) ball.getY(), (float) ball.getR(), paint);

        // draw targets
        paint.setColor(targets[0].getColor());
        for (Circle target: targets) {
            canvas.drawCircle((float) target.getX(), (float) target.getY(), (float) target.getR(), paint);
        }

        // draw obstacles
        paint.setColor(obstacles[0].getColor());
        for (Rectangle obstacle: obstacles) {
            canvas.drawRect((float) obstacle.getX(), (float) obstacle.getY(), (float) (obstacle.getX() + obstacle.getWidth()), (float) (obstacle.getY() + obstacle.getHeight()), paint);
        }

        invalidate();
    }

    private void flingBall() {
        if (ballAccX == 0 || ballAccY == 0) return;

        if (isToHEdge()) ballAccX = -ballAccX;
        if (isToVEdge()) ballAccY = -ballAccY;

        double dX = ballAccX * STEP_TIMES;
        double dY = ballAccY * STEP_TIMES;

        ball.moveX(dX);
        ball.moveY(dY);
    }

    private boolean isToHEdge() {
        return ball.getX() <= 0 || ball.getX() >= canvasW - ball.getR();
    }

    private boolean isToVEdge() {
        return ball.getY() <= 0 || ball.getY() >= canvasH - ball.getR();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasW = w;
        canvasH = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) return true;
        return super.onTouchEvent(event);
    }

    private class FlingGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            double touchX = e1.getX(0);
            double touchY = e1.getY(0);

            if (ball.distance(touchX, touchY) > 20) return false;

            ballAccX = velocityX;
            ballAccY = velocityY;
            return false;
        }
    }
}
