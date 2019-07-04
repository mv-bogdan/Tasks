package com.example.tasks.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tasks.R;
import com.example.tasks.adapters.RealmAdapter;
import com.example.tasks.db.RealmController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RealmAdapter.OnClickListener {

    private static final int INTENT_REQUEST = 123;

    public static final String TITLE = "title";
    public static final String ID = "id";
    public static final String DATE = "date";
    public static final String IS_EDIT = "isEdit";

    @BindView(R.id.listView)
    ListView recyclerView;

    @BindView(R.id.nav_view)
    BottomNavigationView navView;

    private RealmAdapter realmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupAdapter();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addItem:
                startActivityForResult(new Intent(this, AddItemActivity.class), INTENT_REQUEST);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_active_tasks:
                    realmAdapter.onChangeCurrentBottomMenu(0);

                    return true;
                case R.id.navigation_completed_tasks:
                    realmAdapter.onChangeCurrentBottomMenu(1);

                    return true;
            }
            return false;
        }
    };

    private void setupAdapter() {
        realmAdapter = new RealmAdapter(this, new RealmController(this).getInfo());
        realmAdapter.setOnClickListener(this);
        recyclerView.setAdapter(realmAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setupAdapter();
    }

    @Override
    public void onTaskLayoutClick(int id, String title, String date) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra(IS_EDIT, true);
        intent.putExtra(ID, id);
        intent.putExtra(TITLE, title);
        intent.putExtra(DATE, date);
        startActivity(intent);
    }

    @Override
    public void onButtonCompleteClick(int id) {
        // TODO : solve the completed tasks problem
     }
}