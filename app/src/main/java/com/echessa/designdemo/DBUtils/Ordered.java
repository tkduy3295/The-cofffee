package com.echessa.designdemo.DBUtils;

import java.io.Serializable;

/**
 * Created by sung on 28/09/2017.
 */

public class Ordered implements Serializable {
    private String name;
    private String price;
    private int quatity;

    public Ordered(String name, String price, int quatity) {
        this.name = name;
        this.price = price;
        this.quatity = quatity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }
}