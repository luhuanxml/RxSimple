package com.luhuan.rxsimple.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.luhuan.rxsimple.R
import com.luhuan.rxsimple.constraint.LooperActivity
import com.luhuan.rxsimple.drawer.DrawerActivity
import com.luhuan.rxsimple.photo.PhotoActivity
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_news.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewsActivity : RxAppCompatActivity(), NewsContact.View, WordContant.View {

    @Inject
    lateinit var presenterNews: PNews

    @Inject
    lateinit var presenterWord: PWord

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    private lateinit var newsActivity: NewsActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        newsActivity = this
        DaggerNewsComponent.builder().newsModule(NewsModule(this)).wordModule(WordModule(this))
                .build().inject(this)

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .compose(bindUntilEvent(ActivityEvent.PAUSE))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                })

        item_title.setOnClickListener {
            startActivity(Intent(this,LooperActivity::class.java))
        }

        imageView.setOnClickListener{
            startActivity(Intent(this,DrawerActivity::class.java))
        }

        content.setOnClickListener {
            startActivity(Intent(this,PhotoActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
         compositeDisposable.addAll(presenterNews.present(), presenterWord.present())

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG","onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG","onStop")
    }

    override fun showResult(result: String) {
        Log.d("TAG", "news--->" + result)
    }

    override fun onFailure(throwable: Throwable) {
        Log.d("TAG", throwable.message)
    }

    override fun showWordResult(result: String) {
        Log.d("TAG", "word---->" + result)
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG","onPause")
        compositeDisposable.clear()
    }

}
