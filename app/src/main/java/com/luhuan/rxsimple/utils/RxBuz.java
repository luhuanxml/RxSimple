package com.luhuan.rxsimple.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBuz {
    private final Subject<Object> mBus;

    private RxBuz() {
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBuz getSingle(){
        return Holder.BUS;
    }

    public void post(Object object){
        mBus.onNext(object);
    }

    public <T>Observable<T> toObservable(Class<T> tClass){
        return mBus.ofType(tClass);
    }

    private static class Holder {
        private static final RxBuz BUS = new RxBuz();
    }
}
