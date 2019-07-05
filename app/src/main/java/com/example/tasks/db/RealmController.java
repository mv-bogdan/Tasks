package com.example.tasks.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmController {


    private Realm realm;
    private static RealmController instance;

    private Context context;

    public RealmController(Context cont) {

        context = cont;

        RealmConfiguration config = new RealmConfiguration.Builder(cont)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

    }

    public void addInfo(String title, long dateComplete) {
        realm.beginTransaction();

        TasksModel realmObject = realm.createObject(TasksModel.class);
        int id = getNextKey();
        realmObject.setId(id);
        realmObject.setTitle(title);
        realmObject.setDateComplete(dateComplete);
        realmObject.setStatus(0);
        realmObject.setCompletedIn(0);
        realm.commitTransaction();
    }

    public RealmResults<TasksModel> getAllTasks() {
        return realm.where(TasksModel.class).findAll();
    }

    public RealmResults<TasksModel> getActiveTasks() {
        return realm.where(TasksModel.class).equalTo("status", 0).findAll();
    }

    public RealmResults<TasksModel> getCompletedTasks() {
        return realm.where(TasksModel.class).equalTo("status", 1).findAll();
    }

    public void updateInfo(int id, String title, long dateComplete) {
        realm.beginTransaction();
        TasksModel realmObject = realm.where(TasksModel.class).equalTo("id", id).findFirst();
        realmObject.setTitle(title);
        realmObject.setDateComplete(dateComplete);
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

    public void changeStatusComplete(int id) {
        realm.beginTransaction();
        TasksModel realmObject = realm.where(TasksModel.class).equalTo("id", id).findFirst();
        realmObject.setStatus(1);
        realmObject.setCompletedIn(System.currentTimeMillis());
        realm.commitTransaction();
    }
}
