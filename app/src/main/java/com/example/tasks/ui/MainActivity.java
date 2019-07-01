package com.example.tasks.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tasks.R;
import com.example.tasks.adapters.RealmAdapter;
import com.example.tasks.db.RealmController;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RealmAdapter.OnClickListener {

    private static final int INTENT_REQUEST = 123;

    public static final String TITLE = "title";
    public static final String ID = "id";
    public static final String IS_EDIT = "isEdit";

    @BindView(R.id.listView)
    ListView recyclerView;

    private RealmAdapter realmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    private void setupAdapter() {
        realmAdapter = new RealmAdapter(this, new RealmController(this).getInfo());
        realmAdapter.setOnClickListener(this);
        recyclerView.setAdapter(realmAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setupAdapter();
    }

    @Override
    public void onEditButtonClick(int id, String title) {
        Intent intent = new Intent(this, AddItemActivity.class);
        intent.putExtra(IS_EDIT, true);
        intent.putExtra(ID, id);
        intent.putExtra(TITLE, title);
        startActivity(intent);
    }
}