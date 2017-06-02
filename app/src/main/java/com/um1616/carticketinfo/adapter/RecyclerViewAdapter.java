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

import java.util.List;

/**
 * Created by Ba Nyar Naing on 22-Oct-16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private List<String> mGates, mRoutes;

    public RecyclerViewAdapter(List<String> items1, List<String> items2) {
        mGates = items1;
        mRoutes = items2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rc_all_gates_list_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final String gates = mGates.get(i);
        final String routes = mRoutes.get(i);
        final int position = (i + 1);
        viewHolder.mTextViewGate.setText(gates);
        viewHolder.mTextViewRoute.setText(routes);

        viewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Snackbar.make((LinearLayout) view.findViewById(R.id.list_row_gate), position + " " + gates + " in Snackbar " + routes, Snackbar.LENGTH_LONG).show();
                Context context = view.getContext();
                Intent i = new Intent(context, SelectedGateActivity.class);
                i.putExtra("name", gates);
                i.putExtra("id", 500);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGates.size();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        String firstChar = String.valueOf(mGates.get(position).charAt(0));
        String secondChar = String.valueOf(mGates.get(position).charAt(1));
        if (firstChar.equals("ေ") || firstChar.equals("ၾ") || firstChar.equals("ႀ") || firstChar.equals("ၿ") || firstChar.equals("ျ")) {
            firstChar = String.valueOf(mGates.get(position).charAt(1));
            if (secondChar.equals("႐"))
                firstChar = String.valueOf("ရ");
            // Log.i("First Cha : ", String.valueOf(position) + " " + firstChar);
        } else if (firstChar.equals("႐")) {
            firstChar = String.valueOf("ရ");
        }

        return firstChar;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewGate, mTextViewRoute;
        private final LinearLayout mLinearLayout;

        ViewHolder(View v) {
            super(v);
            mTextViewGate = (TextView) v.findViewById(R.id.list_row_gate_name);
            mTextViewRoute = (TextView) v.findViewById(R.id.list_row_gate_route);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.list_row_gate);
        }
    }

}
