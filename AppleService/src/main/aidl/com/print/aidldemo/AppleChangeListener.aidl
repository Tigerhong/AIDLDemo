// AppleChangeListener.aidl
package com.print.aidldemo;
import com.print.aidldemo.Apple;
// Declare any non-default types here with import statements

interface AppleChangeListener {
 void onChange(in List<Apple> apple);
}
