package com.example.tasks.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tasks.R;
import com.example.tasks.db.RealmController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tasks.ui.MainActivity.ID;
import static com.example.tasks.ui.MainActivity.IS_EDIT;
import static com.example.tasks.ui.MainActivity.TITLE;
import static com.example.tasks.ui.MainActivity.DATE;

public class AddItemActivity extends AppCompatActivity {

    @BindView(R.id.title)
    EditText title;

    @BindView(R.id.dateEdit)
    EditText dateEdit;

    @BindView(R.id.delete_task_button)
    Button deleteButton;

    @BindView(R.id.addButton)
    Button addButton;


    private boolean isEditMode = false;
    private String date;
    private int id;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);

        if(getIntent().getExtras() != null) {
            isEditMode = getIntent().getExtras().getBoolean(IS_EDIT);
            title.setText(getIntent().getExtras().getString(TITLE));
            id = getIntent().getExtras().getInt(ID);
            dateEdit.setText(getIntent().getExtras().getString(DATE));
        }
        if (!isEditMode) {
            deleteButton.setVisibility(View.INVISIBLE);
        } else
        {
            addButton.setText(R.string.change_task);
        }
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddItemActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @OnClick(R.id.addButton)
    public void onAddClick() {
        if(!isEditMode)
            new RealmController(this).addInfo(title.getText().toString(), dateEdit.getText().toString());
        else
            new RealmController(this).updateInfo(id, title.getText().toString(), dateEdit.getText().toString());
        finish();
    }

    @OnClick(R.id.delete_task_button)
    public void onDeleteButtonClick() {
        new RealmController(this).removeItemById(id);
        finish();
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEdit.setText(sdf.format(myCalendar.getTime()));
    }
}