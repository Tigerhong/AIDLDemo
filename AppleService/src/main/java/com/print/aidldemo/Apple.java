package com.print.aidldemo;

import android.os.Parcel;
import android.os.Parcelable;

public class Apple implements Parcelable {
    private String name;
    private int size;
    private String des;

    public Apple() {
    }

    protected Apple(Parcel in) {
        name = in.readString();
        size = in.readInt();
        des = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public static final Creator<Apple> CREATOR = new Creator<Apple>() {
        @Override
        public Apple createFromParcel(Parcel in) {
            return new Apple(in);
        }

        @Override
        public Apple[] newArray(int size) {
            return new Apple[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(size);
        dest.writeString(des);
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", des='" + des + '\'' +
                '}';
    }
}
