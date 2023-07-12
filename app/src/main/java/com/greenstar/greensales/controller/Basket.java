package com.greenstar.greensales.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.adapter.BasketAdapter;
import com.greenstar.greensales.dal.CompleteOrder;
import com.greenstar.greensales.dal.OrderDetail;
import com.greenstar.greensales.dal.ProductDetail;
import com.greenstar.greensales.db.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class Basket  extends AppCompatActivity implements ProductOrderDeleteListener{
    ListView lvBasket;
    BasketAdapter basketAdapter;
    AppDatabase db =null;
    List<CompleteOrder> completeOrders = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getAppDatabase(this);

        setContentView(R.layout.basket_activity);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvBasket = findViewById(R.id.lvBasket);

        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails = getData();
        if(orderDetails.size()>0) {
            basketAdapter = new BasketAdapter(Basket.this, R.layout.basket_row, R.id.tvCustomerName, orderDetails, this);

            lvBasket.setAdapter(basketAdapter);
        }else{
            Toast.makeText(this,"There is no pending offer.", Toast.LENGTH_SHORT).show();
        }
        getSupportActionBar().setTitle("Visits");
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
    public void deleteOrder(int orderId) {
        db.getOrderDAO().deleteOrder(orderId);
        db.getProductOderDAO().deleteProductOrder(orderId);
        basketAdapter = new BasketAdapter(Basket.this,R.layout.basket_row, R.id.tvCustomerName, getData(), this );
        lvBasket.setAdapter(basketAdapter);
        basketAdapter.notifyDataSetChanged();
        Toast.makeText(this,"Order deleted!" , Toast.LENGTH_SHORT).show();
    }

    private List<OrderDetail> getData(){
        List<OrderDetail> orderDetails = new ArrayList<>();
        ProductDetail productDetail = new ProductDetail();
        List<ProductDetail> productDetails = new ArrayList<>();
        OrderDetail orderDetail = null;
        int prev = 0;
        List<CompleteOrder> completeOrders = db.getProductOderDAO().loadCompleteOrder();
        for(CompleteOrder completeOrder: completeOrders){
            int orderId = completeOrder.getOrderId();
            if(orderId!=prev){
                productDetails = new ArrayList<>();
                prev = orderId;
                if(orderDetail!=null)
                    orderDetails.add(orderDetail);
                orderDetail = new OrderDetail();
                orderDetail.setOrderId(completeOrder.getOrderId());
                orderDetail.setCustCode(completeOrder.getCustCode());
                orderDetail.setCustName(completeOrder.getCustName());
                orderDetail.setStatus(completeOrder.getStatus());
                orderDetail.setVisitDate(completeOrder.getVisitDate());

                if(completeOrder.getProductName()!=null){
                    productDetail = new ProductDetail();
                    productDetail.setName(completeOrder.getProductName());
                    productDetail.setQuantity(completeOrder.getQuantity());

                    productDetails.add(productDetail);

                    orderDetail.setProductDetails(productDetails);
                }
            }else{
                productDetail = new ProductDetail();
                productDetail.setName(completeOrder.getProductName());
                productDetail.setQuantity(completeOrder.getQuantity());
                productDetails.add(productDetail);
                orderDetail.setProductDetails(productDetails);
            }
        }
        if(orderDetail!=null)
            orderDetails.add(orderDetail);

        return orderDetails ;
    }
}
