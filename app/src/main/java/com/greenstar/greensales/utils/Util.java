package com.greenstar.greensales.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.greenstar.greensales.controller.Codes;
import com.greenstar.greensales.dal.Data;
import com.greenstar.greensales.dal.SyncObject;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.model.LeaveEntry;
import com.greenstar.greensales.model.ProductOrderRelationship;
import com.greenstar.greensales.model.UnapprovedSDCustomer;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Util {
    static WebserviceResponse responseListener;
    public static String saveDataFromJSON(String data, Context context){

        Data dataRetrieved = new Data();

        try {
            dataRetrieved = new Gson().fromJson(data, Data.class);
        }
        catch(Exception e){
int i=0;
        }
        if(dataRetrieved!=null){
            AppDatabase db = AppDatabase.getAppDatabase(context);
            db.getUnapprovedSDCustomerDAO().nukeTable();
            if(dataRetrieved.getCustomers()!=null) {
                db.getCustomerDAO().nukeTable();
                db.getCustomerDAO().insertMultiple(dataRetrieved.getCustomers());
            }

            if(dataRetrieved.getDepots()!=null) {
                db.getDepotDAO().nukeTable();
                db.getDepotDAO().insertMultiple(dataRetrieved.getDepots());
            }

            if(dataRetrieved.getTowns()!=null) {
                db.getTownDAO().nukeTable();
                db.getTownDAO().insertMultiple(dataRetrieved.getTowns());
            }

            if(dataRetrieved.getTownDepots()!=null) {
                db.getTownDepotDAO().nukeTable();
                db.getTownDepotDAO().insertMultiple(dataRetrieved.getTownDepots());
            }

            if(dataRetrieved.getTownCustomers()!=null) {
                db.getTownCustomerDAO().nukeTable();
                db.getTownCustomerDAO().insertMultiple(dataRetrieved.getTownCustomers());
            }

            if(dataRetrieved.getStatuses()!=null) {
                db.getStatusDAO().nukeTable();
                db.getStatusDAO().insertMultiple(dataRetrieved.getStatuses());
            }

            if(dataRetrieved.getSkuGroup()!=null) {
                db.getSdskuGroupDAO().nukeTable();
                db.getSdskuGroupDAO().insertMultiple(dataRetrieved.getSkuGroup());
            }

            if(dataRetrieved.getWorkWiths() != null){
                db.getWorkWithDAO().nukeTable();
                db.getWorkWithDAO().insertMultiple(dataRetrieved.getWorkWiths());
            }

            if(dataRetrieved.getDashboard() != null){
                db.getDashboardDAO().nukeTable();
                db.getDashboardDAO().insertMultiple(dataRetrieved.getDashboard());
            }
        }
        return "Data Saved";

    }

    public static void performSync(final Context context){
        SharedPreferences editor = context.getSharedPreferences(Codes.PREF_NAME, Context.MODE_PRIVATE);
        String code = editor.getString("code","");
        String token = editor.getString("token","");
        RequestParams rp = new RequestParams();
        rp.add("code", code);
        rp.add("token",token);
        AppDatabase db = AppDatabase.getAppDatabase(context);
        List<ProductOrderRelationship> orderProduct = db.getProductOderDAO().loadProductOrder();
        List<UnapprovedSDCustomer> unapprovedSDCustomers = db.getUnapprovedSDCustomerDAO().getAll();
        List<LeaveEntry> leaveEntries = db.getLeaveEntryDAO().getAll();
        SyncObject syncObject = new SyncObject();
        syncObject.setOrderProduct(orderProduct);
        syncObject.setUnapprovedSDCustomers(unapprovedSDCustomers);
        syncObject.setLeaveEntries(leaveEntries);
        final String data = new Gson().toJson(syncObject);
        rp.add("data",data);

        JSONObject response = null;
        HttpUtils.get("sync", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String message = "";
                String status = "";
                String data = "";
                try {
                    status = response.get("status").toString();
                    data = response.get("data").toString();
                }catch(Exception e){

                }
                if(Codes.ALL_OK.equals(status)){
                    saveDataFromJSON(data, context);

                }
                responseListener.responseAlert(response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                int i =9;

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public WebserviceResponse getResponseListener() {
        return responseListener;
    }

    public void setResponseListener(WebserviceResponse responseListener) {
        this.responseListener = responseListener;
    }

    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
    }


}


