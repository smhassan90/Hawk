package com.greenstar.greensales.controller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.adapter.CustomerAdapter;
import com.greenstar.greensales.adapter.DepotAdapter;
import com.greenstar.greensales.adapter.TownAdapter;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.model.SDCustomer;
import com.greenstar.greensales.model.SDDepot;
import com.greenstar.greensales.model.SDTown;
import com.greenstar.greensales.model.UnapprovedSDCustomer;
import com.greenstar.greensales.utils.Util;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddCustomer extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener, LocationListener {
    LocationManager locationManager = null;
    String latLon = "";
    Location location = null;

    AppDatabase db = null;

    List<SDCustomer> customers = new ArrayList<>();

    SearchableSpinner spCustomer;

    LinearLayout llOrderBooking;
    LinearLayout llParentLayout;

    CustomerAdapter customerAdapter;

    Button btnAddSDCustomer;
    EditText etCustomerName;
    EditText etCustomerAddress;

    private LocationListener locationListener = null;

    ProgressDialog dialog = null;
    boolean userSelect = false;

    final int ADDRESSLENGTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getAppDatabase(this);
        locationRequest();
        setContentView(R.layout.add_customer);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        llOrderBooking = findViewById(R.id.llOrderBooking);
        llParentLayout = findViewById(R.id.llParentLayout);

        spCustomer = (SearchableSpinner) findViewById(R.id.spCustomer);
        customers = db.getCustomerDAO().getAllSDCustomer();

        customerAdapter = new CustomerAdapter(this, R.layout.town_list_item, R.id.tvTownNames, customers);

        spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (userSelect) {
                    userSelect = false;
                    return;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        spCustomer.setAdapter(customerAdapter);
        spCustomer.setTitle("Select Customer");

        btnAddSDCustomer = findViewById(R.id.btnAddSDCustomer);
        btnAddSDCustomer.setOnClickListener(this);

        etCustomerAddress = findViewById(R.id.etCustomerAddress);
        etCustomerName = findViewById(R.id.etCustomerName);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Adding SD Customer");
        dialog.setMessage("Fetching location. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
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
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddSDCustomer) {
            if(isValid()){
                dialog.show();

                saveData();
                Toast.makeText(this,"SD Customer added. Perform Sync to send for approval.", Toast.LENGTH_SHORT).show();
                finish();

                dialog.dismiss();
            }
        }
    }

    public void saveData(){
        SDCustomer sdCustomer = new SDCustomer();
        sdCustomer = (SDCustomer) spCustomer.getSelectedItem();

        UnapprovedSDCustomer customer = new UnapprovedSDCustomer();
        customer.setAddress(etCustomerAddress.getText().toString());
        customer.setName(etCustomerName.getText().toString());
        customer.setCustCode(sdCustomer.getCustCode());
        customer.setSaveTime("");
        customer.setLatLong(latLon);
        customer.setId(Util.getNextID(this,Codes.UnapprovedCustomers));

        db.getUnapprovedSDCustomerDAO().insert(customer);
    }

    private boolean isValid(){
        boolean isValid = false;

        SDCustomer sdCustomer = new SDCustomer();
        sdCustomer = (SDCustomer) spCustomer.getSelectedItem();
        if(sdCustomer==null){
            Toast.makeText(this,"Please select SD Customer.",Toast.LENGTH_SHORT).show();
        }else if(etCustomerName.getText().toString()==null || "".equals(etCustomerName.getText().toString())){
            Toast.makeText(this,"Please provide the customer name.",Toast.LENGTH_SHORT).show();
        }else if(etCustomerAddress.getText().toString()==null || "".equals(etCustomerAddress.getText().toString())){
            Toast.makeText(this,"Please provide the customer address.",Toast.LENGTH_SHORT).show();
        }else if(etCustomerAddress.getText().toString().length()<ADDRESSLENGTH){
            Toast.makeText(this,"Please provide the complete customer address. Minimum of "+ADDRESSLENGTH+" digits.",Toast.LENGTH_SHORT).show();
        }else{
            isValid = true;
        }

        return isValid;
    }

    private void locationRequest() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);

            return;
        }else{
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        if(location==null)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        else{
            latLon = location.getLatitude()+","+location.getLongitude();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        latLon = location.getLatitude()+","+location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
