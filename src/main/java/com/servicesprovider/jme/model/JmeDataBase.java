package com.servicesprovider.jme.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 3/23/2018.
 */

public class JmeDataBase {

    /**
     *  DataBase = JmeData
     */

    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context mContext;
    public static final String DATABASE_NAME = "JmeData";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_SITE_WEIGHT = "siteWeight";
    public static final String TABLE_HEAD_ON_WEIGHT = "headOnWeight";
    public static final String TABLE_HEAD_LESS_WEIGHT = "headLessWeight";
    public static final String TABLE_HEAD_ON_HEAD_LESS_WEIGHT = "headOnheadLessWeight";


    // Table column name
    public static final String DATE_TIME = "dateTime";
    public static final String NUMBER_OF_NETS = "netsNumber";
    public static final String TOTAL_WEIGHT = "totalWeight";
    public static final String TOTAL_TARE_WEIGHT = "totalTareWeight";
    public static final String NET_WEIGHT = "netWeight";
    public static final String CUMULATIVE_WEIGHT = "cumulativeWeight";
    public static final String PRODUCT_VARITY_COUNT = "varityCount";
    public static final String TARE_WEIGHT = "tareWeight";

    // Table HEADON column name
    public static final String HEAD_ON_DATE_TIME = "dateTime";
    public static final String HEAD_ON_NUMBER_OF_NETS = "netsNumber";
    public static final String HEAD_ON_TOTAL_WEIGHT = "totalWeight";
    public static final String HEAD_ON_TOTAL_TARE_WEIGHT = "totalTareWeight";
    public static final String HEAD_ON_TARE_WEIGHT = "tareWeight";
    public static final String HEAD_ON_NET_WEIGHT = "netWeight";
    public static final String HEAD_ON_CUMULATIVE_WEIGHT = "cumulativeWeight";
    public static final String HEAD_ON_PRODUCT_VARITY_COUNT = "varityCount";

    // Table HEADLESS column name
    public static final String HEAD_LESS_DATE_TIME = "dateTime";
    public static final String HEAD_LESS_NUMBER_OF_NETS = "netsNumber";
    public static final String HEAD_LESS_TOTAL_WEIGHT = "totalWeight";
    public static final String HEAD_LESS_TOTAL_TARE_WEIGHT = "totalTareWeight";
    public static final String HEAD_LESS_TARE_WEIGHT = "tareWeight";
    public static final String HEAD_LESS_NET_WEIGHT = "netWeight";
    public static final String HEAD_LESS_CUMULATIVE_WEIGHT = "cumulativeWeight";
    public static final String HEAD_LESS_PRODUCT_VARITY_COUNT = "varityCount";

    // Table HEADON_HEADLESS column name
    public static final String HEAD_ON_HEAD_LESS_DATE_TIME = "hlhodateTime";
    public static final String HEAD_ON_HEAD_LESS_NUMBER_OF_NETS = "hlhonetsNumber";
    public static final String HEAD_ON_HEAD_LESS_TOTAL_WEIGHT = "hlhototalWeight";
    public static final String HEAD_ON_HEAD_LESS_TOTAL_TARE_WEIGHT = "hlhototalTareWeight";
    public static final String HEAD_ON_HEAD_LESS_TARE_WEIGHT = "hlhotareWeight";
    public static final String HEAD_ON_HEAD_LESS_NET_WEIGHT = "hlhonetWeight";
    public static final String HEAD_ON_HEAD_LESS_CUMULATIVE_WEIGHT = "hlhocumulativeWeight";
    public static final String HEAD_ON_HEAD_LESS_PRODUCT_VARITY_COUNT = "hlhovarityCount";
    public static final String HEAD_ON_HEAD_LESS_PERSON_NAME = "hlhopersonname";
    public static final String HEAD_ON_HEAD_LESS__TEAM_NO = "hlhovarityCountteamno";


