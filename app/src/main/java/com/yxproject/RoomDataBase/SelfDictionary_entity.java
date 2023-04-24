package com.yxproject.RoomDataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "SelfDictionary")
public class SelfDictionary_entity {
    @PrimaryKey(autoGenerate = true)//設置是否使ID自動累加
    private int id;
    private String word;
    private String annotation;
    private String tags;
    private String elseInfo;

    public SelfDictionary_entity(String word, String annotation, String tags, String elseInfo) {
        this.word = word;
        this.annotation = annotation;
        this.tags = tags;
        this.elseInfo = elseInfo;
    }

    @Ignore//如果要使用多形的建構子，必須加入@Ignore
    public SelfDictionary_entity(int id, String word, String annotation, String tags, String elseInfo) {
        this.id = id;
        this.word = word;
        this.annotation = annotation;
        this.tags = tags;
        this.elseInfo = elseInfo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }
    public void setWord(String word) {
        this.word = word;
    }

    public String getAnnotation() {
        return annotation;
    }
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getElseInfo() {
        return elseInfo;
    }
    public void setElseInfo(String elseInfo) {
        this.elseInfo = elseInfo;
    }
}
