package com.luhuan.rxsimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.luhuan.rxsimple.bezier.BezierActivity
import com.luhuan.rxsimple.design.DesignActivity
import com.luhuan.rxsimple.design.ScrollingActivity
import com.luhuan.rxsimple.drag.DragActivity
import com.luhuan.rxsimple.drawer.DrawerActivity
import com.luhuan.rxsimple.news.NewsActivity
import com.luhuan.rxsimple.utils.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Observable.just(11).compose(scheduler())
        to_drag.click().subscribe{
            open<DragActivity>("aaa" to "bbb","ccc" to "ddd","eee" to 333)
        }
        to_drawer.setOnClickListener {
            open<DrawerActivity>()
        }
        to_use_dagger.setOnClickListener {
            open<NewsActivity>()
        }
        to_scrolling.setOnClickListener {
            open<ScrollingActivity>()
        }
        to_bezier.setOnClickListener {
            open<BezierActivity>()
        }
        to_use_rxfus.setOnClickListener {
            open<FusActivity>()
        }
        to_design.setOnClickListener{
            open<DesignActivity>()
        }
    }

    var i = 0

    override fun onStart() {
        super.onStart()
        log("onStart")
        // 接收相同Class,使用type判断对其中的内容进行处理
        RxFus.single.except().subscribe({
            if (it.type == 5) {
                log(it.obj.toString())
            } else if (it.type == 6) {
                log(it.obj.toString())
            }
        })
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log( "onDestroy")
        RxFus.single.unRegister()
    }
}