package com.moch.javaquiz.value_objects;

import android.graphics.drawable.Drawable;

public class Task {
    private int lab;
    private String task;
    private String image;
    private String code;
    private Drawable drawable = null;

    public Task() {
    }

    public Task(int lab, String task, String image, String code) {
        this.lab = lab;
        this.task = task;
        this.image = image;
        this.code = code;
    }

    public int getLab() {
        return lab;
    }

    public void setLab(int lab) {
        this.lab = lab;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}