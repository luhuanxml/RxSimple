package com.luhuan.rxsimple.news

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.luhuan.rxsimple.R
import javax.inject.Inject

class NewsActivity : AppCompatActivity(), NewsContact.View ,WordContant.View{

    @Inject
    lateinit var presenterNews: PNews

    @Inject
    lateinit var presenterWord: PWord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        DaggerNewsComponent.builder().newsModule(NewsModule(this)).wordModule(WordModule(this))
                .build().inject(this)
    }

    override fun onStart() {
        super.onStart()
        presenterNews.present()
        presenterWord.present()

    }

    override fun showResult(result: String) {
        Log.d("TAG","news--->"+result)
    }

    override fun onFailure(throwable: Throwable) {
        Log.d("TAG",throwable.message)
    }

    override fun showWordResult(result: String) {
        Log.d("TAG","word---->"+result)
    }

}
