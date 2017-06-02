package com.um1616.carticketinfo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.um1616.carticketinfo.R;
import com.um1616.carticketinfo.adapter.RecyclerViewCardAdapter;
import com.um1616.carticketinfo.adapter.RecyclerViewPriceAdapter;
import com.um1616.carticketinfo.database.DatabaseHelper;
import com.um1616.carticketinfo.model.Gate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Ba Nyar Naing on 23-Oct-16.
 */

public class SelectedGateActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Gate gate;
    int id, value;
    String name, route, contact, contactTV = "", price, idName;
    TextView txtRoute, txtContact;
    FloatingActionButton fabSave;
    RecyclerView recyclerViewAddressPhone;
    RecyclerView recyclerViewRoutePrice;
    RecyclerViewCardAdapter recyclerViewCardAdapter;
    RecyclerViewPriceAdapter recyclerViewPriceAdapter;
    ArrayList<String> addressAndPhone = new ArrayList<String>();
    ArrayList<String> routeAndPrice = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_gate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();

        databaseHelper = new DatabaseHelper(this);
        id = bundle.getInt("id");
        if (id == 500) {
            idName = bundle.getString("name");
            gate = databaseHelper.getGateByName(idName);
            id = gate.getId();
        } else {
            gate = databaseHelper.getGateById(id);
        }

        value = databaseHelper.savedState(id);
        fabSave = (FloatingActionButton) findViewById(R.id.fab_save);
        if (value == 1)
            fabSave.setImageResource(R.drawable.ic_fab_saved);
        else
            fabSave.setImageResource(R.drawable.ic_fab_unsaved);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SelectdGate : ", String.valueOf(id));
                if (value == 0) {
                    value = 1;
                    fabSave.setImageResource(R.drawable.ic_fab_saved);
                    Snackbar.make(v,"Saved",Snackbar.LENGTH_SHORT).show();
                } else {
                    value = 0;
                    fabSave.setImageResource(R.drawable.ic_fab_unsaved);
                    Snackbar.make(v,"Unsaved",Snackbar.LENGTH_SHORT).show();
                }
                databaseHelper.savedGate(id, value);
            }
        });


        name = gate.getName();
        route = gate.getRoute();
        contact = gate.getContact();
        price = gate.getPrice();
        setTitle(gate.getName() + "ဂိတ္");
        splitContact(contact);
        splitPrice(price);

        txtRoute = (TextView) findViewById(R.id.selected_gate_route);
        txtContact = (TextView) findViewById(R.id.selected_gate_contact);

        txtRoute.setText(route);

        recyclerViewAddressPhone = (RecyclerView) findViewById(R.id.gate_contact_recyclerview);
        recyclerViewCardAdapter = new RecyclerViewCardAdapter(addressAndPhone);
        recyclerViewAddressPhone.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewAddressPhone.setLayoutManager(mLayoutManager);
        recyclerViewAddressPhone.setAdapter(recyclerViewCardAdapter);

        recyclerViewRoutePrice = (RecyclerView) findViewById(R.id.selected_gate_route_price_recycler_view);
        recyclerViewPriceAdapter = new RecyclerViewPriceAdapter(routeAndPrice);
        recyclerViewRoutePrice.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerViewRoutePrice.setLayoutManager(mLayoutManager1);
        recyclerViewRoutePrice.setAdapter(recyclerViewPriceAdapter);


    }


    private void splitContact(String text) {
        text = text.substring(1, text.length() - 1);
        addressAndPhone.clear();
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(text);

        while (m.find()) {
            // Log.i("Address : ", m.group(1));
            contactTV += m.group(1);
            addressAndPhone.add(m.group(1));
        }

    }


    private void splitPrice(String text) {
        text = text.substring(1, text.length() - 1);
        routeAndPrice.clear();
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(text);

        while (m.find()) {
            //  Log.i("Price : ", m.group(1));
            routeAndPrice.add(m.group(1));
        }
    }


}
