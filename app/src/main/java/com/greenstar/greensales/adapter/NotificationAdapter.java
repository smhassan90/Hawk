package com.greenstar.greensales.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.dal.ProductDetail;
import com.greenstar.greensales.dao.INotificationDeleteListener;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.model.Notification;
import com.greenstar.greensales.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Notification> implements View.OnClickListener {
    private Activity mActivity;
    private List<Notification> list = new ArrayList<>();
    AppDatabase db =null;
    INotificationDeleteListener delete = null;
    public NotificationAdapter(@NonNull Activity activity, int resource, int textViewResourceId,
                               List<Notification> list, INotificationDeleteListener delete) {
        super(activity, resource, textViewResourceId, list);
        db = AppDatabase.getAppDatabase(activity);
        mActivity = activity;
        this.delete = delete;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Notification getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        View view=null;
        Notification i = list.get(position);
        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.notification_new_row, null);
        }

        TextView tvShortNote = (TextView) v.findViewById(R.id.tvShortNote);
        TextView tvDetailNote = (TextView) v.findViewById(R.id.tvDetailNote);
        TextView tvDate = (TextView) v.findViewById(R.id.tvDate);
        TextView tvNew = (TextView) v.findViewById(R.id.tvNew);
        ImageButton btnDelete = v.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(this);

        if(i.getStatus()==0){
            tvNew.setText("NEW");
        }else{
            tvNew.setVisibility(View.INVISIBLE);
        }

        tvShortNote.setText("Topic : " +i.getShortNote());
        tvDetailNote.setText("Detail Note : "+i.getDetailNote());
        tvDate.setText("Date : "    + i.getNotificationDate());

        btnDelete.setTag(i.getId());
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnDelete){
            Object obj =  v.getTag()==null?"0":v.getTag();
            long id = (long)obj;
            if(id!=0){
                delete.deleteNotification(id);
            }else{
                Toast.makeText(mActivity, "Something went wrong while deleting Notification",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
