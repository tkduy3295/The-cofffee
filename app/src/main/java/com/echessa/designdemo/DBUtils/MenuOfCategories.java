package com.echessa.designdemo.DBUtils;

/**
 * Created by sung on 18/08/2017.
 */

public class MenuOfCategories {

    private int id;
    private int image;
    private String name;
    private String description;
    private int price;

    public MenuOfCategories(int id, String name, int image, String description, int price) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
}
