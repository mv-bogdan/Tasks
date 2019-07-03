package com.example.tasks.db;

import io.realm.RealmObject;

public class TasksModel extends RealmObject {

    private int id;
    private String title;
    private String date;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }
}