    public JmeDataBase(Context mContext) {
        this.mContext = mContext;
        dbHelper = new DBHelper(mContext,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /**
     * Open DataBase
     */
    public void open(){
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void readOnly(){
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    /**
     *  Close dataBase
     */

    public void closeDataBase(){
        sqLiteDatabase.close();
    }

    /**
     *  Insert Values
     */
    public void insertData(WeightData data){
        open();
        try{

            ContentValues values = new ContentValues();

            values.put(DATE_TIME, data.getDateTime());
            values.put(NUMBER_OF_NETS,data.getNumberOfNets());
            values.put(TOTAL_WEIGHT,data.getTotalWeight());
            values.put(TOTAL_TARE_WEIGHT,data.getTotalTareWeight());
            values.put(NET_WEIGHT,data.getNetWeight());
            values.put(CUMULATIVE_WEIGHT,data.getComulativeWeight());
            values.put(PRODUCT_VARITY_COUNT,data.getProductVarietyCount());
            values.put(TARE_WEIGHT,data.getTareWeight());
            sqLiteDatabase.insert(TABLE_SITE_WEIGHT, null, values);
            Log.d("InsertData","Insertion done");
        }catch (Exception e){
            e.printStackTrace();
        }

        closeDataBase();
    }

    /**
     *  Insert FACTORY Values
     */
    public void insertHeadOnData(WeightData data){
        open();
        try{

            ContentValues values = new ContentValues();

            values.put(HEAD_ON_DATE_TIME, data.getDateTime());
            values.put(HEAD_ON_NUMBER_OF_NETS,data.getNumberOfNets());
            values.put(HEAD_ON_TOTAL_WEIGHT,data.getTotalWeight());
            values.put(HEAD_ON_TOTAL_TARE_WEIGHT,data.getTotalTareWeight());
            values.put(HEAD_ON_TARE_WEIGHT, data.getTareWeight());
            values.put(HEAD_ON_NET_WEIGHT,data.getNetWeight());
            values.put(HEAD_ON_CUMULATIVE_WEIGHT,data.getComulativeWeight());
            values.put(HEAD_ON_PRODUCT_VARITY_COUNT,data.getProductVarietyCount());
            sqLiteDatabase.insert(TABLE_HEAD_ON_WEIGHT, null, values);
            Log.d("InsertData","Insertion done");
        }catch (Exception e){
            e.printStackTrace();
        }

        closeDataBase();
    }

    public void insertHeadLessData(WeightData data)
    {
        open();
        try{

            ContentValues values = new ContentValues();

            values.put(HEAD_LESS_DATE_TIME, data.getDateTime());
            values.put(HEAD_LESS_NUMBER_OF_NETS,data.getNumberOfNets());
            values.put(HEAD_LESS_TOTAL_WEIGHT,data.getTotalWeight());
            values.put(HEAD_LESS_TOTAL_TARE_WEIGHT,data.getTotalTareWeight());
            values.put(HEAD_LESS_TARE_WEIGHT, data.getTareWeight());
            values.put(HEAD_LESS_NET_WEIGHT,data.getNetWeight());
            values.put(HEAD_LESS_CUMULATIVE_WEIGHT,data.getComulativeWeight());
            values.put(HEAD_LESS_PRODUCT_VARITY_COUNT,data.getProductVarietyCount());
            sqLiteDatabase.insert(TABLE_HEAD_LESS_WEIGHT, null, values);
            Log.d("InsertData","Insertion done");
        }catch (Exception e){
            e.printStackTrace();
        }

        closeDataBase();
    }

    public void insertHeadOnHeadLessData(HoHlWeightData data)
    {
        open();
        try{

            ContentValues values = new ContentValues();

            values.put(HEAD_ON_HEAD_LESS_DATE_TIME, data.getDateTime());
            values.put(HEAD_ON_HEAD_LESS_NUMBER_OF_NETS,data.getNumberOfNets());
            values.put(HEAD_ON_HEAD_LESS_TOTAL_WEIGHT,data.getTotalWeight());
            values.put(HEAD_ON_HEAD_LESS_TOTAL_TARE_WEIGHT,data.getTotalTareWeight());
            values.put(HEAD_ON_HEAD_LESS_TARE_WEIGHT, data.getTareWeight());
            values.put(HEAD_ON_HEAD_LESS_NET_WEIGHT,data.getNetWeight());
            values.put(HEAD_ON_HEAD_LESS_CUMULATIVE_WEIGHT,data.getComulativeWeight());
            values.put(HEAD_ON_HEAD_LESS_PRODUCT_VARITY_COUNT,data.getProductVarietyCount());
            values.put(HEAD_ON_HEAD_LESS_PERSON_NAME, data.getPersonName());
            values.put(HEAD_ON_HEAD_LESS__TEAM_NO, data.getTeamNo());
            sqLiteDatabase.insert(TABLE_HEAD_ON_HEAD_LESS_WEIGHT, null, values);
            Log.d("InsertData","Insertion done");
        }catch (Exception e){
            e.printStackTrace();
        }

        closeDataBase();
    }

    /**
     *  Get All Values
     */

    public List<WeightData> showWeight(){

        List<WeightData> weightDataList = new ArrayList<>();
        Cursor cursor;
        open();

        cursor = sqLiteDatabase.rawQuery("select * from "+TABLE_SITE_WEIGHT+" order by dateTime DESC",null);
        Log.d("InsertData","Retrive Data: "+cursor.getCount());
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            do{
                WeightData data = new WeightData();
                data.setDateTime(cursor.getString(cursor.getColumnIndex(DATE_TIME)));
                data.setNumberOfNets(cursor.getString(cursor.getColumnIndex(NUMBER_OF_NETS)));
                data.setTotalWeight(cursor.getString(cursor.getColumnIndex(TOTAL_WEIGHT)));
                data.setTotalTareWeight(cursor.getString(cursor.getColumnIndex(TOTAL_TARE_WEIGHT)));
                data.setNetWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(NET_WEIGHT))));
                data.setComulativeWeight(Double.parseDouble(cursor.getString(cursor.getColumnIndex(CUMULATIVE_WEIGHT))));
                data.setProductVarietyCount(cursor.getString(cursor.getColumnIndex(PRODUCT_VARITY_COUNT)));
                data.setTareWeight(cursor.getString(cursor.getColumnIndex(TARE_WEIGHT)));
                weightDataList.add(data);
            }while (cursor.moveToNext());
        }
        closeDataBase();
        return weightDataList;
    }

