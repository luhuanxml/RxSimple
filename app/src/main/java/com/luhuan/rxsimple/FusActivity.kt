package com.luhuan.rxsimple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.luhuan.rxsimple.utils.BusBean
import com.luhuan.rxsimple.utils.RxFus
import kotlinx.android.synthetic.main.activity_fus.*

class FusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fus)
        to_post1.setOnClickListener{
            val s="你好"
            RxFus.single.post(5,s)
            finish()
        }
        to_post2.setOnClickListener{
            RxFus.single.post(6,1)
            finish()
        }
    }

}