package com.luhuan.rxsimple.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxCompoundButton
import com.jakewharton.rxbinding2.widget.RxTextView
import com.luhuan.rxsimple.R
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import java.io.Serializable
import java.util.concurrent.TimeUnit

/**
 * 防抖动
 */

//普通点击
fun View.click(): Observable<Any> {
    return RxView.clicks(View@ this).throttleFirst(1, TimeUnit.SECONDS)
}

//长按点击
fun View.longClick(): Observable<Any> {
    return RxView.longClicks(View@ this).throttleFirst(1, TimeUnit.SECONDS)
}

//输入文本监听
fun TextView.textChange(): Observable<Any> {
    return RxTextView.textChanges(TextView@ this).map { it.toString() }
}

fun CompoundButton.check(): Observable<Boolean> {
    return RxCompoundButton.checkedChanges(CompoundButton@ this)
}

fun ImageView.loadImage(path: String) {
    Glide.with(ImageView@ this.context).apply { RequestOptions().headIcon() }.load(path).into(this)
}

private fun RequestOptions.headIcon() =
        RequestOptions@ this.circleCrop().placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)


fun Context.toast(msg: String) = Toast.makeText(Context@ this, msg, Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes msg: Int) = Toast.makeText(Context@ this, msg, Toast.LENGTH_SHORT).show()

fun Any.log(msg: String) = Log.d(Any@ this as? String ?: Any@ this.javaClass.simpleName, msg)

val Int.dp: Int get() = (Int@ this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int get() = (Int@ this * Resources.getSystem().displayMetrics.density).toInt()

//Adapter item初始化引用布局方法扩展
fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(View@ this.context).inflate(layoutRes, View@ this, false)

inline fun <reified T : View> View.find(@IdRes id: Int) = findViewById<T>(id)

fun <T> scheduler() = ObservableTransformer<T, T> {
    it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

val RXJAVA: RxJava2CallAdapterFactory get() = RxJava2CallAdapterFactory.create()

//Activity跳转 Intent传值
inline fun <reified T : Activity>
        Context.open(vararg params: Pair<String, Any>) {
    Anko.startActivity(Context@ this, T::class.java, params)
}

inline fun <reified T : Activity>
        Activity.openForResult(vararg params: Pair<String, Any>, requestCode: Int) {
    Anko.startActivityForResult(Activity@ this, T::class.java, requestCode, params)
}

fun File.getPart(name: String) =
        MultipartBody.Part.createFormData(name, File@ this.name,
                RequestBody.create(MultipartBody.FORM, File@ this))

fun Activity.getWidth() = Point().outSize(Activity@ this).x

fun Activity.getHeight() = Point().outSize(Activity@ this).y

private fun Point.outSize(activity: Activity): Point {
    activity.windowManager.defaultDisplay.getSize(Point@ this)
    return this
}

object Anko {

    fun startActivity(context: Context, clazz: Class<out Activity>,
                      params: Array<out Pair<String, Any>>) {
        val intent = getIntent(context, clazz, params)
        context.startActivity(intent)
    }

    fun startActivityForResult(context: Activity, clazz: Class<out Activity>, requestCode: Int,
                               params: Array<out Pair<String, Any>>) {
        val intent = getIntent(context, clazz, params)
        context.startActivityForResult(intent, requestCode)
        context.startActivity(intent)
    }

    private fun getIntent(context: Context, clazz: Class<out Activity>,
                          params: Array<out Pair<String, Any>>): Intent {
        val intent = Intent(context, clazz)
        if (params.isNotEmpty()) {
            params.forEach {
                "THREAD".log(Thread.currentThread().name)//检查当前线程  D/THREAD: main
                val value = it.second
                when (value) {
                    is Float -> intent.putExtra(it.first, value)
                    is Double -> intent.putExtra(it.first, value)
                    is Short -> intent.putExtra(it.first, value)
                    is Int -> intent.putExtra(it.first, value)
                    is Long -> intent.putExtra(it.first, value)
                    is Boolean -> intent.putExtra(it.first, value)
                    is String -> intent.putExtra(it.first, value)
                    is Serializable -> intent.putExtra(it.first, value)
                }
            }
        }
        return intent
    }
}
