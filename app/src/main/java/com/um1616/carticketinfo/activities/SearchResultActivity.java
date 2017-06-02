package com.um1616.carticketinfo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.um1616.carticketinfo.R;
import com.um1616.carticketinfo.adapter.SearchResultRVAdapter;
import com.um1616.carticketinfo.database.DatabaseHelper;
import com.um1616.carticketinfo.model.Gate;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Ba Nyar Naing on 23-Oct-16.
 */

public class SearchResultActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private List<Gate> lstGate, foundGates = new ArrayList<>(), notFoundGates = new ArrayList<>();
    private int id;
    private String dcity, acity, route;
    private FastScrollRecyclerView recyclerView;
    private SearchResultRVAdapter searchResultRVAdapter;
    private boolean found = false;
    private ArrayList<ArrayList> alllist = new ArrayList<>();
    private List<Gate> getlistfrom = new ArrayList<>();
    private List<Gate> getlistto = new ArrayList<>();
    private List<Gate> getfinallist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        dcity = bundle.getString("dcity");
        acity = bundle.getString("acity");

        databaseHelper = new DatabaseHelper(this);
        lstGate = databaseHelper.getAllGates();

        Log.i("SearchResultActivity : ", "Searching...");

        getAllList();
        searchFrom(dcity);
        searchTo(acity);
        notFoundGates = getlistfrom;


//        MaterialDialog dialogNotFound = new MaterialDialog.Builder(this)
//                .content(R.string.notFound).positiveText(R.string.dismiss).show();

        if (searchFound()) {
            showRV(foundGates);
            setToolbar(dcity + " - " + acity);
        } else {
            showRV(notFoundGates);
            setToolbar(dcity);
            new MaterialDialog.Builder(this).title(R.string.titleNotFound).content(R.string.notFound).positiveText("Dismiss").show();
        }

    }

    private void showRV(List<Gate> gates) {
        setContentView(R.layout.rc_activity_search_result_list_view);

        recyclerView = (FastScrollRecyclerView) findViewById(R.id.search_result_fast_scroller);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new SearchResultRVAdapter(gates));

    }

    private boolean searchFound() {
        found = false;
        for (int i = 0; i < getlistfrom.size(); i++) {
            for (int k = 0; k < getlistto.size(); k++) {

                if (getlistfrom.get(i).getRoute().equals(getlistto.get(k).getRoute())) {
//                    Log.i("Same toandfrom1: ", getlistfrom.get(i).getRoute());
//                    Log.i("Same toandfrom2 : ", getlistto.get(k).getRoute());
                    foundGates.add(getlistfrom.get(i));
                    found = true;
                    break;
                }
            }

        }
        return found;
    }

    private void searchFrom(String dcity) {
        for (int i = 0; i < alllist.size(); i++) {
            for (int j = 0; j < alllist.get(i).size(); j++) {
                if (dcity.equals(alllist.get(i).get(j))) {
                    // Log.i("Same from : ", lstGate.get(i).getRoute());
                    getlistfrom.add(lstGate.get(i));
                    break;
                }
            }
        }
    }

    private void searchTo(String acity) {
        for (int i = 0; i < alllist.size(); i++) {
            for (int j = 0; j < alllist.get(i).size(); j++) {
                if (acity.equals(alllist.get(i).get(j))) {
                    //    Log.i("Same to : ", lstGate.get(i).getRoute());
                    getlistto.add(lstGate.get(i));

                    break;
                }
            }
        }
    }

    private void getAllList() {
        for (int i = 0; i < lstGate.size(); i++) {
            route = lstGate.get(i).getRoute();
            //   Log.i("Gates ", route);
            StringTokenizer st = new StringTokenizer(route, "-");
            ArrayList<String> namelist = new ArrayList<>();
            while (st.hasMoreTokens()) {
                String name = st.nextToken();
                namelist.add(name);
                //     Log.i("Name ", name);
            }
            alllist.add(namelist);
        }
    }

    private void setToolbar(String name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(name);
    }

}
