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

public class GameCanvas extends View implements GameActivityDelegate {
    // paint
    private Paint paint = new Paint();

    // objects
    private Circle ball;
    private Circle[] targets = new Circle[6];
    private Rectangle[] obstacles = new Rectangle[6];

    private GestureDetector gestureDetector;

    private double ballAccX = 0;
    private double ballAccY = 0;

    private double canvasW = 0;
    private double canvasH = 0;

    // const time value, t * t / 2
    private final double STEP_TIMES = 0.04 * 0.04 * 0.5;

    private int hObsMoveFlag = 1;
    private int vObsMoveFlag = 1;
    private final double OBS_MOVE_STEP = 10;

    private final int[] BALL_ORIGINAL = {750, 1500};

    private boolean isPlaying = false;

    public GamePlayDelegate gamePlayDelegate;

    public GameCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setupBall();
        setupTargets();
        setupObstacles();

        gestureDetector = new GestureDetector(context, new FlingGestureListener());

        ((GameActivity) context).gameActivityDelegate = this;
    }

    private void setupBall() {
        // create ball
        ball = new Circle(BALL_ORIGINAL[0], BALL_ORIGINAL[1], 50.0, 0);
        ball.setColor(getResources().getColor(R.color.purple));
    }

    private void setupTargets() {
        // create targets
        int[][] targetsCoors = {{550, 200}, {700, 400}, {150, 800}, {350, 500}, {350, 1400}, {750, 800}};
        int[] scores = {1, 1, 2, 2, 3, 3};
        for (int i = 0; i < targets.length; i++) {
            targets[i] = new Circle(targetsCoors[i][0], targetsCoors[i][1], 50, scores[i]);
            targets[i].setColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void setupObstacles() {
        // create obstacles
        int[][] obstaclesCoors = {{100, 600}, {150, 100}, {650, 950}, {300, 1600}, {100, 1400}, {50, 1300}};
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Rectangle(obstaclesCoors[i][0], obstaclesCoors[i][1], 150, 25);
            obstacles[i].setColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        flingBall();
        moveObstacles();
        obstacleCollisionDetect();
        targetCollisionDetect();

        drawObjects(canvas);

        invalidate();
    }

    private void drawObjects(Canvas canvas) {
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
    }

    private void flingBall() {
        if (!isPlaying) return;

        if (isToHEdge()) ballAccX = -ballAccX;
        if (isToVEdge()) ballAccY = -ballAccY;

        double dX = ballAccX * STEP_TIMES;
        double dY = ballAccY * STEP_TIMES;

        ball.moveX(dX);
        ball.moveY(dY);
    }

    private boolean isToHEdge() {
        return ball.getX() <= ball.getR() ||
                ball.getX() >= canvasW - ball.getR();
    }

    private boolean isToVEdge() {
        return ball.getY() <= ball.getR() ||
                ball.getY() >= canvasH - ball.getR();
    }

    private void moveObstacles() {
        // horizontal movable obs
        Rectangle hObs = obstacles[5];
        if (hObs.getX() <= 350) hObsMoveFlag = 1;
        if (hObs.getX() >= 700) hObsMoveFlag = -1;
        hObs.moveX(OBS_MOVE_STEP * hObsMoveFlag);

        // vertical movable obs
        Rectangle vObs = obstacles[4];
        if (vObs.getY() <= 900) vObsMoveFlag = 1;
        if (vObs.getY() >= 1200) vObsMoveFlag = -1;
        vObs.moveY(OBS_MOVE_STEP * vObsMoveFlag);
    }

    private void obstacleCollisionDetect() {
        if (!isPlaying) return;
        for (Rectangle obs: obstacles) {
            if (ball.rectIntersect(obs)) {
                reset();
                return;
            }
        }
    }

    private void targetCollisionDetect() {
        if (!isPlaying) return;
        for (Circle target: targets) {
            if (!ball.circleIntersect(target)) continue;

            if (ball.isIntersectToCircleH(target)) {
                ballAccX = -ballAccX;
            } else {
                ballAccY = -ballAccY;
            }

            addScore(target);
            flingBall();

            return;
        }
    }

    private void addScore(Circle target) {
        ball.addScore(target.getScore());

        if (gamePlayDelegate != null) gamePlayDelegate.scoreUpdate(ball.getScore());
    }

    private void reset() {
        ballAccX = 0;
        ballAccY = 0;
        ball.setX(BALL_ORIGINAL[0]);
        ball.setY(BALL_ORIGINAL[1]);
        ball.setScore(0);

        isPlaying = false;

        if (gamePlayDelegate != null) gamePlayDelegate.gameEnd();
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

    @Override
    public void requestReset() {
        if (isPlaying) reset();
    }

    private class FlingGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (isPlaying) return true;

            double touchX = e1.getX(0);
            double touchY = e1.getY(0);
            if (ball.distance(touchX, touchY) > 20) return true;

            ballAccX = velocityX;
            ballAccY = velocityY;
            isPlaying = true;

            return false;
        }
    }
}
