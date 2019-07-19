package com.print.appleservice2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.hzg.apple.library1.Apple;
import com.hzg.apple.library1.AppleChangeRemoteListener;
import com.hzg.apple.library1.IAppleService;

import java.util.ArrayList;
import java.util.List;

public class AppleService extends Service {
    private static final String TAG = "AppleService";
    List<Apple> list = new ArrayList<>();
    AppleChangeRemoteListener onAppleChangeListener;
    private boolean flag = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
        Apple apple = new Apple();
        apple.setSize(20);
        apple.setDes("这是一个红苹果");
        apple.setName("红苹果");
        list.add(apple);
        flag=true;
        Log.i(TAG, "onCreate: "+list.toString());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        //每隔五秒往客户端发送数据
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (onAppleChangeListener != null) {
                        try {
                            Log.i(TAG, "run: "+list.toString());
                            Log.i("AppleService", "run: "+onAppleChangeListener.toString());
//                            List<Apple>listTemp=new ArrayList<>();
//                            int random = (int) (Math.random() * 10);
//                            Apple apple=new Apple();
//                            apple.setDes("这是" + random + "苹果");
//                            apple.setSize(random);
//                            apple.setName("苹果" + random);
//                            listTemp.add(apple);
                            onAppleChangeListener.onChange(list);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    IAppleService.Stub mIBinder = new IAppleService.Stub() {

        @Override
        public void conncet(String msg) throws RemoteException {
            Log.i(TAG, "conncet: " + msg);
        }

        @Override
        public void disConnect(String msg) throws RemoteException {
            Log.i(TAG, "disConnect: " + msg);
        }

        @Override
        public List<Apple> getApples() throws RemoteException {
            return list;
        }

        @Override
        public void addApple(Apple apple) throws RemoteException {
            list.add(apple);
        }

        @Override
        public void regisertRemoteListener(AppleChangeRemoteListener listener) throws RemoteException {
            Log.i("AppleService", "regisertAppleChangeListener: "+listener.toString());
            onAppleChangeListener = listener;
        }

        @Override
        public void unRegisertRemoteListener() throws RemoteException {
            onAppleChangeListener = null;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind:   intent = " + intent.toString());
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        flag=false;
        super.onDestroy();
    }
}
