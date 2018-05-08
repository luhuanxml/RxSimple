package com.luhuan.rxlibrary;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitProvider<T> {

    private static RetrofitProvider instance;
    private final int TIME_OUT = 20;
    private final String TAG="OK_HTTP_BODY";
    private final HttpLoggingInterceptor loggingInterceptor
            = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            Log.d(TAG,"--->>>"+message);
        }
    }).setLevel(HttpLoggingInterceptor.Level.BODY);
    private final OkHttpClient okhttpClient = new OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build();
    private String DOMAIN = "/";

    protected RetrofitProvider() {

    }

    protected RetrofitProvider(String domain) {
        DOMAIN = domain;
    }

    public static RetrofitProvider getInstance() {
        if (instance == null) {
            synchronized (RetrofitProvider.class) {
                if (instance == null) {
                    instance = new RetrofitProvider();
                }
            }
        }
        return instance;
    }

    public static RetrofitProvider getInstance(String domain) {
        if (instance == null) {
            synchronized (RetrofitProvider.class) {
                if (instance == null) {
                    instance = new RetrofitProvider(domain);
                }
            }
        }
        return instance;
    }

    public T create(Class<T> clazz) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(DOMAIN);
        builder.client(okhttpClient);
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.addConverterFactory(ScalarsConverterFactory.create());
        return builder.build().create(clazz);
    }
}
