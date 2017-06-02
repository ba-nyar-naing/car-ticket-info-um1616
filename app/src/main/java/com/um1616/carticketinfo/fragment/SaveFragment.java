package com.um1616.carticketinfo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.um1616.carticketinfo.R;
import com.um1616.carticketinfo.adapter.RecyclerViewAdapter;
import com.um1616.carticketinfo.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ba Nyar Naing on 31-Oct-16.
 */

public class SaveFragment extends Fragment {

    FastScrollRecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    public SaveFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
//        final List<String> gates = Arrays.asList(getResources().getStringArray(R.array.gates));
//        final List<String> routes = Arrays.asList(getResources().getStringArray(R.array.routes));

        List<String> names = new ArrayList<>();
        List<String> routes = new ArrayList<>();


        DatabaseHelper mdbHelper = new DatabaseHelper(getActivity());
        names = mdbHelper.getAllSavedGateName();
        routes = mdbHelper.getAllSavedRoute();

        View v = inflater.inflate(R.layout.fragment_save, container, false);

        recyclerView = (FastScrollRecyclerView) v.findViewById(R.id.fast_scroller_save);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new RecyclerViewAdapter(names, routes);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        return v;
    }
}
