package com.luhuan.rxsimple

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [(TestModule::class)])
interface TestComponent {

    fun inject(testActivity: TestActivity)

}
