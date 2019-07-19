package com.hzg.appleclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.print.aidldemo.Apple;
import com.print.aidldemo.AppleChangeListener;
import com.print.aidldemo.IAppleService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String ACTION = "com.print.aidldemo.IAppleService";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.applelist);
    }

    private IAppleService mIAppleService;
    ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIAppleService = IAppleService.Stub.asInterface(service);
            Log.i(TAG, "onServiceConnected: " + mIAppleService.toString());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: ");
            mIAppleService = null;
            unbindService(this);
        }
    };

    public void onClick(View view) throws RemoteException {
        switch (view.getId()) {
            case R.id.bt1:
                Intent intent = new Intent(ACTION);
                // 注意在 Android 5.0以后，不能通过隐式 Intent 启动 service，必须制定包名
                intent.setPackage("com.print.aidldemo");
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.bt4:
                unbindService(mServiceConnection);
                break;
            case R.id.bt2:
                if (mIAppleService != null) {
                    mIAppleService.conncet("我来连接了");
                }
                break;
            case R.id.bt3:
                if (mIAppleService != null) {
                    mIAppleService.disConnect("断开连接");
                }
                break;
            case R.id.addApple:
                if (mIAppleService != null) {
                    Apple apple = new Apple();
                    int random = (int) (Math.random() * 10);
                    apple.setDes("这是" + random + "苹果");
                    apple.setSize(random);
                    apple.setName("苹果" + random);
                    mIAppleService.addApple(apple);
                }
                break;
            case R.id.getApple:
                if (mIAppleService != null) {
                    List<Apple> apples = mIAppleService.getApples();
                    tv.setText(" 苹果列表数据：\n" + apples.toString());
                }
                break;
            case R.id.regisger:
                if (mIAppleService != null) {
                    AppleChangeListenerImpl listener = new AppleChangeListenerImpl();
                    Log.i(TAG, "onClick: "+listener.toString());
                    mIAppleService.regisertAppleChangeListener(listener);
                }
                break;
            case R.id.unregisger:
                if (mIAppleService != null) {
                    mIAppleService.unRegisertAppleChangeListener();
                }
                break;
        }
    }
    private class AppleChangeListenerImpl extends AppleChangeListener.Stub{

        @Override
        public void onChange(List<Apple> list) throws RemoteException {
            Log.i(TAG, "onChange: "+list.toString());
        }
    }

}
