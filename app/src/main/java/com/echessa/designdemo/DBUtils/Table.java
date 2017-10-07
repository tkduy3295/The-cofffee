package com.echessa.designdemo.DBUtils;

import java.io.Serializable;

/**
 * Created by sung on 26/09/2017.
 */

public class Table {

    private String id;
    private String name;
    private int positionX;
    private int positionY;
    private String receiptId;


    public Table(String id, String name, int positionX, int positionY, String receiptId) {
        this.id = id;
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.receiptId = receiptId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }
}