    public List<WeightData> showHeadOnWeight(){

        List<WeightData> weightDataList = new ArrayList<>();
        Cursor cursor;
        open();

        cursor = sqLiteDatabase.rawQuery("select * from "+ TABLE_HEAD_ON_WEIGHT +" order by dateTime DESC",null);
        Log.d("InsertData","Retrive Data: "+cursor.getCount());
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            do{
                WeightData data = new WeightData();
                data.setDateTime(cursor.getString(cursor.getColumnIndex(HEAD_ON_DATE_TIME)));
                data.setNumberOfNets(cursor.getString(cursor.getColumnIndex(HEAD_ON_NUMBER_OF_NETS)));
                data.setTotalWeight(cursor.getString(cursor.getColumnIndex(HEAD_ON_TOTAL_WEIGHT)));
                data.setTareWeight(cursor.getString(cursor.getColumnIndex(HEAD_ON_TARE_WEIGHT)));
                data.setTotalTareWeight(cursor.getString(cursor.getColumnIndex(HEAD_ON_TOTAL_TARE_WEIGHT)));
                data.setNetWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(HEAD_ON_NET_WEIGHT))));
                data.setComulativeWeight(Double.parseDouble(cursor.getString(cursor.getColumnIndex(HEAD_ON_CUMULATIVE_WEIGHT))));
                data.setProductVarietyCount(cursor.getString(cursor.getColumnIndex(HEAD_ON_PRODUCT_VARITY_COUNT)));
                weightDataList.add(data);
            }while (cursor.moveToNext());
        }
        closeDataBase();
        return weightDataList;
    }

    public List<WeightData> showHeadLessWeight()
    {
        List<WeightData> weightDataList = new ArrayList<>();
        Cursor cursor;
        open();

        cursor = sqLiteDatabase.rawQuery("select * from "+ TABLE_HEAD_LESS_WEIGHT +" order by dateTime DESC",null);
        Log.d("InsertData","Retrive Data: "+cursor.getCount());
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            do{
                WeightData data = new WeightData();
                data.setDateTime(cursor.getString(cursor.getColumnIndex(HEAD_LESS_DATE_TIME)));
                data.setNumberOfNets(cursor.getString(cursor.getColumnIndex(HEAD_LESS_NUMBER_OF_NETS)));
                data.setTotalWeight(cursor.getString(cursor.getColumnIndex(HEAD_LESS_TOTAL_WEIGHT)));
                data.setTareWeight(cursor.getString(cursor.getColumnIndex(HEAD_LESS_TARE_WEIGHT)));
                data.setTotalTareWeight(cursor.getString(cursor.getColumnIndex(HEAD_LESS_TOTAL_TARE_WEIGHT)));
                data.setNetWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(HEAD_LESS_NET_WEIGHT))));
                data.setComulativeWeight(Double.parseDouble(cursor.getString(cursor.getColumnIndex(HEAD_LESS_CUMULATIVE_WEIGHT))));
                data.setProductVarietyCount(cursor.getString(cursor.getColumnIndex(HEAD_LESS_PRODUCT_VARITY_COUNT)));
                weightDataList.add(data);
            }while (cursor.moveToNext());
        }
        closeDataBase();
        return weightDataList;
    }

    public List<HoHlWeightData> showHeadOnHeadLessWeight()
    {
        List<HoHlWeightData> weightDataList = new ArrayList<>();
        Cursor cursor;
        open();

        cursor = sqLiteDatabase.rawQuery("select * from "+ TABLE_HEAD_ON_HEAD_LESS_WEIGHT +" order by hlhodateTime DESC",null);
        Log.d("InsertData","Retrive Data: "+cursor.getCount());
        cursor.moveToFirst();
        if (cursor != null && cursor.getCount() > 0){
            do{
                HoHlWeightData data = new HoHlWeightData();
                data.setDateTime(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_DATE_TIME)));
                data.setNumberOfNets(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_NUMBER_OF_NETS)));
                data.setTotalWeight(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_TOTAL_WEIGHT)));
                data.setTareWeight(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_TARE_WEIGHT)));
                data.setTotalTareWeight(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_TOTAL_TARE_WEIGHT)));
                data.setNetWeight(Float.parseFloat(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_NET_WEIGHT))));
                data.setComulativeWeight(Double.parseDouble(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_CUMULATIVE_WEIGHT))));
                data.setProductVarietyCount(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_PRODUCT_VARITY_COUNT)));
                data.setPersonName(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS_PERSON_NAME)));
                data.setTeamNo(cursor.getString(cursor.getColumnIndex(HEAD_ON_HEAD_LESS__TEAM_NO)));
                weightDataList.add(data);
            }while (cursor.moveToNext());
        }
        closeDataBase();
        return weightDataList;
    }


    public void delete(){
        open();
        sqLiteDatabase.execSQL("DELETE FROM "+TABLE_SITE_WEIGHT);
        closeDataBase();
    }

    public void deleteHeadOnDb(){
        open();
        sqLiteDatabase.execSQL("DELETE FROM "+ TABLE_HEAD_ON_WEIGHT);
        closeDataBase();
    }

    public void deleteHeadLessDb(){
        open();
        sqLiteDatabase.execSQL("DELETE FROM "+ TABLE_HEAD_LESS_WEIGHT);
        closeDataBase();
    }

    public void deleteHeadOnHeadLessDb(){
        open();
        sqLiteDatabase.execSQL("DELETE FROM "+ TABLE_HEAD_ON_HEAD_LESS_WEIGHT);
        closeDataBase();
    }

}
