package com.luhuan.rxsimple.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 二阶贝塞尔曲线
 */
public class BezierCurve extends View {

    private Paint mBezierPaint;
    private Paint mAuxiliaryPaint;
    private Paint mTextPaint;

    //辅助点1
    private float mAuxiliaryX;
    private float mAuxiliaryY;

    //辅助点2
    private float mAuxiliaryX2;
    private float mAuxiliaryY2;

    //起点
    private float mStartPointX;
    private float mStartPointY;

    //终点
    private float mEndPointX;
    private float mEndPointY;

    private Path mPath=new Path();

    public BezierCurve(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //设置抗锯齿
        mBezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBezierPaint.setStyle(Paint.Style.STROKE);
        mBezierPaint.setStrokeWidth(8f);

        mAuxiliaryPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAuxiliaryPaint.setStyle(Paint.Style.STROKE);
        mAuxiliaryPaint.setStrokeWidth(2f);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(20f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartPointX=w/4;
        mStartPointY=h/2-200;

        mEndPointX=w/4*3;
        mEndPointY=h/2 -200;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);//设置起点

        //辅助点
        canvas.drawPoint(mAuxiliaryX,mAuxiliaryY,mAuxiliaryPaint);
        canvas.drawText("控制点1",mAuxiliaryX,mAuxiliaryY,mTextPaint);
        canvas.drawText("控制点2",mAuxiliaryX2,mAuxiliaryY2,mTextPaint);
        canvas.drawText("起始点",mStartPointX,mStartPointY,mTextPaint);
        canvas.drawText("终止点",mEndPointX,mEndPointY,mTextPaint);

        //辅助线
        canvas.drawLine(mStartPointX,mStartPointY,mAuxiliaryX,mAuxiliaryY,mAuxiliaryPaint);
        canvas.drawLine(mAuxiliaryX,mAuxiliaryY,mAuxiliaryX2,mAuxiliaryY2,mAuxiliaryPaint);
        canvas.drawLine(mAuxiliaryX2,mAuxiliaryY2,mEndPointX,mEndPointY,mAuxiliaryPaint);

        //mPath.quadTo(mAuxiliaryX,mAuxiliaryY,mEndPointX,mEndPointY);
        mPath.cubicTo(mAuxiliaryX,mAuxiliaryY,mAuxiliaryX2,mAuxiliaryY2,mEndPointX,mEndPointY);
        canvas.drawPath(mPath,mBezierPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mAuxiliaryX2=event.getX();
                mAuxiliaryY2=event.getY();
                mAuxiliaryX=mAuxiliaryX2-300;
                mAuxiliaryY=mAuxiliaryY2-50;
                invalidate();
        }
        return true;
    }
}
