package com.greenstar.greensales.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.adapter.NotificationAdapter;
import com.greenstar.greensales.dao.INotificationDeleteListener;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationController extends AppCompatActivity implements INotificationDeleteListener {
    AppDatabase db = null;
    ListView lvNotificationList = null;
    NotificationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification);

        lvNotificationList = findViewById(R.id.lvNotificationList);

        db = AppDatabase.getAppDatabase(this);

        List<Notification> notifications = new ArrayList<>();
        notifications = getData();
        if(notifications.size()>0) {
            adapter = new NotificationAdapter(this, R.layout.notification_row, R.id.tvCustomerName, notifications,  this);

            lvNotificationList.setAdapter(adapter);
        }else{
            Toast.makeText(this,"There is no pending notifications.", Toast.LENGTH_SHORT).show();
        }
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifications");
    }

    private List<Notification> getData() {
        List<Notification> notifications = new ArrayList<>();

        notifications = db.getNotificationDAO().getAll();

        return notifications;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void deleteNotification(long id) {
        db.getNotificationDAO().deleteNotification(id);
        adapter = new NotificationAdapter(this,R.layout.notification_row, R.id.tvDetailNote, getData(), this );
        lvNotificationList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Toast.makeText(this,"Notification deleted!" , Toast.LENGTH_SHORT).show();
    }
}