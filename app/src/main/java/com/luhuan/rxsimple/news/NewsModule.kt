package com.luhuan.rxsimple.news

import dagger.Module
import dagger.Provides

@Module
class NewsModule(val view: NewsContact.View){

    private val presenter=PNews(view)

    @Provides
    fun providesPNews():PNews{
        return presenter
    }
}
