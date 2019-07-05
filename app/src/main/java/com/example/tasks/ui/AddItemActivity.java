package com.example.tasks.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

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
import static com.example.tasks.ui.MainActivity.IS_COMPLETED;
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
    private boolean isCompletedMode = false;
    private String date;
    private int id;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        ButterKnife.bind(this);
        setInitialDateTime();

        if(getIntent().getExtras() != null) {
            isEditMode = getIntent().getExtras().getBoolean(IS_EDIT);
            isCompletedMode = getIntent().getExtras().getBoolean(IS_COMPLETED);
            title.setText(getIntent().getExtras().getString(TITLE));
            id = getIntent().getExtras().getInt(ID);
            dateEdit.setText(DateUtils.formatDateTime(this,
                    getIntent().getExtras().getLong(DATE),
                    DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                            | DateUtils.FORMAT_SHOW_TIME));
        }
        if (!isEditMode) {
            deleteButton.setVisibility(View.INVISIBLE);
        } else
        {
            addButton.setText(R.string.change_task);
        }
        if (isCompletedMode) {
            title.setEnabled(false);
            dateEdit.setEnabled(false);
            //deleteButton.setVisibility(View.INVISIBLE);
            addButton.setVisibility(View.INVISIBLE);
        }
    }

    private void setInitialDateTime() {
        dateEdit.setText(DateUtils.formatDateTime(this,
                myCalendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    public void onTimeClick(View v) {
        new TimePickerDialog(AddItemActivity.this, t,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE), true)
                .show();
        new DatePickerDialog(AddItemActivity.this, d,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            myCalendar.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    @OnClick(R.id.addButton)
    public void onAddClick() {
        if(!isEditMode)
            new RealmController(this).addInfo(title.getText().toString(), myCalendar.getTimeInMillis());
        else
            new RealmController(this).updateInfo(id, title.getText().toString(), myCalendar.getTimeInMillis());
        finish();
    }

    @OnClick(R.id.delete_task_button)
    public void onDeleteButtonClick() {
        new RealmController(this).removeItemById(id);
        finish();
    }
}