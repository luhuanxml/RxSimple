package com.luhuan.rxsimple.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luhuan.rxsimple.design.CircleTransformation;

/**
 * 图片加载工具类
 */
public class GlideApp {

    private static Context mContext;

    public static void init(Context context){
        mContext=context;
    }

    /**
     * 加载本地资源
     * @param resId 图片资源id,
     */
    @SuppressLint("CheckResult")
    public static RequestBuilder loadCircle(int resId){
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.transform(new CircleTransformation());
        return Glide.with(mContext).load(resId).apply(requestOptions);
    }

    /**
     * 加载本地资源
     * @param path 图片资源id,
     */
    @SuppressLint("CheckResult")
    public static RequestBuilder loadCircle(String path){
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.circleCrop();
        return Glide.with(mContext).load(path).apply(requestOptions);
    }
}
