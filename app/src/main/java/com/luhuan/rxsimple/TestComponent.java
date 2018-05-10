package com.luhuan.rxsimple;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestModule.class)
public interface TestComponent {

    void inject(TestActivity testActivity);

}
