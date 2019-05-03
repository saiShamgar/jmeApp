package com.servicesprovider.jme.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 8/17/2017.
 */

public class SharedPrefrencesData
{
    public static String LOGIN_CHECK = "login_check";
    public static String EMPLOYEE_NAME = "employee_name";
    public static String EMPLOYEE_EMAIL_ID = "employee_email";
    public static String EMPLOYEE_ID;
    public static String WEIGHT_DATA_LIST = "weight_list_data";
    public static String WEIGHT_LOADED = "weight_loaded";
    public static String EMPLOYEE_PHONE_NUMBER = "employee_phone_number";
    public static String COMING_FROM_WEIGHT_DEATILS_ACTIVITY = "coming_from_weight_details_activity";
    public static String FARMER_LATITUDE = "farmer_latitude";
    public static String FARMER_LONGITUDE = "farmer_longitude";
    public static String FARMAER_LOCATION_FROM_GPS = "farmer_location_from_gps";
    public static String CHECK_LOCATION = "check_location";
    public static String APP_VERSION_NUMBER = "app_version_number";
    public static String LOGOUT_CHECK = "logout_check";
    public static String AUTH_TOKEN = "auth_token";
    public static String VARIETY_COUNT_LIST = "variety_count_list";


    public static String SCHEDULE_NUMBER = "schedule_number";
    public static String ENQUIRY_NUMBER = "enquiry_number";
    public static String LOT_NUMBER = "lot_number";
    public static String AGENT_NAME = "agent_name";
    public static String FARMER_NAME = "farmer_name";
    public static String FARMER_POND_NUMBER = "farmer_pond_number";
    public static String FARMER_LOCATION = "farmer_location";
    public static String MATERIAL_GROUP_NAME = "material_group_name";
    public static String PRODUCT_VARIETY_NAME = "product_variety_name";
    public static String TOTAL_WEIGHT_FOR_ALL_COUNT = "total_weight_all_count";

    //HeadOn Weighment
    public static String IS_HEAD_ON_WEIGHT = "isHeadOnWeightSet";
    public static String HEAD_ON_WEIGHT = "HeadOnWeight";
    public static final String WEIGHT_HEAD_ON_DATA_LIST = "weight_head_on_data_list";
    public static final String WEIGHT_HEAD_ON_LOADED = "weight_head_on_loaded";
    public static final String TOTAL_WEIGHT_OF_HEAD_ON_FOR_ALL_COUNT = "total_weight_of_head_on_for_all_count";

    //HeadLess Weighment
    public static final String HEAD_LESS_WEIGHT = "HeadLessWeight";
    public static String IS_HEAD_LESS_WEIGHT = "isHeadLessWeightSet";
    public static final String WEIGHT_HEAD_LESS_DATA_LIST = "weight_head_on_data_list";
    public static final String WEIGHT_HEAD_LESS_LOADED = "weight_head_on_loaded";
    public static final String TOTAL_WEIGHT_OF_HEAD_LESS_FOR_ALL_COUNT = "total_weight_of_head_less_for_all_count";

    //HeadOnheadLess Weighment
    public static final String HEAD_ON_HEAD_LESS_WEIGHT = "HeadOnHeadLessWeight";
    public static final String IS_HEAD_ON_HEAD_LESS_WEIGHT = "isHeadLessWeightSet";
    public static final String WEIGHT_HEAD_ON_HEAD_LESS_DATA_LIST = "weight_hO_hL_data_list";
    public static final String WEIGHT_HEAD_ON_HEAD_LESS_LOADED = "weight_hO_hl_loaded";
    public static final String TOTAL_WEIGHT_OF_HEAD_ON_HEAD_LESS_FOR_ALL_COUNT = "total_weight_of_head_on_head_less_for_all_count";









    private static SharedPrefrencesData sharedPrefrencesData = null;


    private SharedPrefrencesData(){

    }

    public static synchronized SharedPrefrencesData newInstance(){
        if (sharedPrefrencesData == null){
            sharedPrefrencesData = new SharedPrefrencesData();
        }
        return sharedPrefrencesData;
    }

    /**
     *
     * @param context
     * @param value
     * @param key
     */
    public void saveString(Context context, String value, String key){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key,value);
        editor.commit();
    }

    /**
     *
     * @param context
     * @param key
     * @return
     */
    public String getString(Context context, String key){
        SharedPreferences settings;
        String value;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        value = settings.getString(key,null);
        return value;
    }

    public void saveBoolean(Context context,boolean value, String key){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public boolean getBoolean(Context context,String key){
        SharedPreferences settings;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        return settings.getBoolean(key,false);

    }

    public void saveStringList(Context context, List<String> arrayList, String key){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String value = gson.toJson(arrayList);
        editor.putString(key,value);
        editor.commit();
    }

    public List<String> getStringList(Context context, String key){

        SharedPreferences settings;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String value = settings.getString(key,null);
        Type type = new TypeToken<List<String>>(){}.getType();
        List<String> arrayList = gson.fromJson(value,type);
        return arrayList;
    }

    public void saveListData(Context context, ArrayList<WeightData> arrayList, String key){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String value = gson.toJson(arrayList);
        editor.putString(key,value);
        editor.commit();
    }

    public void saveHoHlListData(Context context, ArrayList<HoHlWeightData> arrayList, String key){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String value = gson.toJson(arrayList);
        editor.putString(key,value);
        editor.commit();
    }

    public ArrayList<WeightData> getListData(Context context, String key){

        SharedPreferences settings;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String value = settings.getString(key,null);
        Type type = new TypeToken<ArrayList<WeightData>>(){}.getType();
        ArrayList<WeightData> arrayList = gson.fromJson(value,type);
        return arrayList;
    }

    /**
     *  Save double value
     * @param context
     * @param value
     * @param key
     */
    public void saveDouble(Context context, double value, String key){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putLong(key,Double.doubleToRawLongBits(value)); // converting raw Long because long and double have same memory space
        editor.commit();
    }

    /**
     *  Read double value
     * @param context
     * @param key
     * @return
     */
    public double getDouble(Context context, String key){
        SharedPreferences settings;
        double value;
        settings = context.getSharedPreferences(key,Context.MODE_PRIVATE);
        value = Double.longBitsToDouble(settings.getLong(key,0));
        return value;
    }

}
