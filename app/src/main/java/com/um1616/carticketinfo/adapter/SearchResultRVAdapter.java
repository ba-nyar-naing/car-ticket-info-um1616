package com.um1616.carticketinfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.um1616.carticketinfo.R;
import com.um1616.carticketinfo.activities.SelectedGateActivity;
import com.um1616.carticketinfo.model.Gate;

import java.util.List;

/**
 * Created by Ba Nyar Naing on 23-Oct-16.
 */

public class SearchResultRVAdapter extends RecyclerView.Adapter<SearchResultRVAdapter.MyViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private List<Gate> mGate;

    public SearchResultRVAdapter(List<Gate> items) {
        mGate = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_activity_search_result_list_row, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        Gate g = mGate.get(i);
        final String gates = g.getName();
        final String routes = g.getRoute();
        final int position = g.getId();
        viewHolder.mTextViewGate.setText(gates);
        viewHolder.mTextViewRoute.setText(routes);

        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make((LinearLayout) view.findViewById(R.id.list_row_search_result_gate), position + " " + gates + " in Snackbar " + routes, Snackbar.LENGTH_LONG).show();
                Context context = view.getContext();
                Intent i = new Intent(context, SelectedGateActivity.class);
                i.putExtra("id", position);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGate.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        String firstChar = String.valueOf(mGate.get(position).getName().charAt(0));
        String secondChar = String.valueOf(mGate.get(position).getName().charAt(1));
        if (firstChar.equals("ေ") || firstChar.equals("ၾ") || firstChar.equals("ႀ") || firstChar.equals("ၿ") || firstChar.equals("ျ")) {
            firstChar = String.valueOf(mGate.get(position).getName().charAt(1));
            if (secondChar.equals("႐"))
                firstChar = String.valueOf("ရ");
            // Log.i("First Cha : ", String.valueOf(position) + " " + firstChar);
        } else if (firstChar.equals("႐")) {
            firstChar = String.valueOf("ရ");
        }

        return firstChar;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewGate, mTextViewRoute;
        private final LinearLayout mLinearLayout;

        MyViewHolder(View v) {
            super(v);
            mTextViewGate = (TextView) v.findViewById(R.id.list_row_search_result_gate_name);
            mTextViewRoute = (TextView) v.findViewById(R.id.list_row_search_result_gate_route);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.list_row_search_result_gate);
        }
    }

}
