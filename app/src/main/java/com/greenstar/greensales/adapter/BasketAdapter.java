package com.greenstar.greensales.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.greenstar.greensales.R;
import com.greenstar.greensales.controller.Form;
import com.greenstar.greensales.controller.ProductOrderDeleteListener;
import com.greenstar.greensales.dal.OrderDetail;
import com.greenstar.greensales.dal.ProductDetail;
import com.greenstar.greensales.db.AppDatabase;
import com.greenstar.greensales.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class BasketAdapter extends ArrayAdapter<OrderDetail> implements View.OnClickListener {
    private Activity mActivity;
    private List<OrderDetail> list = new ArrayList<>();
    AppDatabase db =null;
    ProductOrderDeleteListener deleteOrder = null;
    public BasketAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<OrderDetail> list, ProductOrderDeleteListener deleteOrder) {
        super(activity, resource, textViewResourceId, list);
        db = AppDatabase.getAppDatabase(activity);
        mActivity = activity;
        this.deleteOrder = deleteOrder;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public OrderDetail getItem(int position) {
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
        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.basket_row, null);
        }
        LinearLayout llInnerBasket = v.findViewById(R.id.llInnerBasket);

        TextView tvCustomerName = (TextView) v.findViewById(R.id.tvCustomerName);
        TextView tvCustomerCode = (TextView) v.findViewById(R.id.tvCustomerCode);
        TextView tvStatus = (TextView) v.findViewById(R.id.tvStatus);
        ImageButton btnDelete = v.findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(this);

        ListView lvBasketProduct = v.findViewById(R.id.lvProductList);

        OrderDetail i = list.get(position);

        BasketProductAdapter basketProductAdapter = new BasketProductAdapter(mActivity, R.layout.product_list, R.id.tvProductName, i.getProductDetails()==null?new ArrayList<ProductDetail>(): i.getProductDetails() );
        lvBasketProduct.setAdapter(basketProductAdapter);

        Util.getListViewSize(lvBasketProduct);

        tvCustomerName.setText("Customer : " +i.getCustName());
        tvCustomerCode.setText("Customer Code : "+i.getCustCode());
        tvStatus.setText("Status : "    + i.getStatus());

        btnDelete.setTag(i.getOrderId());
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btnDelete){
            Object obj =  v.getTag()==null?"0":v.getTag();
            int orderId = (int)obj;
            if(orderId!=0){
                deleteOrder.deleteOrder(orderId);
            }else{
                Toast.makeText(mActivity, "Something went wrong while deleting Order",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
