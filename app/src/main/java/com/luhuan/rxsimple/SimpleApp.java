package com.luhuan.rxsimple;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.utils.RxPickerImageLoader;

public class SimpleApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RxPicker.init(new RxPickerImageLoader() {
            @Override
            public void display(ImageView imageView, String path, int width, int height) {
                RequestOptions requestOptions=new RequestOptions()
                        .centerCrop().error(R.mipmap.ic_launcher)
                        .priority(Priority.LOW).override(width,height)
                        .placeholder(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                Glide.with(getApplicationContext()).load(path).apply(requestOptions).into(imageView);
            }
        });
    }
}
