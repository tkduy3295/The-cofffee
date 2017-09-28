package com.echessa.designdemo.DBUtils;

import java.util.List;

/**
 * Created by sung on 17/08/2017.
 */

public class Categories {
    private String id;
    private String name;
    private int createAt;
    private String urlImage;
    private List<MenuOfCategories> menuOfCategories;

    public Categories(String id, String name, int createAt, String urlImage, List<MenuOfCategories> menuOfCategories) {
        this.id = id;
        this.name = name;
        this.createAt = createAt;
        this.urlImage = urlImage;
        this.menuOfCategories = menuOfCategories;
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

    public List<MenuOfCategories> getMenuOfCategories() {
        return menuOfCategories;
    }

    public void setMenuOfCategories(List<MenuOfCategories> menuOfCategories) {
        this.menuOfCategories = menuOfCategories;
    }
}
