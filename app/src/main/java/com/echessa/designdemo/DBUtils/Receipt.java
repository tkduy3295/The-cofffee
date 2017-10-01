package com.echessa.designdemo.DBUtils;

import java.util.List;

/**
 * Created by sung on 20/08/2017.
 */

public class Receipt {
    private String id;
    private String tableId;
    private int totalPrice;
    private int createAt;
    private int payAt;

    private List<Ordered> listOrdered;

    public Receipt(String id, String tableId, int totalPrice, int createAt, int payAt, List<Ordered> listOrdered) {
        this.id = id;
        this.tableId = tableId;
        this.totalPrice = totalPrice;
        this.createAt = createAt;
        this.payAt = payAt;
        this.listOrdered = listOrdered;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCreateAt() {
        return createAt;
    }

    public void setCreateAt(int createAt) {
        this.createAt = createAt;
    }

    public int getPayAt() {
        return payAt;
    }

    public void setPayAt(int payAt) {
        this.payAt = payAt;
    }

    public List<Ordered> getListOrdered() {
        return listOrdered;
    }

    public void setListOrdered(List<Ordered> listOrdered) {
        this.listOrdered = listOrdered;
    }
}
