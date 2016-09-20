package com.lv.gsondemo.model;

import com.google.gson.annotations.SerializedName;

/**
 * User: 吕勇
 * Date: 2016-09-20
 * Time: 15:32
 * Description:
 */
public class User {
    public String name="lv";

    @SerializedName("email_address")
    public String address = "双流";

    public int age=0;
}
