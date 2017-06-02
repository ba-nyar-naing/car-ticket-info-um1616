package com.um1616.carticketinfo.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.um1616.carticketinfo.R;

import java.util.ArrayList;

/**
 * Created by Ba Nyar Naing on 25-Oct-16.
 */

public class DialogCallAdapter extends RecyclerView.Adapter<DialogCallAdapter.MyViewHolder> {

    private ArrayList<String> mPhone;

    public DialogCallAdapter(ArrayList<String> items) {
        mPhone = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_call_list_row, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        final String phone = mPhone.get(i).trim();
        viewHolder.mTextViewGate.setText(phone);

        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.i("Phone : ", phone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phone));
                view.getContext().startActivity(callIntent);
//                Snackbar.make((LinearLayout) view.findViewById(R.id.dialog_call_list_item_linear_layout), phone, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhone.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewGate;
        private final RelativeLayout mLinearLayout;

        MyViewHolder(View v) {
            super(v);
            mTextViewGate = (TextView) v.findViewById(R.id.dialog_call_phone_list_item);
            mLinearLayout = (RelativeLayout) v.findViewById(R.id.dialog_call_list_item_linear_layout);
        }
    }

}
