package com.echessa.designdemo.DBUtils;

/**
 * Created by sung on 27/09/2017.
 */

public class SizeTable {
    private int width;
    private int height;

    public SizeTable(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
