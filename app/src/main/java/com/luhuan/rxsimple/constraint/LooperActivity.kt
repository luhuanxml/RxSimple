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
        val constraintAdapter=ConstraintAdapter()
        recycler.apply {
            layoutManager=LinearLayoutManager(context,LinearLayout.VERTICAL,false)
            adapter=constraintAdapter
            addItemDecoration(CustomItemDecoration())
        }

    }
}
