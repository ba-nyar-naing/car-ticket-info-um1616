package com.um1616.carticketinfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.um1616.carticketinfo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by UCSM on 10/26/2016.
 */

public class RecyclerViewCardAdapter extends RecyclerView.Adapter<RecyclerViewCardAdapter.ViewHolder> {

    private List<String> mContacts;
    private String add = " ", ph = " ";

    public RecyclerViewCardAdapter(List<String> items) {
        mContacts = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.address_call_recycler_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String contact = mContacts.get(i);
        splitAddPh(contact);
        viewHolder.mTextViewAddress.setText(add);
        viewHolder.mTextViewPhone.setText(ph);

        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make((LinearLayout) view.findViewById(R.id.dialog_call_list_item_linear_layout), contact, Snackbar.LENGTH_LONG).show();
                //Log.i("Adapter : ", contact);
                splitAddressPhone(contact, view.getContext());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    private void splitAddPh(String text) {
        StringTokenizer st = new StringTokenizer(text, "Ph:");
        add = st.nextToken();
        ph = "ဖုန္း -" + st.nextToken();
    }

    private void splitAddressPhone(String text, Context context) {
        String addressLine = " ", allphone = " ";
        ArrayList<String> phone = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(text, "Ph:");
        addressLine = st.nextToken();
        allphone = st.nextToken();
        st = new StringTokenizer(allphone, ",");
        while (st.hasMoreElements()) {
            phone.add(st.nextToken());
        }
        new MaterialDialog.Builder(context)
                .title(addressLine)
                .adapter(new DialogCallAdapter(phone), null)
                .negativeText("Cancel")
                .show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewAddress, mTextViewPhone;
        private final RelativeLayout mLinearLayout;

        ViewHolder(View v) {
            super(v);
            mTextViewAddress = (TextView) v.findViewById(R.id.address_list_item);
            mTextViewPhone = (TextView) v.findViewById(R.id.dialog_call_phone_list_item);
            mLinearLayout = (RelativeLayout) v.findViewById(R.id.dialog_call_list_item_linear_layout);
        }
    }

}
