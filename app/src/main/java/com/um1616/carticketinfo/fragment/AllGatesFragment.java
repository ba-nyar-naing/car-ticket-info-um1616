package com.um1616.carticketinfo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.um1616.carticketinfo.R;
import com.um1616.carticketinfo.adapter.RecyclerViewAdapter;
import com.um1616.carticketinfo.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ba Nyar Naing on 22-Oct-16.
 */

public class AllGatesFragment extends Fragment {

    FastScrollRecyclerView recyclerView;
    List<String> gates = new ArrayList<>();
    List<String> routes = new ArrayList<>();

    public AllGatesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        DatabaseHelper mdbHelper = new DatabaseHelper(getActivity());
        gates = mdbHelper.getAllGateName();
        routes = mdbHelper.getAllRoute();

        View v = inflater.inflate(R.layout.rc_fragment_all_gates_list_view, container, false);

        recyclerView = (FastScrollRecyclerView) v.findViewById(R.id.fast_scroller);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(gates, routes));

        SearchView search = (SearchView) v.findViewById(R.id.search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                List<String> filteredListGates = new ArrayList<>();
                List<String> filteredListRoutes = new ArrayList<>();

                for (int i = 0; i < gates.size(); i++) {
                    final String gate = gates.get(i);
                    final String route = routes.get(i);
                    if (gate.contains(query) || route.contains(query)) {
                        //Log.i("Text : ",text);
                        filteredListGates.add(gates.get(i));
                        filteredListRoutes.add(routes.get(i));
                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(filteredListGates, filteredListRoutes);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerViewAdapter.notifyDataSetChanged();  // data set changed
                return true;
            }
        });
        return v;
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        //   super.onCreateOptionsMenu(menu, inflater);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_search) {
//            Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
//        }
//        return true;
//
//    }
}

