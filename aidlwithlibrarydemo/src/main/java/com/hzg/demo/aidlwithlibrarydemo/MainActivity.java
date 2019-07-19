package com.hzg.demo.aidlwithlibrarydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hzg.apple.client.AppleChangeListener;
import com.hzg.apple.client.AppleHandler;
import com.hzg.apple.library1.Apple;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.applelist);
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.bt1:
                AppleHandler.get().initialize(this);
                break;
            case R.id.bt4:
                AppleHandler.get().release();
                break;
            case R.id.bt2:
                    AppleHandler.get().conncet("我来连接了");
                break;
            case R.id.bt3:
                    AppleHandler.get().disConnect("断开连接");
                break;
            case R.id.addApple:
                    Apple apple = new Apple();
                    int random = (int) (Math.random() * 10);
                    apple.setDes("这是" + random + "苹果");
                    apple.setSize(random);
                    apple.setName("苹果" + random);
                AppleHandler.get().addApple(apple);
                break;
            case R.id.getApple:
                    List<Apple> apples = AppleHandler.get().getApples();
                    tv.setText(" 苹果列表数据：\n" + apples);
                break;
            case R.id.regisger:
                AppleHandler.get().regisertAppleChangeListener(new AppleChangeListener() {
                    @Override
                    public void onChange(List<Apple> apple) {
                        Log.i(TAG, "onChange: "+apple.toString());
                    }
                });
                break;
            case R.id.unregisger:
                    AppleHandler.get().unRegisertAppleChangeListener();
                break;
        }
    }

}
