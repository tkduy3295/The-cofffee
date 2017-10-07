package com.echessa.designdemo.DBUtils;

import java.io.Serializable;

/**
 * Created by sung on 01/10/2017.
 */

public class Ordered implements Serializable {
    private String itemId;
    private String srcImage;
    private String name;
    private int price;
    private int quantity;

    public Ordered(String itemId, String srcImage, String name, int price, int quantity) {
        this.itemId = itemId;
        this.srcImage = srcImage;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
