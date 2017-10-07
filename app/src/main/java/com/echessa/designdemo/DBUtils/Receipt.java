package com.echessa.designdemo.DBUtils;

import java.util.List;

/**
 * Created by sung on 20/08/2017.
 */

public class Receipt {
    private String id;
    private String tableId;
    private int totalPrice;

    private List<Ordered> listOrdered;

    public Receipt(String id, String tableId, int totalPrice, List<Ordered> listOrdered) {
        this.id = id;
        this.tableId = tableId;
        this.totalPrice = totalPrice;
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

    public List<Ordered> getListOrdered() {
        return listOrdered;
    }

    public void setListOrdered(List<Ordered> listOrdered) {
        this.listOrdered = listOrdered;
    }
}
