package com.luhuan.rxlibrary;

import android.view.View;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapter;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jakewharton.rxbinding2.widget.RxPopupMenu;
import com.jakewharton.rxbinding2.widget.RxProgressBar;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class RxBinding {

    private static final int DEFAULT_TIME = 1;

    //普通点击时间
    public static Observable<Object> click(View view) {
        return RxView.clicks(view).throttleFirst(DEFAULT_TIME, TimeUnit.SECONDS);
    }

    //长按点击时间
    public static Observable<Object> longClick(View view) {
        return RxView.longClicks(view).throttleFirst(DEFAULT_TIME, TimeUnit.SECONDS);
    }

    public static Observable<String> textChanges(TextView textView) {
        return RxTextView.textChanges(textView).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Exception {
                return charSequence.toString();
            }
        });
    }

    public static InitialValueObservable<Boolean> checked(CompoundButton compoundButton) {
        return RxCompoundButton.checkedChanges(compoundButton);
    }

}
