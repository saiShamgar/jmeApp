package com.servicesprovider.jme.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Akash on 3/23/2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SITE_WEIGHT_TABLE = "CREATE TABLE "+JmeDataBase.TABLE_SITE_WEIGHT+"(_id integer primary key,"+
                JmeDataBase.DATE_TIME+" text,"+JmeDataBase.NUMBER_OF_NETS+" text,"+JmeDataBase.TOTAL_WEIGHT+" text,"+JmeDataBase.TOTAL_TARE_WEIGHT+" text,"+
                JmeDataBase.NET_WEIGHT+" text,"+JmeDataBase.CUMULATIVE_WEIGHT+" text,"+JmeDataBase.PRODUCT_VARITY_COUNT+" text,"+JmeDataBase.TARE_WEIGHT+" text"+" )";

        String CREATE_HEAD_ON_WEIGHT_TABLE = "CREATE TABLE "+JmeDataBase.TABLE_HEAD_ON_WEIGHT +"(_id integer primary key,"+
                JmeDataBase.HEAD_ON_DATE_TIME +" text,"+JmeDataBase.HEAD_ON_NUMBER_OF_NETS +" text,"+JmeDataBase.HEAD_ON_TOTAL_WEIGHT +" text,"+JmeDataBase.HEAD_ON_TOTAL_TARE_WEIGHT +" text,"+JmeDataBase.HEAD_ON_TARE_WEIGHT +" text,"
                +JmeDataBase.HEAD_ON_NET_WEIGHT +" text,"+JmeDataBase.HEAD_ON_CUMULATIVE_WEIGHT +" text,"+JmeDataBase.HEAD_ON_PRODUCT_VARITY_COUNT +" text"+" )";

        String CREATE_HEAD_LESS_WEIGHT_TABLE = "CREATE TABLE "+JmeDataBase.TABLE_HEAD_LESS_WEIGHT +"(_id integer primary key,"+
                JmeDataBase.HEAD_LESS_DATE_TIME +" text,"+JmeDataBase.HEAD_LESS_NUMBER_OF_NETS +" text,"+JmeDataBase.HEAD_LESS_TOTAL_WEIGHT +" text,"+JmeDataBase.HEAD_LESS_TOTAL_TARE_WEIGHT +" text,"+JmeDataBase.HEAD_LESS_TARE_WEIGHT +" text,"
                +JmeDataBase.HEAD_LESS_NET_WEIGHT +" text,"+JmeDataBase.HEAD_LESS_CUMULATIVE_WEIGHT +" text,"+JmeDataBase.HEAD_LESS_PRODUCT_VARITY_COUNT +" text"+" )";

        String CREATE_HEAD_ON_HEAD_LESS_WEIGHT_TABLE = "CREATE TABLE "+JmeDataBase.TABLE_HEAD_ON_HEAD_LESS_WEIGHT +"(_id integer primary key,"+
                JmeDataBase.HEAD_ON_HEAD_LESS_DATE_TIME +" text,"+JmeDataBase.HEAD_ON_HEAD_LESS_NUMBER_OF_NETS +" text,"+JmeDataBase.HEAD_ON_HEAD_LESS_TOTAL_WEIGHT +" text,"+JmeDataBase.HEAD_ON_HEAD_LESS_TOTAL_TARE_WEIGHT +" text,"
                +JmeDataBase.HEAD_ON_HEAD_LESS_TARE_WEIGHT +" text,"+JmeDataBase.HEAD_ON_HEAD_LESS_NET_WEIGHT +" text,"+JmeDataBase.HEAD_ON_HEAD_LESS_CUMULATIVE_WEIGHT +" text,"+JmeDataBase.HEAD_ON_HEAD_LESS_PRODUCT_VARITY_COUNT +" text,"
                +JmeDataBase.HEAD_ON_HEAD_LESS_PERSON_NAME +" text,"+JmeDataBase.HEAD_ON_HEAD_LESS__TEAM_NO +" text"+" )";


        db.execSQL(CREATE_SITE_WEIGHT_TABLE);
        db.execSQL(CREATE_HEAD_ON_WEIGHT_TABLE);
        db.execSQL(CREATE_HEAD_LESS_WEIGHT_TABLE);
        db.execSQL(CREATE_HEAD_ON_HEAD_LESS_WEIGHT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
