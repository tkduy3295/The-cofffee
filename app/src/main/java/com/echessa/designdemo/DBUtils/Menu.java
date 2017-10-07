package com.echessa.designdemo.DBUtils;

/**
 * Created by sung on 18/08/2017.
 */

public class Menu {

    private String id;
    private String name;
    private String description;
    private int price;
    private String urlImage;
    private int totalFavorite;
    private int quatity;

    public Menu(String id, String name, String description, int price, String urlImage, int totalFavorite, int quatity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.urlImage = urlImage;
        this.totalFavorite = totalFavorite;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getTotalOrder() {
        return totalFavorite;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalFavorite = totalOrder;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }
}
