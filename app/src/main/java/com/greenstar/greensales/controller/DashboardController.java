package com.greenstar.greensales.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

import com.greenstar.greensales.R;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.model.Dashboard;

public class DashboardController  extends AppCompatActivity {
    WebView wvDashboard;
    AppDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        db = AppDatabase.getAppDatabase(this);

        Dashboard dashboard = db.getDashboardDAO().getAll();

        wvDashboard = findViewById(R.id.wvDashboard);
        wvDashboard.loadData(dashboard.getHtml(), "text/html", null);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dashboard");
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
}
