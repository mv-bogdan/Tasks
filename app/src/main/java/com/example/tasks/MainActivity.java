package com.example.tasks;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private TextView mTextCardView;
    private TextView mTextQuantity;
    private LinearLayout mLinearLayout;
    private ArrayList<TextView> arrayCards = new ArrayList<>();
    private int mNum = 0;

  /*  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        mTextQuantity = findViewById(R.id.textView_quantity);
        mLinearLayout = findViewById(R.id.LinearLayoutCard);
        //navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void onClickCreate(View view) {
        TextView newTextView = new TextView(this);
        mNum += 1;
        newTextView.setText(Integer.toString(mNum));
        arrayCards.add(newTextView);
        mLinearLayout.addView(newTextView);
    }

    public void onClickShow(View view) {
        mTextQuantity.setText(Integer.toString(arrayCards.size()));
    }
}
