package com.luhuan.rxsimple.news

import com.luhuan.rxlibrary.RetrofitProvider
import com.luhuan.rxlibrary.ThreadTransform
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

interface NewsContact {
    interface Presenter {
        fun present():Disposable
    }

    interface View {
        fun showResult(result:String)
        fun onFailure(throwable:Throwable)
    }

    interface Model {
        fun getResult() :Observable<String>
    }
}

class PNews constructor(val view:NewsContact.View): NewsContact.Presenter {

    var model: NewsContact.Model=MNews()

    override fun present() :Disposable{
        return model.getResult().subscribe({view.showResult(it)},{view.onFailure(it)})
    }
}

class MNews : NewsContact.Model {
    override fun getResult() :Observable<String>{
        var apiStore  = RetrofitProvider.getInstance("http://v.juhe.cn").create(ApiStore::class.java) as ApiStore

        val params=LinkedHashMap<String,String>()
        params["key"]="873d6e9b9dd1c6b13632f8cde02000b8"
        return apiStore.getNews(params).compose(ThreadTransform.replayIM())
    }

}
