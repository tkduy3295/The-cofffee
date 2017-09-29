package com.echessa.designdemo.DBUtils;

import java.io.Serializable;

/**
 * Created by sung on 26/09/2017.
 */

public class Table {

    private String id;
    private String name;
    private String receiptId;
    private String createAt;
    private Position position;
    private SizeTable sizeTable;

    public Table(String id, String name, String receiptId, String createAt, Position position, SizeTable sizeTable) {
        this.id = id;
        this.name = name;
        this.receiptId = receiptId;
        this.createAt = createAt;
        this.position = position;
        this.sizeTable = sizeTable;
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

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public SizeTable getSizeTable() {
        return sizeTable;
    }

    public void setSizeTable(SizeTable sizeTable) {
        this.sizeTable = sizeTable;
    }
}
