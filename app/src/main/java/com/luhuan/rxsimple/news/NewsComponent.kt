package com.luhuan.rxsimple.news

import dagger.Component

@Component(modules = [NewsModule::class,WordModule::class])
interface NewsComponent{

    fun inject(newsActivity: NewsActivity)
}
