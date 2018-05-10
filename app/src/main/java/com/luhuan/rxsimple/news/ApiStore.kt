package com.luhuan.rxsimple.news

import java.util.LinkedHashMap

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiStore {

    @GET("toutiao/index")
    fun getNews(@QueryMap params: LinkedHashMap<String, String>): Observable<String>

    @GET("chengyu/query")
    fun queryWord(@QueryMap params: LinkedHashMap<String, String>): Observable<String>

}
