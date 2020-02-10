package com.moch.javaquiz.value_objects;

public class Task {
    private int lab;
    private String task;
    private int image;
    private String code;

    public Task() {}

    public Task(int lab, String task, int image, String code) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
