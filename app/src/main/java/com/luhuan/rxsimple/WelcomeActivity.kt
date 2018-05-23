package com.luhuan.rxsimple

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.luhuan.rxsimple.bezier.BezierActivity
import com.luhuan.rxsimple.design.ScrollingActivity
import com.luhuan.rxsimple.drag.DragActivity
import com.luhuan.rxsimple.drawer.DrawerActivity
import com.luhuan.rxsimple.news.NewsActivity
import com.luhuan.rxsimple.utils.RxFus
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    val TAG = WelcomeActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        to_drag.setOnClickListener {
            openActivity(DragActivity::class.java)
        }
        to_drawer.setOnClickListener {
            openActivity(DrawerActivity::class.java)
        }
        to_use_dagger.setOnClickListener {
            openActivity(NewsActivity::class.java)
        }
        to_scrolling.setOnClickListener {
            openActivity(ScrollingActivity::class.java)
        }
        to_bezier.setOnClickListener {
            openActivity(BezierActivity::class.java)
        }
        to_use_rxfus.setOnClickListener {
            openActivity(FusActivity::class.java)
        }
    }

    var i = 0

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        RxFus.getSingle().except(String::class.java)
                .subscribe({
                    Log.d("RXFus", it)
                    //disposable.dispose()
                })
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    //规定class是AppCompatActivity.class 的子类 使用到out关键字
    private fun openActivity(clazz: Class<out AppCompatActivity>) {
        startActivity(Intent(this, clazz))
    }

}