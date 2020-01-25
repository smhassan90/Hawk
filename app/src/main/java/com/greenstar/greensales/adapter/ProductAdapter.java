package com.greenstar.greensales.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.greenstar.greensales.R;
import com.greenstar.greensales.controller.Codes;
import com.greenstar.greensales.controller.Form;
import com.greenstar.greensales.model.ProductOrder;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter<ProductOrder> {
    private Activity mActivity;
    private List<ProductOrder> list = new ArrayList<>();
    public ProductAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<ProductOrder> list) {
        super(activity, resource, textViewResourceId, list);
        mActivity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public ProductOrder getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(list.get(position).getName().equals(Codes.MESSAGE)){
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.message, null);
            TextView tvMessage = v.findViewById(R.id.tvMessage);
            tvMessage.setText(Codes.MESSAGE);
        }else {
                LayoutInflater inflater = mActivity.getLayoutInflater();
                v = inflater.inflate(R.layout.product_list, null);

            // inflate other items here :
            ImageButton deleteButton = (ImageButton) v.findViewById(R.id.btnDelete);
            deleteButton.setTag(position);

            deleteButton.setOnClickListener(
                    new Button.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer index = (Integer) v.getTag();
                            list.remove(index.intValue());
                            if(list.size()==0){
                                ProductOrder productOrder = new ProductOrder();
                                productOrder.setName(Codes.MESSAGE);
                                list.add(productOrder);
                            }
                            notifyDataSetChanged();
                        }
                    }
            );
            TextView tvProductName = (TextView) v.findViewById(R.id.tvProductName);
            TextView tvProductQuantity = (TextView) v.findViewById(R.id.tvProductQuantity);
            tvProductName.setText("Product Name : "+list.get(position).getName());
            tvProductQuantity.setText("Quantity : " +String.valueOf(list.get(position).getQuantity()));
        }
        return v;
    }

    public List<ProductOrder> getList() {
        return list;
    }

    public void setList(List<ProductOrder> list) {
        this.list = list;
    }
}
