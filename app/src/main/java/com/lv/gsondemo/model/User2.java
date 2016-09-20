package com.lv.gsondemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * User: 吕勇
 * Date: 2016-09-20
 * Time: 15:32
 * Description:
 */
public class User2 {
    public String name;
    @SerializedName(value = "addreaa", alternate = {"address", "email_address"})
    public String address;
    public int age=0;

    @Override
    public String toString() {
        return "User2{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
