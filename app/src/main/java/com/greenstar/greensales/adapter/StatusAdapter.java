package com.greenstar.greensales.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenstar.greensales.R;
import com.greenstar.greensales.model.SDStatus;

import java.util.ArrayList;
import java.util.List;

public class StatusAdapter extends ArrayAdapter<SDStatus> {
    private Activity mActivity;
    private List<SDStatus> list = new ArrayList<>();
    public StatusAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<SDStatus> list) {
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
    public SDStatus getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = mActivity.getLayoutInflater();
            v = inflater.inflate(R.layout.town_list_item, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.tvTownNames);
        lbl.setText(list.get(position).getStatus());
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
        lbl.setText(list.get(position).getStatus());
        return v;
    }
}
