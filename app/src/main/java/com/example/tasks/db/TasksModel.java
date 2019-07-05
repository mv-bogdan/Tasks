package com.example.tasks.db;

import io.realm.RealmObject;

public class TasksModel extends RealmObject {

    private int id;
    private String title;
    private long dateComplete;
    private int status;
    private long completedIn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(long dateComplete) {
        this.dateComplete = dateComplete;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public long getCompletedIn() {
        return completedIn;
    }

    public void setCompletedIn(long completedIn) {
        this.completedIn = completedIn;
    }
}
