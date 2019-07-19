// IAppleService.aidl
package com.print.aidldemo;
import com.print.aidldemo.Apple;
import com.print.aidldemo.AppleChangeListener;
// Declare any non-default types here with import statements

interface IAppleService {

void conncet(String msg);

void disConnect(String msg);

List<Apple>getApples();

void addApple(in Apple apple);


void regisertAppleChangeListener(AppleChangeListener listener);
void unRegisertAppleChangeListener();
}
