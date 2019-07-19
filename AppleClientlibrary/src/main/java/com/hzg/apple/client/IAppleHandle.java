package com.hzg.apple.client;

import com.hzg.apple.library1.Apple;

import java.util.List;

public interface IAppleHandle {

    void release();

    void conncet(String msg);


    void disConnect(String msg);


    List<Apple> getApples();

    void addApple(Apple apple);

    void regisertAppleChangeListener(AppleChangeListener listener);

    void unRegisertAppleChangeListener();
}
