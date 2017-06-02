package com.um1616.carticketinfo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.um1616.carticketinfo.model.Gate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ba Nyar Naing on 22-Oct-16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DBName = "cartickets_city_gate.sqlite";
    public static final String DBLocatoin = "/data/data/com.um1616.carticketinfo/databases/";
    public static final String TableGates = "gates";
    public static final String TableCities = "cities";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBName, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBName).getPath();

        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public List<Gate> getAllGates() {
        Gate gate = null;
        List<Gate> gateList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TableGates, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            gate = new Gate(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            gateList.add(gate);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return gateList;
    }

    public Gate getGateById(int id) {
        Gate gate = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TableGates + " WHERE _ID = ? ", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        gate = new Gate(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        closeDatabase();
        return gate;
    }

    public Gate getGateByName(String name) {
        Gate gate = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TableGates + " WHERE name = ? ", new String[]{name});
        cursor.moveToFirst();
        gate = new Gate(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        cursor.close();
        closeDatabase();
        return gate;
    }

    public List<String> getAllGateName() {
        String name = null;
        List<String> nameList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT name FROM " + TableGates, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name = cursor.getString(0);
            nameList.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return nameList;
    }

    public List<String> getAllRoute() {
        String route = null;
        List<String> routeList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT routes FROM " + TableGates, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            route = cursor.getString(0);
            routeList.add(route);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return routeList;
    }

    public List<String> getAllSavedGateName() {
        String name = null;
        List<String> nameList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT name FROM " + TableGates + " WHERE saved = ?", new String[]{"1"});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name = cursor.getString(0);
            nameList.add(name);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return nameList;
    }

    public List<String> getAllSavedRoute() {
        String route = null;
        List<String> routeList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT routes FROM " + TableGates + " WHERE saved = ?", new String[]{"1"});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            route = cursor.getString(0);
            routeList.add(route);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return routeList;
    }

    public List<String> getAllCities() {
        List<String> cityList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + TableCities, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cityList.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return cityList;
    }

    public void savedGate(int id, int saved) {
        openDatabase();
        mDatabase.execSQL("UPDATE " + TableGates + " SET saved = ? WHERE _ID = ? ", new String[]{String.valueOf(saved), String.valueOf(id)});
    }

    public int savedState(int id) {
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT saved FROM " + TableGates + " WHERE _id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        int state = Integer.valueOf(cursor.getString(0));
        cursor.close();
        closeDatabase();
        return state;
    }
}
