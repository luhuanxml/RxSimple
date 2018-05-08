package com.luhuan.rxsimple;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {


    @GET
    Observable<String> method();
}
