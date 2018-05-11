package com.luhuan.rxsimple.constraint

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.luhuan.rxsimple.R
import kotlinx.android.synthetic.main.activity_looper.*

class LooperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_looper)
        val adapter=ConstraintAdapter()
        recycler.layoutManager=LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        recycler.adapter=adapter
        recycler.addItemDecoration(CustomItemDecoration())
    }
}
