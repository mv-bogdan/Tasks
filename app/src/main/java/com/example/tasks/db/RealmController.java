package com.example.tasks.db;

import android.content.Context;

import com.example.tasks.db.TasksModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmController {

    private Realm realm;

    public RealmController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void addInfo(String title) {
        realm.beginTransaction();

        TasksModel realmObject = realm.createObject(TasksModel.class);
        int id = getNextKey();
        realmObject.setID(id);
        realmObject.setName(title);
        realm.commitTransaction();
    }

    public RealmResults<TasksModel> getInfo() {
        return realm.where(TasksModel.class).findAll();
    }

    public void updateInfo(int id, String title) {
        realm.beginTransaction();
        TasksModel realmObject = realm.where(TasksModel.class).equalTo("id", id).findFirst();
        realmObject.setName(title);
        realm.commitTransaction();
    }

    public void removeItemById(int id) {
        realm.beginTransaction();
        RealmResults<TasksModel> rows = realm.where(TasksModel.class).equalTo("id", id).findAll();
        rows.clear();
        realm.commitTransaction();
    }

    private int getNextKey() {
        return realm.where(TasksModel.class).max("id").intValue() + 1;
    }
}
