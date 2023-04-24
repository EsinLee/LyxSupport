package com.yxproject.RoomDataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Items")
public class Items_entity {
    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    private String name;
    private String itemid;
    private int quantity;
    private int indecrease;
    private String storageplace;
    private String itemtype;
    private String source;
    private String elseInfo;

    public Items_entity(String name, String itemid, int quantity, int indecrease, String storageplace, String itemtype, String source, String elseInfo) {
        this.name = name;
        this.itemid = itemid;
        this.quantity = quantity;
        this.indecrease = indecrease;
        this.storageplace = storageplace;
        this.itemtype = itemtype;
        this.source = source;
        this.elseInfo = elseInfo;
    }

    @Ignore//如果要使用多形的建構子，必須加入@Ignore
    public Items_entity(int id, String name, String itemid, int quantity, int indecrease, String storageplace, String itemtype, String source, String elseInfo) {
        this.id = id;
        this.name = name;
        this.itemid = itemid;
        this.quantity = quantity;
        this.indecrease = indecrease;
        this.storageplace = storageplace;
        this.itemtype = itemtype;
        this.source = source;
        this.elseInfo = elseInfo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getItemid() {
        return itemid;
    }
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIndecrease() {
        return indecrease;
    }
    public void setIndecrease(int indecrease) {
        this.indecrease = indecrease;
    }

    public String getStorageplace() {
        return storageplace;
    }
    public void setStorageplace(String storageplace) {
        this.storageplace = storageplace;
    }

    public String getItemtype() {return itemtype;}
    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getSource() {return source;}
    public void setSource(String source) {
        this.source = source;
    }

    public String getElseInfo() {
        return elseInfo;
    }
    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}
