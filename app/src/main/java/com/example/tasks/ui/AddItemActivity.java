package com.example.tasks.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
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

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEdit.setText(sdf.format(myCalendar.getTime()));
    }
}