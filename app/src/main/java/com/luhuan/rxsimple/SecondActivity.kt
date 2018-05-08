package com.luhuan.rxsimple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.luhuan.rxlibrary.EventBean
import com.luhuan.rxlibrary.RxBus
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        second_btn.setOnClickListener {
            RxBus.getInstance().post(EventBean<String>("我是被发射出去的消息",EventBean.NORMAL))
            finish()
        }
    }
}
