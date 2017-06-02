package com.um1616.carticketinfo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.um1616.carticketinfo.R;
import com.um1616.carticketinfo.database.DatabaseHelper;
import com.um1616.carticketinfo.fragment.AboutFragment;
import com.um1616.carticketinfo.fragment.AllGatesFragment;
import com.um1616.carticketinfo.fragment.ChooseFragment;
import com.um1616.carticketinfo.fragment.SaveFragment;
import com.um1616.carticketinfo.fragment.SettingsFragment;
import com.um1616.carticketinfo.model.Gate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Ba Nyar Naing on 22-Oct-16.
 */

public class MainActivity extends AppCompatActivity {
    private static int defaultID = R.id.nav_choose;
    private DrawerLayout mDrawerLayout;
    private DatabaseHelper mdbHelper;
    private List<Gate> lstGate;
    private Class defaultFragment = ChooseFragment.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabase();
        lstGate = mdbHelper.getAllGates();
        // Log.i("DB : ", mdbHelper.getGateById(23).getName());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(navigationView);
        selectDrawerItem(defaultID);
        setDrawerTitle(defaultID);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();
    }

    private void setDrawerTitle(int id) {
        if (id == R.id.nav_choose)
            setTitle("Search Routes");
        else if (id == R.id.nav_all_gates)
            setTitle("All Gates");
        else if (id == R.id.nav_settings)
            setTitle("Settings");
        else if (id == R.id.nav_save)
            setTitle("Saved Gates");
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem.getItemId());
                mDrawerLayout.closeDrawers();
                menuItem.setChecked(true);
                if (menuItem.getItemId() != R.id.nav_exit)
                    setTitle(menuItem.getTitle());
                return true;
            }
        });
    }

    private void selectDrawerItem(int id) {
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_choose:
                defaultID = R.id.nav_choose;
                defaultFragment = ChooseFragment.class;
                break;
            case R.id.nav_all_gates:
                defaultID = R.id.nav_all_gates;
                defaultFragment = AllGatesFragment.class;
                break;
            case R.id.nav_save:
                defaultID = R.id.nav_save;
                defaultFragment = SaveFragment.class;
                break;
            case R.id.nav_settings:
                defaultID = R.id.nav_settings;
                defaultFragment = SettingsFragment.class;
                break;
            case R.id.nav_about:
                defaultID = R.id.nav_about;
                defaultFragment = AboutFragment.class;
                break;
            case R.id.nav_exit:
                checkExit();
        }
        try {
            fragment = (Fragment) defaultFragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
    }


    private void checkExit() {
        new MaterialDialog.Builder(this)
                .title("Exit")
                .content("Are you sure to exit?")
                .positiveText("Exit")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        System.exit(0);
                    }
                })
                .negativeText("Cancel")
                .show();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
            isFinishing();
        } else {
            checkExit();
            isFinishing();
        }
    }

//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (defaultID == R.id.nav_all_gates)
//            getMenuInflater().inflate(R.menu.menu_search, menu);
//        else if (defaultID == R.id.nav_choose)
//            getMenuInflater().inflate(R.menu.menu_none, menu);
//        return super.onPrepareOptionsMenu(menu);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void createDatabase() {
        mdbHelper = new DatabaseHelper(this);

        File database = getApplicationContext().getDatabasePath(mdbHelper.DBName);
        if (false == database.exists()) {
            mdbHelper.getReadableDatabase();
            if (copyDatabase(this)) {
                Log.i("Database : ", "Copy success");
            } else {
                Log.i("Database : ", "Copy denined");
            }
        }
    }

    private boolean copyDatabase(Context context) {
        try {
            InputStream myInput = context.getAssets().open(mdbHelper.DBName);
            String outFileName = mdbHelper.DBLocatoin + mdbHelper.DBName;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length = 0;

            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
