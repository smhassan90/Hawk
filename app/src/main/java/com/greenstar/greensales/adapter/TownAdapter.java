package com.greenstar.greensales.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenstar.greensales.R;
import com.greenstar.greensales.model.SDTown;

import java.util.ArrayList;
import java.util.List;

public class TownAdapter extends ArrayAdapter<SDTown> {
    private Activity mActivity;
    private List<SDTown> list = new ArrayList<>();
    public TownAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<SDTown> list) {
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
    public SDTown getItem(int position) {
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
        lbl.setText(list.get(position).getTownName());
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
        lbl.setText(list.get(position).getTownName());
        return v;
    }

    public List<SDTown> getList() {
        return list;
    }

    public void setList(List<SDTown> list) {
        this.list = list;
    }
}
