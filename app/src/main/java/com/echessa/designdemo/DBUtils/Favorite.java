package com.echessa.designdemo.DBUtils;

/**
 * Created by sung on 18/08/2017.
 */

public class Favorite {

    private String id;
    private String name;
    private int price;
    private int createAt;
    private String urlImage;
    private int totalOrder;
    private int quatity;

    public Favorite(String id, String name, int price, int createAt, String urlImage, int totalOrder, int quatity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createAt = createAt;
        this.urlImage = urlImage;
        this.totalOrder = totalOrder;
        this.quatity = quatity;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }
}
