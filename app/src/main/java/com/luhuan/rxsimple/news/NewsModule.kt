package com.luhuan.rxsimple.news

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class NewsModule(val view: NewsContact.View){

    private val presenter=PNews(view)

    @Provides
    fun providesPNews():PNews{
        return presenter
    }

    @Provides
    fun providesCompositeDisposable():CompositeDisposable{
        return CompositeDisposable()
    }
}
