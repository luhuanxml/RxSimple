package com.luhuan.rxsimple.bezier;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.luhuan.rxsimple.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.observers.EmptyCompletableObserver;
import io.reactivex.internal.operators.observable.ObservableJust;
import io.reactivex.schedulers.Schedulers;

public class BezierActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        List<Integer> mlist = new ArrayList<>();
        mlist.add(1);
        mlist.add(2);
        mlist.add(3);
        mlist.add(4);
        mlist.add(5);
        mlist.add(6);

        Observable.just(1, 1, 2, 1, 1).distinctUntilChanged()
                .subscribe(integer -> log("distinct", integer.toString()));
//        Observable.fromIterable(mlist).startWith(mlist).compose(replayIM())
//                .subscribe(integer -> Log.d("startWith", "onCreate: " + integer));
//
//        Observable.fromIterable(mlist).concatWith(Observable.just(7)).compose(replayIM())
//                .subscribe(integer -> Log.d("concatWith", "onCreate: " + integer));
//
//        Observable.empty().compose(replayIM())
//                .subscribe(o -> log("empty", "onCreate"),
//                        throwable -> log("empty", "throw"),
//                        () -> log("empty", "onComplete"));
//
//        Observable.never().compose(replayIM())
//                .subscribe(o -> log("never", "onCreate"),
//                        throwable -> log("never", "throw"),
//                        () -> log("never", "onComplete"));
//        Observable.error(new Throwable("error"))
//                .subscribe(o -> log("error", "onCreate"),
//                        throwable -> log("error", "throw"),
//                        () -> log("error", "onComplete"));
//
//        Observable.interval(3, 3, TimeUnit.SECONDS, Schedulers.io());
//
//        Observable.range(3, 5).compose(replayIM()).subscribe(integer -> log("range", integer.toString()));
//
//        Observable.just(1).repeat(5).compose(replayIM()).subscribe(integer -> log("repeat", integer.toString()));
//
//        Observable.fromIterable(mlist).cast(String.class).compose(replayIM()).subscribe(s -> log("cast", s));
//
//        Observable.just(1, 1, 1, 1, 1).distinct().subscribe(integer -> log("distinct", integer.toString()));
//
//        Observable.fromIterable(mlist).flatMap((Function<Integer, ObservableSource<?>>) integer -> {
//            if (integer == 3) {
//                return Observable.error(new Throwable());
//            } else {
//                return Observable.just(integer);
//            }
//        }).onErrorResumeNext(throwable -> {
//            return Observable.empty();
//        }).compose(replayIM()).subscribe(o -> Log.d("TAG", "accept: " + o.toString())
//                , throwable -> Log.d("TAG", "accept: " + throwable.toString()),
//                () -> Log.d("TAG", "run: "));
//
//        Observable.fromIterable(mlist).skip(3).scan((integer, integer2) -> integer + integer2)
//                .subscribe(integer -> Log.d("TAG", "accept: " + integer.toString() + "####"));
    }

    private void log(String tag, String msg) {
        Log.d(tag, msg);
    }

    private <T> ObservableTransformer<T, T> replayIM() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
