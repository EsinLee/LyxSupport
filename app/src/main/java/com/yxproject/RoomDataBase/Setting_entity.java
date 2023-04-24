package com.yxproject.RoomDataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Setting")
public class Setting_entity {
    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    private String name;
    private String value_a;
    private String value_b;
    private String elseInfo;

    public Setting_entity(String name, String value_a, String value_b,String elseInfo) {
        this.name = name;
        this.value_a = value_a;
        this.value_b = value_b;
        this.elseInfo = elseInfo;
    }

    @Ignore//如果要使用多形的建構子，必須加入@Ignore
    public Setting_entity(int id, String name, String value_a, String value_b, String elseInfo) {
        this.id = id;
        this.name = name;
        this.value_a = value_a;
        this.value_b = value_b;
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

    public String getValue_a() {
        return value_a;
    }
    public void setValue_a(String value_a) {
        this.value_a = value_a;
    }

    public String getValue_b() {
        return value_b;
    }
    public void setValue_b(String value_b) {
        this.value_b = value_b;
    }

    public String getElseInfo() {
        return elseInfo;
    }
    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}
