package com.luhuan.rxsimple


import com.luhuan.rxsimple.dagger_computer.MacComputer
import com.luhuan.rxsimple.dagger_computer.WinComputer

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class TestModule {

    @Provides
    @Singleton
    fun providesUserName(): UserName {
        return UserName()
    }

    @Provides
    fun providesAppleBean(): AppleBean {
        return AppleBean()
    }

    @Provides
    @WinComputer
    fun providesWinBean(): ComputerBean {
        return ComputerBean("windows电脑")
    }

    @Provides
    @MacComputer
    fun providesMacBean(): ComputerBean {
        return ComputerBean("mac电脑")
    }

}
