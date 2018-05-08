package com.luhuan.rxlibrary;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程切换
 */
public class ThreadTransform {

    /**
     * 只用于IO-UI线程的切换，不做数据更改处理
     * @param <T> 任意对象
     * @return 任意对象
     */
    public  static <T> ObservableTransformer<T,T> replayIM(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 只用于new_thread-UI线程的切换，不做数据更改处理
     * @param <T> 任意对象
     * @return 任意对象
     */
    public  static <T> ObservableTransformer<T,T> replayNM(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
