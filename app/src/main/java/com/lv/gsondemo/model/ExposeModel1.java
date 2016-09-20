package com.lv.gsondemo.model;

import com.google.gson.annotations.Expose;

/**
 * User: 吕勇
 * Date: 2016-09-20
 * Time: 16:20
 * Description:
 */
public class ExposeModel1 {
    @Expose
    private int id = 0;
    @Expose(serialize = false)
    private String name = "xx";

    private String wode="youyou";

    public String getWode() {
        return wode;
    }

    public void setWode(String wode) {
        this.wode = wode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
