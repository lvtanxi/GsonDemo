package com.lv.gsondemo.model;

/**
 * User: 吕勇
 * Date: 2016-09-20
 * Time: 15:32
 * Description:
 */
public class User3 {
    public String name="lv";
    public String address = "双流";
    public int age=0;

    @Override
    public String toString() {
        return "User3{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
