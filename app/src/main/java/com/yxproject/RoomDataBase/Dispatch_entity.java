package com.yxproject.RoomDataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Dispatch")
public class Dispatch_entity {
    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    private String time;
    private String event;
    private String area;
    private String conductor;
    private String driver;
    private String car;
    private String elseInfo;

    public Dispatch_entity(String time,String event, String area, String conductor, String driver, String car, String elseInfo) {
        this.time = time;
        this.event = event;
        this.area = area;
        this.conductor = conductor;
        this.driver = driver;
        this.car = car;
        this.elseInfo = elseInfo;
    }

    @Ignore//如果要使用多形的建構子，必須加入@Ignore
    public Dispatch_entity(int id, String time, String event, String area, String conductor, String driver, String car, String elseInfo) {
        this.id = id;
        this.time = time;
        this.event = event;
        this.area = area;
        this.conductor = conductor;
        this.driver = driver;
        this.car = car;
        this.elseInfo = elseInfo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }
    public void setEvent(String event) {
        this.event = event;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {this.area = area; }

    public String getConductor() {
        return conductor;
    }
    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public String getDriver() {return driver;}
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getCar() {return car;}
    public void setCar(String car) {
        this.car = car;
    }

    public String getElseInfo() {
        return elseInfo;
    }
    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}
