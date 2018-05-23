package com.luhuan.rxsimple.utils;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

public class RxFus {

    private final FlowableProcessor<Object> mBus;

    public static RxFus getSingle(){
        return Holder.FUS;
    }

    private Subscription mSubscription;

    private RxFus() {
        mBus= PublishProcessor.create().toSerialized();
    }

    public void post(Object object){
        mBus.onNext(object);
    }

    /**
     *该方法在reStart中注册的情况，第一遍回来不会走执行，因为RxBus需要先注册接收
     * 所以放在onStart中，但是放在onStart 中，当手机在当前页面锁定或者屏幕熄灭后回到当前页面，RxBus接收器会注册两个。
     * 这时候跳转返回，数据会接收两次。所以在onStart中订阅前，如果 Subscription 没有解除之前的订阅就先解除之前的订阅
     * 防止出现重复订阅导致接收两次的情况
     * 这里使用了 doOnSubscribe  doAfterNext 操作符，将 Subscription 放在RxBus中不暴露给其他类。
     * 更单纯的调用RxBus自己的方法，将RxJava中间处理隐藏起来
     */
    public <T> Flowable<T> except(Class<T> typeClazz){
        //重点在这里
        if (mSubscription!=null) mSubscription.cancel();
        return mBus.ofType(typeClazz).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription ->mSubscription=subscription)
                .doAfterNext(t -> mSubscription.cancel());
    }

    public void unRegister(){
        if (mSubscription!=null)
        mSubscription.cancel();
    }

    private static class Holder{
        private static final RxFus FUS =new RxFus();
    }
}
