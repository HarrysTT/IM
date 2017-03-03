package com.xiey.im.im.module_main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiey.im.im.BuildConfig;
import com.xiey.im.im.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_test)
    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (BuildConfig.isTestEnvironment) {
            tv_test.setText("true");
        } else {
            tv_test.setText("false");
        }
    }
}
