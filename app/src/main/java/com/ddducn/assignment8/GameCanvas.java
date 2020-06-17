package com.ddducn.assignment8;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class GameCanvas extends View implements GameActivityDelegate {
    private Paint paint = new Paint(); // paint

    private Circle ball; // ball
    private Circle[] targets = new Circle[6]; // targets
    private Rectangle[] obstacles = new Rectangle[6]; // obstacles
    private GestureDetector gestureDetector; // gesture detector

    private double ballAccX = 0; // ball accelerated x
    private double ballAccY = 0; // ball accelerated y

    private double canvasW = 0; // canvas width
    private double canvasH = 0; // canvas height

    private int hObsMoveFlag = 1; // horizontal movable obstacle flag
    private int vObsMoveFlag = 1; // vertical movable obstacle flag

    private final double[] BALL_ORIGINAL = {0.69, 0.86}; // original ball coordinate

    private boolean isPlaying = false; // is playing

    public GamePlayDelegate gamePlayDelegate; // delegate

    public GameCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        gestureDetector = new GestureDetector(context, new FlingGestureListener());
        ((GameActivity) context).gameActivityDelegate = this;
    }

    /**
     * create objects
     */
    private void setupObjects() {
        // create ball
        ball = new Circle(BALL_ORIGINAL[0] * canvasW, BALL_ORIGINAL[1] * canvasH, 0.05 * canvasW, 0);
        ball.setColor(getResources().getColor(R.color.colorAccent));

        // create targets
        double[][] targetsCoors = {{0.51, 0.11}, {0.65, 0.23}, {0.14, 0.46}, {0.32, 0.29}, {0.32, 0.8}, {0.7, 0.46}};
        int[] scores = {1, 1, 5, 2, 3, 1};
        for (int i = 0; i < targets.length; i++) {
            targets[i] = new Circle(targetsCoors[i][0] * canvasW, targetsCoors[i][1] * canvasH, 0.05 * canvasW, scores[i]);
            targets[i].setColor(getResources().getColor(R.color.colorPrimary));
        }

        // create obstacles
        double[][] obstaclesCoors = {{0.09, 0.34}, {0.14, 0.06}, {0.6, 0.54}, {0.28, 0.92}, {0.09, 0.8}, {0.05, 0.74}};
        for (int i = 0; i < obstacles.length; i++) {
            obstacles[i] = new Rectangle(obstaclesCoors[i][0] * canvasW, obstaclesCoors[i][1] * canvasH, 0.14 * canvasW, 0.014 * canvasH);
            obstacles[i].setColor(getResources().getColor(R.color.black));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        moveBall();
        moveObstacles();
        obstacleCollisionDetect();
        targetCollisionDetect();

        drawObjects(canvas);

        invalidate();
    }

    /**
     * draw objects
     * @param canvas canvas to draw
     */
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

    /**
     * move the ball
     */
    private void moveBall() {
        if (!isPlaying) return;

        // is current ball hit to h or v of screen
        if (isToHEdge()) ballAccX = -ballAccX;
        if (isToVEdge()) ballAccY = -ballAccY;

        // const time value, t * t / 2
        double STEP_TIMES = 0.04 * 0.04 * 0.5;
        double dX = ballAccX * STEP_TIMES;
        double dY = ballAccY * STEP_TIMES;

        // move
        ball.moveX(dX);
        ball.moveY(dY);
    }

    /**
     * if ball hits to h screen
     */
    private boolean isToHEdge() {
        return ball.getX() <= ball.getR() ||
                ball.getX() >= canvasW - ball.getR();
    }

    /**
     * if ball hits to v screen
     */
    private boolean isToVEdge() {
        return ball.getY() <= ball.getR() ||
                ball.getY() >= canvasH - ball.getR();
    }

    /**
     * move obstacles
     */
    private void moveObstacles() {
        double OBS_MOVE_STEP = 10;

        // horizontal movable obs
        Rectangle hObs = obstacles[5];
        if (hObs.getX() <= (0.32 * canvasW)) hObsMoveFlag = 1;
        if (hObs.getX() >= (0.65 * canvasW)) hObsMoveFlag = -1;
        hObs.moveX(OBS_MOVE_STEP * hObsMoveFlag);

        // vertical movable obs
        Rectangle vObs = obstacles[4];
        if (vObs.getY() <= (0.52 * canvasH)) vObsMoveFlag = 1;
        if (vObs.getY() >= (0.69 * canvasH)) vObsMoveFlag = -1;
        vObs.moveY(OBS_MOVE_STEP * vObsMoveFlag);
    }

    /**
     * obstacle collision detect
     */
    private void obstacleCollisionDetect() {
        if (!isPlaying) return;
        for (Rectangle obs: obstacles) {
            // reset the game if ball hit to a obstacle
            if (ball.rectIntersect(obs)) {
                reset();
                return;
            }
        }
    }

    /**
     * target collision detect
     */
    private void targetCollisionDetect() {
        if (!isPlaying) return;
        for (Circle target: targets) {
            if (!ball.circleIntersect(target)) continue;

            // if ball hits the target horizontally
            if (ball.isIntersectToCircleH(target)) {
                ballAccX = -ballAccX;
            } else { // vertically
                ballAccY = -ballAccY;
            }

            // append scorer and move ball
            addScore(target);
            moveBall();

            return;
        }
    }

    /**
     * append score
     * @param target score from target
     */
    private void addScore(Circle target) {
        ball.addScore(target.getScore());

        // notify delegate with latest score
        if (gamePlayDelegate != null) gamePlayDelegate.scoreUpdate(ball.getScore());
    }

    /**
     * reset the game
     */
    private void reset() {
        // reset the ball to originally positions and make it static
        ballAccX = 0;
        ballAccY = 0;
        ball.setX(BALL_ORIGINAL[0] * canvasW);
        ball.setY(BALL_ORIGINAL[1] * canvasH);
        ball.setScore(0);

        // update game status
        isPlaying = false;

        // notify delegate game is ended
        if (gamePlayDelegate != null) gamePlayDelegate.gameEnd();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // new w and h
        canvasW = w;
        canvasH = h;

        setupObjects();
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

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    private class FlingGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // stop fling if the game is ongoing and the touch point is far from the ball
            if (isPlaying || ball.distance(e1.getX(0), e1.getY(0)) > 100) return true;

            // setup accelerated values and start the game
            ballAccX = velocityX;
            ballAccY = velocityY;
            isPlaying = true;

            return false;
        }
    }
}
