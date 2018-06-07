package com.luhuan.rxsimple.design;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.luhuan.rxsimple.R;
import com.luhuan.rxsimple.utils.GlideApp;
import com.luhuan.rxsimple.utils.ToolKt;

public class DesignActivity extends AppCompatActivity {

    private CircleView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        imageView = findViewById(R.id.squareView);
        GlideApp.loadCircle(R.drawable.aaa).into(imageView);
    }
}
