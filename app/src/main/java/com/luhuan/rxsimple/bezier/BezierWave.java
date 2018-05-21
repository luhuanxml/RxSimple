package com.luhuan.rxsimple.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 二阶贝塞尔曲线
 */
public class BezierWave extends View implements View.OnClickListener {

    private Paint mPaint;

    private Path mPath;

    private int waveLength;

    private int mOffset;

    private int mViewWith;
    private int mViewHeight;
    private int mWaveCount;
    private int mCenterY;

    public BezierWave(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        waveLength=w;
        mViewWith = w;
        mViewHeight = h;
        mWaveCount = (int) Math.round(mViewWith / mViewHeight + 1.5);
        mCenterY = (mViewHeight / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
//        if (mCenterY > 0) {
//            mCenterY--;
//        }
        mPath.moveTo(-waveLength + mOffset, mCenterY);
        for (int i = 0; i < mWaveCount; i++) {
            mPath.quadTo(
                    (-waveLength * 3 / 4) + (i * waveLength) + mOffset,
                    mCenterY + 60,
                    (-waveLength / 2) + (i * waveLength) + mOffset,
                    mCenterY);
            mPath.quadTo(
                    (-waveLength / 4) + (i * waveLength) + mOffset,
                    mCenterY - 60,
                    i * waveLength + mOffset,
                    mCenterY);
            //60是起伏高度
        }
        mPath.lineTo(mViewWith, mViewHeight);
        mPath.lineTo(0, mViewHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void onClick(View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, waveLength);
        valueAnimator.setDuration(1000);//波浪速度
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mOffset = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
}
