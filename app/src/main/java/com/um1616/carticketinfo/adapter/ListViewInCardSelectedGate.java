package com.um1616.carticketinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.um1616.carticketinfo.R;

import java.util.ArrayList;

/**
 * Created by UCSM on 10/26/2016.
 */

public class ListViewInCardSelectedGate extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;


    public ListViewInCardSelectedGate(Context context, ArrayList<String> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dialog_call_list_item, null);
        }

        String contact = (String) getItem(position);
        TextView txtName = (TextView) convertView.findViewById(R.id.dialog_call_phone_list_item);
        txtName.setText(contact);

        return convertView;
    }
}