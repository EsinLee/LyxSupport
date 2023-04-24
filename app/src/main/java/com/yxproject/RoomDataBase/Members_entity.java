package com.yxproject.RoomDataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Members")
public class Members_entity {
    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    private String name;
    private String phone;
    private String rank;
    private String jobfunctions;
    private String relation;
    private String personaltags;
    private String elseInfo;

    public Members_entity(String name, String phone, String rank, String jobfunctions, String relation, String personaltags, String elseInfo) {
        this.name = name;
        this.phone = phone;
        this.rank = rank;
        this.jobfunctions = jobfunctions;
        this.relation = relation;
        this.personaltags = personaltags;
        this.elseInfo = elseInfo;
    }
    @Ignore//如果要使用多形的建構子，必須加入@Ignore
    public Members_entity(int id, String name, String phone, String rank, String jobfunctions, String relation, String personaltags, String elseInfo) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.rank = rank;
        this.jobfunctions = jobfunctions;
        this.relation = relation;
        this.personaltags = personaltags;
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

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getJobfunctions() {
        return jobfunctions;
    }
    public void setJobunctions(String jobfunctions) {
        this.jobfunctions = jobfunctions;
    }

    public String getRelation() {
        return relation;
    }
    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPersonaltags() {
        return personaltags;
    }
    public void setPersonaltags(String personaltags) {
        this.personaltags = personaltags;
    }

    public String getElseInfo() {
        return elseInfo;
    }
    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}

