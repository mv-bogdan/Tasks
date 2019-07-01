package com.example.tasks.ui;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tasks.R;
import com.example.tasks.db.RealmController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tasks.ui.MainActivity.ID;
import static com.example.tasks.ui.MainActivity.IS_EDIT;
import static com.example.tasks.ui.MainActivity.TITLE;

public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.title)
    EditText title;

    private boolean isEditMode = false;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);

        if(getIntent().getExtras() != null) {
            isEditMode = getIntent().getExtras().getBoolean(IS_EDIT);
            title.setText(getIntent().getExtras().getString(TITLE));
            id = getIntent().getExtras().getInt(ID);
        }
    }

    @OnClick(R.id.addButton)
    public void onAddClick() {
        if(!isEditMode)
            new RealmController(this).addInfo(title.getText().toString());
        else
            new RealmController(this).updateInfo(id, title.getText().toString());
        finish();
    }
}