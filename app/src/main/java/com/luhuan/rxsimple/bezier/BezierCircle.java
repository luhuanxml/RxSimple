package com.luhuan.rxsimple.bezier;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class BezierCircle extends View implements View.OnClickListener {

    private Paint mPaint;

    //两个圆  中心点  半径
    private float mCircleOneX;
    private float mCircleOneY;

    private float mCircleTwoX;
    private float mCircleTwoY;

    private float mCircleOneRadius;
    private float mCircleTwoRadius;

    private float mNormalRadius;

    //两个圆的动画管理
    private ObjectAnimator mOneAnimator;
    private ObjectAnimator mTwoAnimator;

    private AnimatorSet animatorSet;

    private Path mPath;

    public BezierCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setAnimator();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPath = new Path();
        mCircleOneX = 200;
        mCircleOneY = 200;
        mCircleTwoX = 700;
        mCircleTwoY = 900;
        mNormalRadius = 200;
        mCircleOneRadius = mNormalRadius;
        mCircleTwoRadius = mNormalRadius;
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void setAnimator() {
        mOneAnimator = ObjectAnimator.ofFloat(this, "circleOneX", mCircleTwoX, mCircleOneY);
        mOneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });
        mTwoAnimator = ObjectAnimator.ofFloat(this, "circleOneY", mCircleTwoY, mCircleOneY);
        mTwoAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

        animatorSet = new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(mOneAnimator, mTwoAnimator);

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //根据圆心半径绘制圆
        canvas.drawCircle(mCircleOneX, mCircleOneY, mCircleOneRadius, mPaint);
        canvas.drawCircle(mCircleTwoX, mCircleTwoY, mCircleTwoRadius, mPaint);

        //meatball算法
        metaball(canvas);
    }

    //meatball算法 拖动粘粘
    private void metaball(Canvas canvas) {
        float x = mCircleTwoX;
        float y = mCircleTwoY;
        float startX = mCircleOneX;
        float startY = mCircleOneY;

        //两个圆之间的中心点作为控制点
        float controlX = (startX + x) / 2;
        float controlY = (startY + y) / 2;

        //算出两个圆之间的中心点到两个圆圆心的直线距离，两个距离是相等的，只需要计算一个
        float distanceX = controlX - startX;
        float distanceY = controlY - startY;
        //使用勾股定理得出斜边长
        float distance = (float) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        //使用cos函数得出中心线和切线垂直线的夹角
        double a = Math.acos(mNormalRadius / distance);

        //中心线与切线垂直线的夹角
        double b = Math.acos(distanceX / distance);
        //通过夹角(a-b)和圆半径的到切线点1到圆心的x和y方向距离
        float offsetX1 = (float) (mNormalRadius * Math.cos(a - b));
        float offsetY1 = (float) (mNormalRadius * Math.sin(a - b));
        //得到切点1坐标 tangency(切点)的缩写
        float tanX1 = startX + offsetX1;
        float tanY1 = startY - offsetY1;

        double c = Math.acos(distanceY / distance);
        //通过夹角(a-c)和圆心半径的到切点2到圆心的x和y方向的距离
        float offsetX2 = (float) (mNormalRadius * Math.sin(a - c));
        float offsetY2 = (float) (mNormalRadius * Math.cos(a - c));
        float tanX2 = startX - offsetX2;
        float tanY2 = startY + offsetY2;

        double d = Math.acos((y-controlY)/ distance);
        float offsetX3 = (float) (mNormalRadius * Math.sin(a - d));
        float offsetY3 = (float) (mNormalRadius * Math.cos(a - d));
        float tanX3 = x + offsetX3;
        float tanY3 = y - offsetY3;

        double e = Math.acos((x-controlX) / distance);
        float offsetX4 = (float) (mNormalRadius * Math.cos(a - e));
        float offsetY4 = (float) (mNormalRadius * Math.sin(a - e));
        float tanX4 = x - offsetX4;
        float tanY4 = y + offsetY4;

        mPath.reset();

        mPath.moveTo(tanX1, tanY1);
        mPath.quadTo(controlX, controlY, tanX3, tanY3);
        mPath.lineTo(tanX4, tanY4);
        mPath.quadTo(controlX, controlY, tanX2, tanY2);
        canvas.drawPath(mPath, mPaint);

        // 辅助线
        canvas.drawCircle(tanX1, tanY1, 5, mPaint);
        canvas.drawCircle(tanX2, tanY2, 5, mPaint);
        canvas.drawCircle(tanX3, tanY3, 5, mPaint);
        canvas.drawCircle(tanX4, tanY4, 5, mPaint);
        canvas.drawLine(mCircleOneX, mCircleOneY, mCircleTwoX, mCircleTwoY, mPaint);
        canvas.drawLine(0, mCircleOneY, mCircleOneX + mNormalRadius + 400, mCircleOneY, mPaint);
        canvas.drawLine(mCircleOneX, 0, mCircleOneX, mCircleOneY + mNormalRadius + 50, mPaint);
        canvas.drawLine(mCircleTwoX, mCircleTwoY, mCircleTwoX, 0, mPaint);
        canvas.drawCircle(controlX, controlY, 5, mPaint);
        canvas.drawLine(startX, startY, tanX1, tanY1, mPaint);
        canvas.drawLine(tanX1, tanY1, controlX, controlY, mPaint);
    }

    @Override
    public void onClick(View view) {
        animatorSet.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mCircleOneX=event.getX();
                mCircleOneY=event.getY();
                invalidate();
        }
        return true;
    }
}
