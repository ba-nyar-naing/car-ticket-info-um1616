package com.um1616.carticketinfo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.um1616.carticketinfo.R;
import com.um1616.carticketinfo.activities.SearchResultActivity;
import com.um1616.carticketinfo.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ba Nyar Naing on 22-Oct-16.
 */

public class ChooseFragment extends Fragment {

    private LinearLayout linearLayoutDeparture, linearLayoutArrival;
    private TextView textViewDeparture, textViewArrival;
    private String departureCity, arrivalCity;
    private LinearLayout btnGo;
    private List<String> cities = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_choose, container, false);

        DatabaseHelper mdbHelper = new DatabaseHelper(getActivity());
        cities = mdbHelper.getAllCities();

        textViewDeparture = (TextView) view.findViewById(R.id.tv_departure_city);
        textViewArrival = (TextView) view.findViewById(R.id.tv_arrival_city);
        linearLayoutDeparture = (LinearLayout) view.findViewById(R.id.ll_departure);
        linearLayoutArrival = (LinearLayout) view.findViewById(R.id.ll_arrival);

        linearLayoutDeparture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).items(cities)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                departureCity = text.toString();
                                textViewDeparture.setText(departureCity);
                            }
                        })
                        .show();
            }
        });

        linearLayoutArrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext()).items(cities)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                arrivalCity = text.toString();
                                textViewArrival.setText(arrivalCity);
                            }
                        })
                        .show();
            }
        });

        btnGo = (LinearLayout) view.findViewById(R.id.btnGO);
        btnGo.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (departureCity == null || arrivalCity == null) {
                                             Snackbar.make((LinearLayout) view.findViewById(R.id.ll), "Please choose city.", Snackbar.LENGTH_LONG).show();
                                         } else if (!departureCity.equals(arrivalCity)) {
                                             Context context = view.getContext();
                                             Intent i = new Intent(context, SearchResultActivity.class);
                                             i.putExtra("dcity", departureCity);
                                             i.putExtra("acity", arrivalCity);
                                             context.startActivity(i);

                                         } else {
                                             Snackbar.make((LinearLayout) view.findViewById(R.id.ll), "Please choose different cities.", Snackbar.LENGTH_LONG).show();
                                         }
                                     }
                                 }
        );
        return view;
    }
}
