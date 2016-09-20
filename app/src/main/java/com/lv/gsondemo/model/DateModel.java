package com.lv.gsondemo.model;

import java.util.Date;

/**
 * User: 吕勇
 * Date: 2016-09-20
 * Time: 16:11
 * Description:
 */
public class DateModel {
    private String name;
    private Date mDate = new Date();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "DateModel{" +
                "name='" + name + '\'' +
                ", mDate=" + mDate +
                '}';
    }
}
