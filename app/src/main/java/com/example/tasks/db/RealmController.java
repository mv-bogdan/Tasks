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

//        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
//        realm.setDefaultConfiguration(config);
//        realm = Realm.getDefaultInstance();

        RealmConfiguration config = new RealmConfiguration.Builder(cont)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

    }




    public void addInfo(String title, String date) {
        realm.beginTransaction();

        TasksModel realmObject = realm.createObject(TasksModel.class);
        int id = getNextKey();
        realmObject.setId(id);
        realmObject.setTitle(title);
        realmObject.setDate(date);
        realm.commitTransaction();
    }

    public RealmResults<TasksModel> getInfo() {
        return realm.where(TasksModel.class).findAll();
    }

    public void updateInfo(int id, String title, String date) {
        realm.beginTransaction();
        TasksModel realmObject = realm.where(TasksModel.class).equalTo("id", id).findFirst();
        realmObject.setTitle(title);
        realmObject.setDate(date);
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
