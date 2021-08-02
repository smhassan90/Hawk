package com.greenstar.greensales.controller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.adapter.CustomerAdapter;
import com.greenstar.greensales.adapter.DepotAdapter;
import com.greenstar.greensales.adapter.ProductAdapter;
import com.greenstar.greensales.adapter.SKUGroupAdapter;
import com.greenstar.greensales.adapter.StatusAdapter;
import com.greenstar.greensales.adapter.TownAdapter;
import com.greenstar.greensales.adapter.WorkWithAdapter;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.model.GSSWorkWith;
import com.greenstar.greensales.model.Orderr;
import com.greenstar.greensales.model.ProductOrder;
import com.greenstar.greensales.model.SDCustomer;
import com.greenstar.greensales.model.SDDepot;
import com.greenstar.greensales.model.SDSKUGroup;
import com.greenstar.greensales.model.SDStatus;
import com.greenstar.greensales.model.SDTown;
import com.greenstar.greensales.model.SDTownCustomer;
import com.greenstar.greensales.model.SDTownDepot;
import com.greenstar.greensales.utils.Util;
import com.greenstar.greensales.utils.WebserviceResponse;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Form extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, WebserviceResponse, LocationListener {
    LocationManager locationManager = null;
    String latLon = "";
    Form activity = null;
    List<SDDepot> depots = new ArrayList<>();
    List<SDTown> towns = new ArrayList<>();
    List<SDCustomer> customers = new ArrayList<>();
    List<SDStatus> statuses = new ArrayList<>();
    List<SDSKUGroup> skuGroup = new ArrayList<>();
    List<ProductOrder> productOrders = new ArrayList<>();
    List<GSSWorkWith> workWiths = new ArrayList<>();


    boolean userSelect = false;
    AppDatabase db = null;
    Spinner spDepot;
    Spinner spTown;
    SearchableSpinner spCustomer;
    Spinner spStatus;
    Spinner spSKUGroup;
    Spinner spWorkWith;

    ListView lvProducts;

    Button btnAdd;
    Button btnSubmit;

    EditText etQuantity;

    LinearLayout llOrderBooking;
    LinearLayout llParentLayout;

    DepotAdapter depotAdapter;
    TownAdapter townAdapter;
    CustomerAdapter customerAdapter;
    StatusAdapter statusAdapter;
    SKUGroupAdapter skuGroupAdapter;
    ProductAdapter productAdapter;
    WorkWithAdapter workWithAdapter;

    JSONObject responses;

    ProgressDialog dialog = null;

    Location location = null;

    EditText etCommments = null;
    TextView tvName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getAppDatabase(this);
        locationRequest();
        setContentView(R.layout.form_activity);

        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Order Booking");
        dialog.setMessage("Fetching location. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences shared = getSharedPreferences(Codes.PREF_NAME, MODE_PRIVATE);
        String name = (shared.getString("name", ""));

        tvName = findViewById(R.id.tvName);
        tvName.setText(name);

        llOrderBooking = findViewById(R.id.llOrderBooking);
        llParentLayout = findViewById(R.id.llParentLayout);
        activity = this;

        spDepot = (Spinner) findViewById(R.id.spDepot);
        depots = db.getDepotDAO().getAll();

        SDDepot firstDepot = new SDDepot();
        firstDepot.setDepotCode(0);
        firstDepot.setDepotName("Please Select");

        depots.add(0, firstDepot);

        depotAdapter = new DepotAdapter(Form.this, R.layout.town_list_item, R.id.tvTownNames, depots);

        spDepot.setAdapter(depotAdapter);
        spDepot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userSelect = false;

                SDDepot depotSelected = (SDDepot) spDepot.getSelectedItem();
                SDTown town = (SDTown) spTown.getSelectedItem();
                refreshTown(depotSelected.getDepotCode(), town == null ? "0" : town.getTownId());

                SDTown townSelected = (SDTown) spTown.getSelectedItem();
                refreshCustomer(townSelected == null ? "" : townSelected.getTownId());
                return;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        spDepot.setOnTouchListener(this);

        spTown = (Spinner) findViewById(R.id.spTown);
        towns = db.getTownDAO().getAll();
        SDTown firstTown = new SDTown();
        firstTown.setTownId("0");
        firstTown.setTownName("Please Select");
        towns.add(0, firstTown);

        townAdapter = new TownAdapter(Form.this, R.layout.town_list_item, R.id.tvTownNames, towns);

        spTown.setAdapter(townAdapter);
        spTown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userSelect = false;
                refreshCustomer(towns.get(i).getTownId());
                refreshDepot(towns.get(i).getTownId());
                return;
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        spTown.setOnTouchListener(this);
        /*
        if(depots!=null && depots.size()>0)
            refreshTown(depots.get(0).getDepotCode(),"0");
*/
        spCustomer = (SearchableSpinner) findViewById(R.id.spCustomer);
        customers = db.getCustomerDAO().getAll();

        SDCustomer firstCustomer = new SDCustomer();
        firstCustomer.setCustCode("0");
        firstCustomer.setCustName("Please Select");
        customers.add(0, firstCustomer);
        customerAdapter = new CustomerAdapter(Form.this, R.layout.town_list_item, R.id.tvTownNames, customers);

        spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SDCustomer customer = (SDCustomer) spCustomer.getSelectedItem();
                if (customer != null) {
                    SDTownCustomer town = db.getTownCustomerDAO().getTownId(String.valueOf(customer.getCustCode()));
                    if (town != null) {
                        refreshDepot(town.getTownId());
                        SDDepot depot = (SDDepot) spDepot.getSelectedItem();
                        refreshTown(depot.getDepotCode(), town.getTownId());
                        userSelect = false;
                    }

                    return;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
        spCustomer.setAdapter(customerAdapter);
        /*
        if(customers!=null && customers.size()>0)
            refreshCustomer(customers.get(0).getCustCode());
*/
        //Work With dropdown
        spWorkWith = (Spinner) findViewById(R.id.spWorkWith);
        workWiths = db.getWorkWithDAO().getAll();

        workWithAdapter = new WorkWithAdapter(Form.this, R.layout.town_list_item, R.id.tvTownNames, workWiths);

        spWorkWith.setAdapter(workWithAdapter);

        spStatus = (Spinner) findViewById(R.id.spStatus);
        statuses = db.getStatusDAO().getAll();

        statusAdapter = new StatusAdapter(Form.this, R.layout.town_list_item, R.id.tvTownNames, statuses);

        spStatus.setAdapter(statusAdapter);
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spStatus.getSelectedItemId() == 0) {

                    // llParentLayout.addView(llOrderBooking);
                    llOrderBooking.setVisibility(View.VISIBLE);
                } else {
                    //llOrderBooking.removeAllViews();
                    llOrderBooking.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        spSKUGroup = (Spinner) findViewById(R.id.spSKUGroup);
        skuGroup = db.getSdskuGroupDAO().getAll();

        skuGroupAdapter = new SKUGroupAdapter(Form.this, R.layout.town_list_item, R.id.tvTownNames, skuGroup);

        spSKUGroup.setAdapter(skuGroupAdapter);

        productOrders = new ArrayList<>();
        ProductOrder temp = new ProductOrder();
        temp.setName(Codes.MESSAGE);
        temp.setQuantity(0);
        productOrders.add(temp);
        lvProducts = findViewById(R.id.lvProducts);
        productAdapter = new ProductAdapter(Form.this, R.layout.product_list, R.id.tvProductName, productOrders);
        lvProducts.setAdapter(productAdapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        etCommments = findViewById(R.id.etComments);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        etQuantity = findViewById(R.id.etQuantity);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAdd) {
            if (etQuantity.getText() == null || "".equals(etQuantity.getText().toString().trim())) {
                Toast.makeText(this, "Please enter quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            SDSKUGroup skuGroup = (SDSKUGroup) spSKUGroup.getSelectedItem();
            String addedProductName = skuGroup.getGrpName();
            String addedProductId = skuGroup.getGrpId();
            int productQuantity = Integer.valueOf(etQuantity.getText().toString());
            if (!updateList(addedProductName, productQuantity)) {
                ProductOrder productOrder = new ProductOrder();
                productOrder.setName(addedProductName);
                productOrder.setQuantity(productQuantity);
                productOrder.setProductId(addedProductId);
                productOrder.setId(Util.getNextID(this,Codes.ProductOrder));
                if (productOrders.size() == 1 && productOrders.get(0).getName().equals(Codes.MESSAGE)) {
                    productOrders = new ArrayList<>();
                }
                productOrders.add(productOrder);
            }
            productAdapter.setList(productOrders);
            productAdapter.notifyDataSetChanged();
        } else if (v.getId() == R.id.btnSubmit) {

            if (isValid()) {
                Orderr order = new Orderr();
                SDCustomer customer = new SDCustomer();
                customer = (SDCustomer) spCustomer.getSelectedItem();
                order.setCustCode(customer == null ? "" : customer.getCustCode());
                SDStatus status = new SDStatus();
                status = (SDStatus) spStatus.getSelectedItem();
                order.setStatusId(status == null ? 0 : status.getId());
                order.setVisitDate("");
                order.setComments(etCommments.getText().toString());

                GSSWorkWith workWith = new GSSWorkWith();
                workWith = (GSSWorkWith) spWorkWith.getSelectedItem();

                order.setWorkWith(workWith.getWorkWith());
                order.setLatLon(latLon);
                long orderId = Util.getNextID(this,Codes.ORDER);
                order.setId(orderId);
                db.getOrderDAO().insert(order);
                if (isValidProduct(productOrders)) {
                    for (ProductOrder productOrder : productOrders) {
                        productOrder.setOrderId(orderId);
                    }
                }

                db.getProductOderDAO().insertCompleteOrder(orderId, productOrders);
                Toast.makeText(this, "Order added. Please press SYNC whenever you get internet service.", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        userSelect = true;
        return false;
    }

    private boolean isValidProduct(List<ProductOrder> productOrders) {
        boolean isvalid = true;

        if (productOrders.size() == 1 && productOrders.get(0).getName().equals(Codes.MESSAGE)) {
            isvalid = false;
        }

        return isvalid;
    }

    public void refreshDepot(String townId) {
        SDDepot firstDepot = new SDDepot();
        firstDepot.setDepotCode(0);
        firstDepot.setDepotName("Please Select");
        if (townId.equals("0")) {

            depots = db.getDepotDAO().getAll();
            depots.add(0, firstDepot);
            depotAdapter.setList(depots);
            spDepot.setSelection(0);
        } else {
            List<SDTownDepot> townDepot = db.getTownDepotDAO().getAllByTownId(townId);
            depots = db.getDepotDAO().getSpecificDepot(Integer.valueOf(townDepot.get(0).getDepotId()));
            List<SDDepot> allDepot = new ArrayList<>();
            allDepot = db.getDepotDAO().getAll();
            allDepot.add(0, firstDepot);
            depotAdapter.setList(allDepot);
            int code = depots.get(0).getDepotCode();
            SDDepot depot = new SDDepot();
            int position = 0;

            for (int i = 0; i < allDepot.size(); i++) {
                depot = allDepot.get(i);
                if (depot.getDepotCode() == code) {
                    position = i;
                    break;
                }
            }
            spDepot.setSelection(position);
        }

        depotAdapter.notifyDataSetChanged();
    }

    public void refreshTown(int depotId, String townId) {
        SDTown firstTown = new SDTown();
        firstTown.setTownId("0");
        firstTown.setTownName("Please Select");
        if (depotId == 0) {
            towns = db.getTownDAO().getAll();
        } else {
            SDDepot depot = (SDDepot) spDepot.getSelectedItem();
            towns = db.getTownDAO().getSpecificDepotTowns(depot.getDepotCode());
        }
        towns.add(0, firstTown);
        townAdapter.setList(towns);
        SDTown town = db.getTownDAO().getSpecificTown(townId);

        if (town == null) {
            spTown.setSelection(0);
        } else {
            int position = 0;
            String townCode = town.getTownId();
            for (int i = 0; i < towns.size(); i++) {
                town = towns.get(i);
                if (town.getTownId().equals(townCode)) {
                    position = i;
                    break;
                }
            }
            spTown.setSelection(position);
        }

        townAdapter.notifyDataSetChanged();

    }

    public void refreshCustomer(String townId) {
        SDCustomer firstCustomer = new SDCustomer();
        firstCustomer.setCustName("Please Select");
        firstCustomer.setCustCode("0");

        if (townId.equals("0")) {
            customers = db.getCustomerDAO().getAll();

        } else {
            customers = db.getCustomerDAO().getSpecificCustomers(townId);
        }
        customers.add(0, firstCustomer);
        customerAdapter.setList(customers);
        // spCustomer.setSelection(0);
        customerAdapter.notifyDataSetChanged();
    }

    private boolean isValid() {
        boolean isValid = true;
        if(spCustomer.getSelectedItemId() == 0){
            isValid = false;
            Toast.makeText(this, "Please add customers in the list", Toast.LENGTH_SHORT).show();
        }
        if (spStatus.getSelectedItemId() == 0) {
            if (productOrders.get(0).getName() == Codes.MESSAGE) {
                isValid = false;
                Toast.makeText(this, "Please add orders in the list", Toast.LENGTH_SHORT).show();
            }
        }

        return isValid;
    }

    private boolean updateList(String name, int quantity) {
        boolean isExist = false;

        for (ProductOrder productOrder : productOrders) {
            if (name.equals(productOrder.getName())) {
                productOrder.setQuantity(productOrder.getQuantity() + quantity);
                isExist = true;
                break;
            }
        }

        return isExist;
    }

    private void runTestOnUiThread(Runnable sync) {
        sync.run();
    }

    @Override
    public void responseAlert(String response) {
        JSONObject responseObj = null;
        String status = "";
        String message = "";
        String data = "";

        try {
            responseObj = new JSONObject(response);
            status = responseObj.get("status").toString();
            message = responseObj.get("message").toString();
            // data=responseObj.get("data").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (Codes.ALL_OK.equals(status)) {
            db.getOrderDAO().nukeTable();
            db.getProductDAO().nukeTable();
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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