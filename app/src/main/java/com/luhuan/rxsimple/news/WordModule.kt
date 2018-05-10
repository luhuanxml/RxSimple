package com.luhuan.rxsimple.news

import dagger.Module
import dagger.Provides

@Module
class WordModule(val view: WordContant.View){

    private val presenter=PWord(view)

    @Provides
    fun providesPWord():PWord{
        return presenter
    }
}
