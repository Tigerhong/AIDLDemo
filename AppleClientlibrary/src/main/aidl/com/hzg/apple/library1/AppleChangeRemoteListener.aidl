// AppleChangeRemoteListener.aidl
package com.hzg.apple.library1;
import com.hzg.apple.library1.Apple;
// Declare any non-default types here with import statements

interface AppleChangeRemoteListener {
void onChange(in List<Apple> apple);
}
