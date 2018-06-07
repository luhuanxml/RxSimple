package com.luhuan.rxsimple.design;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class CircleTransformation extends BitmapTransformation {

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Canvas canvas = new Canvas(toTransform);
        BitmapShader bitmapShader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        int min = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int radius = toTransform.getWidth() / 2;
        RadialGradient radialGradient = new RadialGradient(toTransform.getWidth() / 2 ,
                toTransform.getWidth() / 2, radius, Color.TRANSPARENT, Color.WHITE, Shader.TileMode
                .CLAMP);
        ComposeShader composeShader = new ComposeShader(bitmapShader, radialGradient, PorterDuff
                .Mode.SRC_OVER);
        Paint paint = new Paint();
        paint.setShader(composeShader);
        canvas.drawRect(0, 0, toTransform.getWidth(), toTransform.getWidth(), paint);
        return toTransform;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
