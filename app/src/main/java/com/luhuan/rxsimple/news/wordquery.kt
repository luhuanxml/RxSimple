package com.luhuan.rxsimple.news

import com.luhuan.rxlibrary.RetrofitProvider
import com.luhuan.rxlibrary.ThreadTransform
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface WordContant{

    interface Presenter{
        fun present():Disposable
    }

    interface View{
        fun showWordResult(result:String)
        fun onFailure(throwable: Throwable)
    }

    interface Model{
        fun request():Observable<String>
    }

}

class PWord(val view: WordContant.View):WordContant.Presenter {

    private val model:WordContant.Model=MWord()

    override fun present() :Disposable{
       return model.request().subscribe({view.showWordResult(it)},{view.onFailure(it)})
    }
}

class MWord:WordContant.Model {
    override fun request() :Observable<String>{
       val apiStore= RetrofitProvider.getInstance("http://v.juhe.cn")
               .create(ApiStore::class.java) as ApiStore
        val params=LinkedHashMap<String,String>()
        params["word"] = "亡羊补牢"
        params["key"]="7a27e9d57727b4668c24a1533e0cad13"
        return apiStore.queryWord(params).compose(ThreadTransform.replayIM())
    }
}