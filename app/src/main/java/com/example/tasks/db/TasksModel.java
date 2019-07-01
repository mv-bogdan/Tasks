package com.example.tasks.db;

import io.realm.RealmObject;

public class TasksModel extends RealmObject {

    private int id;

    private String name;

    public TasksModel() { id = -1; name = "null"; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }
}
