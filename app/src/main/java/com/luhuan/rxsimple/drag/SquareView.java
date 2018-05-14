package com.luhuan.rxsimple.drag;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class SquareView extends ImageView {
    public SquareView(Context context) {
        super(context);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        //这里是根据高度来固定正方形的边长
//        setMeasuredDimension(getMeasuredHeight(), getMeasuredHeight());
        //下面是根据宽度来固定正方形的边长
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
