package com.greenstar.greensales.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenstar.greensales.R;
import com.greenstar.greensales.model.SDCustomer;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends ArrayAdapter<SDCustomer> implements TextWatcher {
    private Activity mActivity;
    private List<SDCustomer> list = new ArrayList<>();
    private List<SDCustomer> backupList = new ArrayList<>();
    public CustomerAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<SDCustomer> list) {
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
    public SDCustomer getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.sp_customer_dialog, null);
           /*
            View search=null;
            LinearLayout llSearchParent = null;

            if(position==0){
                search = inflater.inflate(R.layout.search_field, null);
                llSearchParent = v.findViewById(R.id.llSearchParent);
                llSearchParent.addView(search);
                EditText etSearch = search.findViewById(R.id.etSearch);
                etSearch.addTextChangedListener(this);
            }
            */
        }
        TextView tvName = (TextView) v.findViewById(R.id.tvName);
        TextView tvAddress = (TextView) v.findViewById(R.id.tvAddress);

        if(list.get(position).getCustAdd()==null){
            tvName.setText("Please Select");
        }else{
            tvName.setText(list.get(position).getCustName()+" - "+list.get(position).getCustCode());
            tvAddress.setText(list.get(position).getCustAdd());
        }
        return v;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.dropdown_view, null);
        }

        TextView lbl = (TextView) v.findViewById(R.id.tvDDName);
        String code = list.get(position).getCustCode();
        if(code.equals("") || code.equals("0")){
            lbl.setText("Select Customer");
        }else{
            lbl.setText(list.get(position).getCustName()+" - "+list.get(position).getCustCode());
        }

        return v;
    }

    public List<SDCustomer> getList() {
        return list;
    }

    public void setList(List<SDCustomer> list) {
        this.list = list;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        filter(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    private void filter(String filter){

        //list = new ArrayList<>();
        list = getList();
        if("".equals(filter)){
            list = list;
            return;
        }

        for(SDCustomer customer : list){
            if(customer.getCustName().contains(filter) ||
                    customer.getCustAdd().contains(filter) ||
                    customer.getCustCode().contains(filter)){
                backupList.add(customer);
            }
        }
        this.setList(backupList);

    }
}
