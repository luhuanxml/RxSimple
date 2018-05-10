package com.luhuan.rxsimple;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.luhuan.rxsimple.dagger_computer.MacComputer;
import com.luhuan.rxsimple.dagger_computer.WinComputer;

import javax.inject.Inject;


public class TestActivity extends AppCompatActivity {

    @Inject
    UserName userName;

    @Inject
    AppleBean appleBean;

    @Inject
    @WinComputer
    ComputerBean winBean;

    @Inject
    @MacComputer
    ComputerBean macBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        DaggerTestComponent.builder().build().inject(this);
        Log.d("TAG", winBean.getType());
        Log.d("TAG", macBean.getType());
    }

}
