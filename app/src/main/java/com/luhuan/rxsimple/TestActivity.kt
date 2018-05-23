package com.luhuan.rxsimple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.luhuan.rxsimple.dagger_computer.MacComputer
import com.luhuan.rxsimple.dagger_computer.WinComputer

import javax.inject.Inject


class TestActivity : AppCompatActivity() {

    @Inject
    lateinit var userName: UserName

    @Inject
    lateinit var appleBean: AppleBean

    @Inject
    @field:WinComputer
    lateinit var winBean: ComputerBean

    @Inject
    @field:MacComputer
    lateinit var macBean: ComputerBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        DaggerTestComponent.create().inject(this)
        Log.d("TAG", winBean.type)
        Log.d("TAG", macBean.type)
    }

}
