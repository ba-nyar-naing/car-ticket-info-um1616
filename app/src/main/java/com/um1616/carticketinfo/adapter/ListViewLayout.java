package com.um1616.carticketinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.um1616.carticketinfo.R;

/**
 * Created by UCSM on 10/26/2016.
 */

public class ListViewLayout extends ArrayAdapter<String> {
    public ListViewLayout(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(String object) {
        super.add(object);
    }

    @Override
    public String getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dialog_call_list_item, null);
        }

        String contact = (String) getItem(position);

        TextView txtName = (TextView) convertView.findViewById(R.id.dialog_call_phone_list_item);

        txtName.setText(contact);

        return convertView;
    }
}
