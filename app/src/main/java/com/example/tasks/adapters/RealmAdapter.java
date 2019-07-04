package com.example.tasks.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tasks.db.TasksModel;
import com.example.tasks.R;
import com.example.tasks.db.RealmController;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class RealmAdapter extends RealmBaseAdapter<TasksModel> {

    private OnClickListener onClickListener;

    private int CurrentBottomMenuElement = 0;

    public RealmAdapter(Context context, RealmResults<TasksModel> realmResults) {
        super(context, realmResults, true);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final TasksModel model = getRealmResults().get(position);

        if (model.getStatus() == CurrentBottomMenuElement) {
            Log.d("log", ""+CurrentBottomMenuElement);
            convertView = inflater.inflate(R.layout.task_standard_layout, parent, false);
            RealmViewHolder viewHolder = new RealmViewHolder(convertView);

            viewHolder.title.setText(model.getTitle());
            viewHolder.date.setText(model.getDate());
            viewHolder.buttonComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new RealmController(context).changeStatusComplete(getRealmResults().get(position).getId());

                    onClickListener.onButtonCompleteClick(model.getId());
                }
            });
            viewHolder.TaskLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onTaskLayoutClick(model.getId(), model.getTitle(), model.getDate());
                }

            });

        } else {
            convertView = inflater.inflate(R.layout.task_empty_layout, parent, false);
        }
        return convertView;
    }
    public RealmResults<TasksModel> getRealmResults() {
        return realmResults;
    }

    public class RealmViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.date)
        TextView date;

        @BindView(R.id.TaskLayout)
        LinearLayout TaskLayout;

        @BindView(R.id.buttonComplete)
        Button buttonComplete;

        public RealmViewHolder(final View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void onChangeCurrentBottomMenu(int n) {
        CurrentBottomMenuElement = n;
        Log.d("log", ""+CurrentBottomMenuElement);
        super.notifyDataSetChanged();
    }

    public interface OnClickListener {
        void onTaskLayoutClick(int id, String title, String date);
        void onButtonCompleteClick(int id);
    }
}