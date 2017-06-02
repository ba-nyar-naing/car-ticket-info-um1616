package com.um1616.carticketinfo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.um1616.carticketinfo.R;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by UCSM on 10/28/2016.
 */

public class RecyclerViewPriceAdapter extends RecyclerView.Adapter<RecyclerViewPriceAdapter.ViewHolder> {

    private List<String> mPrice;
    private String route = " ", price = " ";

    public RecyclerViewPriceAdapter(List<String> items) {
        mPrice = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.route_price_list_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String routenprice = mPrice.get(i);
        splitRoutePrice(routenprice);
        viewHolder.mTextViewRoute.setText(route);
        viewHolder.mTextViewPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return mPrice.size();
    }

    private void splitRoutePrice(String text) {
        StringTokenizer st = new StringTokenizer(text, "(");
        route = st.nextToken();
        price = st.nextToken();
        price = price.substring(0, price.length() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewRoute, mTextViewPrice;

        ViewHolder(View v) {
            super(v);
            mTextViewRoute = (TextView) v.findViewById(R.id.route_price_list_row_route);
            mTextViewPrice = (TextView) v.findViewById(R.id.route_price_list_row_price);
        }
    }


}
