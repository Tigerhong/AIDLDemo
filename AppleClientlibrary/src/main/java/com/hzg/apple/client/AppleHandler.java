package com.hzg.apple.client;

import android.content.Context;
import android.os.RemoteException;

import com.hzg.apple.library1.Apple;

import java.util.List;

public class AppleHandler implements IAppleHandle {

    private IAppleHandle appleHandle;
    private AppleChangeListener listener;
    private AppleHandler() {

    }

    private static class InstanceHolder {
        private static AppleHandler insatnce = new AppleHandler();
    }

    public static AppleHandler get() {
        return InstanceHolder.insatnce;
    }

    public void initialize(Context context) {
        appleHandle = new AppleProxy(context);
    }

    private boolean isInitialized() {
        return appleHandle != null;
    }

    @Override
    public void release() {
        if (!isInitialized()) return;
        appleHandle.release();
    }

    @Override
    public void conncet(String msg) {
        if (!isInitialized()) return;
        appleHandle.conncet(msg);
    }

    @Override
    public void disConnect(String msg) {
        if (!isInitialized()) return;
        appleHandle.disConnect(msg);
    }

    @Override
    public List<Apple> getApples() {
        if (!isInitialized()) return null;
        return appleHandle.getApples();
    }

    @Override
    public void addApple(Apple apple) {
        if (!isInitialized()) return;
        appleHandle.addApple(apple);
    }

    @Override
    public void regisertAppleChangeListener(AppleChangeListener listener) {
        if (!isInitialized()) return;
        appleHandle.regisertAppleChangeListener(listener);
    }

    @Override
    public void unRegisertAppleChangeListener() {
        if (!isInitialized()) return;
        appleHandle.unRegisertAppleChangeListener();
    }
}
