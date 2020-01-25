package com.greenstar.greensales.adapter;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.greenstar.greensales.R;
import com.greenstar.greensales.model.SDDepot;
import com.greenstar.greensales.model.SDTown;
import com.greenstar.greensales.utils.TypefaceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DepotAdapter extends ArrayAdapter<SDDepot> {
    private Activity mActivity;
    private List<SDDepot> list = new ArrayList<>();
    public DepotAdapter(@NonNull Activity activity, int resource, int textViewResourceId, List<SDDepot> list) {
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
    public SDDepot getItem(int position) {
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
        SDDepot temp = list.get(position);

        lbl.setText(temp.getDepotName());
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
        lbl.setText(list.get(position).getDepotName());
        return v;
    }

    public List<SDDepot> getList() {
        return list;
    }

    public void setList(List<SDDepot> list) {
        this.list = list;
    }
}
