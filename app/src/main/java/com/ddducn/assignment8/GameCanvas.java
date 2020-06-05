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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw objects
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // create objects
    }
}

