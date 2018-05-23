package com.luhuan.rxsimple

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.luhuan.rxsimple.utils.RxFus
import kotlinx.android.synthetic.main.activity_fus.*

class FusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fus)
        to_post.setOnClickListener{
            RxFus.getSingle().post("你好")
            finish()
        }
    }

}
