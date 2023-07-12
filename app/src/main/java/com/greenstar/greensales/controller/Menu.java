package com.greenstar.greensales.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.model.Notification;
import com.greenstar.greensales.utils.Util;
import com.greenstar.greensales.utils.WebserviceResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity implements View.OnClickListener, WebserviceResponse {
    LinearLayout llOrderBooking;
    LinearLayout llSync;
    LinearLayout llBasket;
    LinearLayout llProfile;
    LinearLayout llLeaveRequest;
    LinearLayout llDashboard;
    LinearLayout llNotification;

    AppDatabase db =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        db = AppDatabase.getAppDatabase(this);

        llOrderBooking = findViewById(R.id.llOrderBooking);
        llOrderBooking.setOnClickListener(this);

        llSync = findViewById(R.id.llSync);
        llSync.setOnClickListener(this);

        llBasket = findViewById(R.id.llBasket);
        llBasket.setOnClickListener(this);

        llProfile = findViewById(R.id.llProfile);
        llProfile.setOnClickListener(this);

        llLeaveRequest = findViewById(R.id.llLeaveRequest);
        llLeaveRequest.setOnClickListener(this);

        llDashboard = findViewById(R.id.llDashboard);
        llDashboard.setOnClickListener(this);

        llNotification = findViewById(R.id.llNotification);
        llNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.llOrderBooking){
            Intent myIntent = new Intent(this, Form.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llSync){

            Util util = new Util();
            util.setResponseListener(this);

            util.performSync(this);


        }else if(v.getId()==R.id.llBasket){
            Intent myIntent = new Intent(this, Basket.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llProfile){

            Intent myIntent = new Intent(this, AddCustomer.class);
            startActivity(myIntent);

        }else if(v.getId()==R.id.llLeaveRequest){
            Intent myIntent = new Intent(this, LeaveEntry.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llDashboard){
            Intent myIntent = new Intent(this, DashboardController.class);
            startActivity(myIntent);
        }else if(v.getId()==R.id.llNotification){
            Intent myIntent = new Intent(this, NotificationController.class);
            startActivity(myIntent);
        }
    }

    @Override
    public void responseAlert(String response) {
        JSONObject responseObj=null;
        String status="";
        String message = "";
        String data="";

        try {
            responseObj = new JSONObject(response);
            status=responseObj.get("status").toString();
            message=responseObj.get("message").toString();
            data=responseObj.get("data").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(Codes.ALL_OK.equals(status)){
            db.getOrderDAO().nukeTable();
            db.getProductDAO().nukeTable();
            db.getLeaveEntryDAO().nukeTable();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}