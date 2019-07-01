package com.example.tasks.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public RealmAdapter(Context context, RealmResults<TasksModel> realmResults) {
        super(context, realmResults, true);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.task_standard_layout, parent, false);
        RealmViewHolder viewHolder = new RealmViewHolder(convertView);

        final TasksModel model = getRealmResults().get(position);
        viewHolder.title.setText(model.getName());
        viewHolder.ID.setText(model.getID());
        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RealmController(context).removeItemById(getRealmResults().get(position).getID());
            }
        });
        viewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onEditButtonClick(model.getID(), model.getName());
            }
        });
        return convertView;
    }

    public RealmResults<TasksModel> getRealmResults() {
        return realmResults;
    }

    public class RealmViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.description)
        TextView ID;

        @BindView(R.id.editButton)
        ImageView editButton;

        @BindView(R.id.removeButton)
        ImageView removeButton;

        public RealmViewHolder(final View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onEditButtonClick(int id, String title);
    }
}