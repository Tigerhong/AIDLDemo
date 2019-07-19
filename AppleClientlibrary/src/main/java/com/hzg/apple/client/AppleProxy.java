package com.hzg.apple.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.hzg.apple.library1.Apple;
import com.hzg.apple.library1.AppleChangeRemoteListener;
import com.hzg.apple.library1.IAppleService;

import java.util.List;

public class AppleProxy implements ServiceConnection, IAppleHandle {
    IAppleService mIAppleService;
    private String TAG = AppleProxy.class.getSimpleName();
    private Context mContext;
    private boolean isConnect=false;
    public AppleProxy(Context mContext) {
        this.mContext = mContext;
        bindPrinterService();
    }

    private void bindPrinterService() {
        Intent intent = new Intent();
        intent.setAction("com.print.appleservice2.Bind");
        intent.setPackage("com.print.appleservice2");
        mContext.bindService(intent, this, Context.BIND_AUTO_CREATE);
    }
    @Override
    public void release(){
        if (isConnect){
            mContext.unbindService(this);
            mIAppleService=null;
            isConnect=false;
        }
    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        isConnect=true;
        mIAppleService = IAppleService.Stub.asInterface(service);
        Log.i(TAG, "onServiceConnected: " + mIAppleService.toString());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.i(TAG, "onServiceDisconnected: ");
        mIAppleService = null;
        isConnect=false;
        mContext.unbindService(this);
    }

    @Override
    public void conncet(String msg) {
        if (mIAppleService == null) {
            Log.i(TAG, "conncet:  服务暂无启动");
            return;
        }
        try {
            mIAppleService.conncet(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disConnect(String msg) {
        if (mIAppleService == null) {
            Log.i(TAG, "disConnect:  服务暂无启动");
            return;
        }
        try {
            mIAppleService.disConnect(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Apple> getApples() {
        if (mIAppleService == null) {
            Log.i(TAG, "getApples:  服务暂无启动");
            return null;
        }
        try {
            return mIAppleService.getApples();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addApple(Apple apple) {
        if (mIAppleService == null) {
            Log.i(TAG, "addApple:  服务暂无启动");
            return;
        }
        try {
            mIAppleService.addApple(apple);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void regisertAppleChangeListener(AppleChangeListener listener) {
        if (mIAppleService == null) {
            Log.i(TAG, "regisertAppleChangeListener:  服务暂无启动");
            return;
        }
        try {
            mIAppleService.regisertRemoteListener(new AppleChangeListenerImpl(listener));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class AppleChangeListenerImpl extends AppleChangeRemoteListener.Stub {
        AppleChangeListener listener;

        public AppleChangeListenerImpl(AppleChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onChange(List<Apple> list) {
            if (listener != null) {
                listener.onChange(list);
            }
        }
    }

    @Override
    public void unRegisertAppleChangeListener() {
        if (mIAppleService == null) {
            Log.i(TAG, "unRegisertAppleChangeListener:  服务暂无启动");
            return;
        }
        try {
            mIAppleService.unRegisertRemoteListener();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
