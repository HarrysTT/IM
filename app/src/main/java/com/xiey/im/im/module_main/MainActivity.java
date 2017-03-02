package com.xiey.im.im.module_main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiey.im.im.BuildConfig;
import com.xiey.im.im.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_test = (TextView) findViewById(R.id.tv_test);
        if (BuildConfig.isTestEnvironment) {
            tv_test.setText("true");
        } else {
            tv_test.setText("false");
        }
    }
}
