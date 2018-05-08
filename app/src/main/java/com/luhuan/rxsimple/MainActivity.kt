package com.luhuan.rxsimple

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.luhuan.rxlibrary.ApiStore
import com.luhuan.rxlibrary.EventBean
import com.luhuan.rxlibrary.RetrofitProvider
import com.luhuan.rxlibrary.RxBus
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var disposable:Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_btn.setOnClickListener {
            val intent=Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        disposable=RxBus.getInstance().toObservable(EventBean::class.java,{
            if (it.tag==EventBean.NORMAL){
                Log.d(it.tag,it.event.toString())
                main_btn.text=it.event.toString()
                disposable!!.dispose()
            }
        })
        RetrofitProvider.getInstance().create(ApiStore::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable!=null&&!disposable!!.isDisposed)
        RxBus.getInstance().unregister(disposable)
    }

}
