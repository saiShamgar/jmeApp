package com.servicesprovider.jme.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.EvenBusClass.Events;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.controller.VarietyCountListener;
import com.servicesprovider.jme.model.JmeDataBase;
import com.servicesprovider.jme.model.SaveDataSingletone;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.WeightData;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.utils.GpsLocation;
import com.servicesprovider.jme.view.adapters.WeightDetailsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeightDetails extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        VarietyCountListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.spinner_nav)
    Spinner spinnerCount;
    @BindView(R.id.spinner_layout)
    LinearLayout spinner_layout;

    @BindView(R.id.recycler_view)
    RecyclerView weightView;
    @BindView(R.id.txt_weight_date_time)
    TextView weightDateTime;
    @BindView(R.id.txt_no_nets)
    EditText ed_NumberOfNets;

    @BindView(R.id.txt_total_weight)
    EditText ed_TotalWeight;

    @BindView(R.id.txt_total_weight_view)
    TextView txt_total_weight_view;

    @BindView(R.id.txt_tare_weight)
    EditText ed_TareWeight;
    @BindView(R.id.txt_total_tare_wt)
    TextView txt_TotalTareWeight;
    @BindView(R.id.txt_net_weight)
    TextView txt_netWeight;
    @BindView(R.id.txt_btn_complete)
    TextView txt_btn_complete;
    @BindView(R.id.txt_btn_save)
    TextView txt_btn_save;

    private WeightDetailsAdapter detailsAdapter;
    private Context mContext;
    private ArrayList<WeightData> weightList = new ArrayList<>();
    private String numberOfNets = "0.0";
    private double totalWeight = 0.0;
    private double tareWeight = 0.0;
    private String totalTareWeight = "";
    private String netWeight = "0.0";
    private SharedPrefrencesData prefrencesData = null;
    private boolean validate;
    private JmeDataBase dataBase;
    private ArrayAdapter<String> countAdapter;
    private String[] productCounts = {"Select count", "45", "47", "56"};
    private List<String> productContList = new ArrayList<>();
    private List<Double> totalCommulativeWeightList = new ArrayList<>();
    private List<WeightData> weightDataList = new ArrayList<>();
    private List<WeightData> dataList;
    private String item = "";
    private OnCalculateTotalWeight onCalculateTotalWeight = null;
    private double cumulativeWeights = 0.0;
    private static final int REQ_CODE = 1;

    public static String OPEN_WEIGHT_DETAILS_SCREEN = "activityKey";
    public static final String COMING_FOR_FACTORY = "F";
    public static final String COMING_FOR_FACTORY_MANUAL = "FM";
    public static final String COMING_FOR_SITE = "S";
    private String activityStatus;

    public static void openWeightDetalisActivity(Context activity, String status) {
        Log.d("CheckStatus", "2: " + status);
        Intent intent;
        switch (status) {
            case COMING_FOR_FACTORY:
                activity.startActivity(new Intent(activity, WeightDetails.class).putExtra(OPEN_WEIGHT_DETAILS_SCREEN, status));
                break;
            case COMING_FOR_FACTORY_MANUAL:
                activity.startActivity(new Intent(activity, WeightDetails.class).putExtra(OPEN_WEIGHT_DETAILS_SCREEN, status));
                break;
            case COMING_FOR_SITE:
                activity.startActivity(new Intent(activity, WeightDetails.class).putExtra(OPEN_WEIGHT_DETAILS_SCREEN, status));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_details);
        ButterKnife.bind(this);
        mContext = WeightDetails.this;

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
//            getSupportActionBar().setIcon(R.drawable.ic_menu);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText(R.string.weight_details);
        }
        activityStatus = getIntent().getStringExtra(OPEN_WEIGHT_DETAILS_SCREEN);

        init();
    }

    private void init() {

        Log.d("CheckStatus", "1: " + getIntent().getStringExtra(WeightDetails.OPEN_WEIGHT_DETAILS_SCREEN));
        Log.i("statusfrom", String.valueOf(activityStatus));
        switch (activityStatus) {
            case COMING_FOR_FACTORY:
                txt_total_weight_view.setVisibility(View.VISIBLE);
                ed_TotalWeight.setVisibility(View.GONE);
                break;
            case COMING_FOR_FACTORY_MANUAL:
                txt_total_weight_view.setVisibility(View.GONE);
                ed_TotalWeight.setVisibility(View.VISIBLE);
                break;
            case COMING_FOR_SITE:
                txt_total_weight_view.setVisibility(View.GONE);
                ed_TotalWeight.setVisibility(View.VISIBLE);
                break;
        }

        ed_TotalWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String manualTotalWeight = editable.toString().trim();
                if (manualTotalWeight != null && !manualTotalWeight.isEmpty()) {
                    totalWeight = Float.parseFloat(manualTotalWeight);
                    calculateWeight();
                }

            }
        });
        txt_btn_complete.setOnClickListener(this);
        txt_btn_save.setOnClickListener(this);
//        ed_TotalWeight.setOnClickListener(this);
        txt_total_weight_view.setOnClickListener(this);

        ed_NumberOfNets.addTextChangedListener(new GenericTextWatcher(ed_NumberOfNets));
        ed_TareWeight.addTextChangedListener(new GenericTextWatcher(ed_TareWeight));
         ed_TotalWeight.addTextChangedListener(new GenericTextWatcher(ed_TotalWeight));
//        txt_total_weight_view.addTextChangedListener((TextWatcher) txt_total_weight_view);
        prefrencesData = SharedPrefrencesData.newInstance();
        dataBase = new JmeDataBase(this);

        productContList = prefrencesData.getStringList(mContext, SharedPrefrencesData.VARIETY_COUNT_LIST);
        countAdapter = new ArrayAdapter<String>(this, R.layout.show_count, productContList);

        spinner_layout.setVisibility(View.VISIBLE);
        spinnerCount.setAdapter(countAdapter);
        spinnerCount.setOnItemSelectedListener(this);

        prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.COMING_FROM_WEIGHT_DEATILS_ACTIVITY);

        weightView.setLayoutManager(new LinearLayoutManager(mContext));
        weightView.setHasFixedSize(true);

//        weightList.add("Akash");
//        weightList.add("Akash");


    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getMessage(Events.ActivityServiceMessage message) {
        weightDateTime.setText(message.getTime());
        prefrencesData.saveString(mContext, message.getLatitude(), SharedPrefrencesData.FARMER_LATITUDE);
        prefrencesData.saveString(mContext, message.getLongitude(), SharedPrefrencesData.FARMER_LONGITUDE);
        prefrencesData.saveString(mContext, message.getAddress(), SharedPrefrencesData.FARMAER_LOCATION_FROM_GPS);

        Log.d("EventMessage", message.getLatitude() + " Time " + message.getTime());

    }

    /**
     * Do calculation
     */
    private void calculateWeight() {
        totalTareWeight = String.valueOf(Math.round((Double.parseDouble(numberOfNets) * tareWeight) * 100.0) / 100.0);
        txt_TotalTareWeight.setText(totalTareWeight);

        if (totalWeight > 0) {
            netWeight = String.valueOf(Math.round((totalWeight - Double.parseDouble(totalTareWeight)) * 100.0) / 100.0);
            txt_netWeight.setText(netWeight);
        } else {
            txt_netWeight.setText("");
//            AppUtils.showToast(mContext,"Invalid Total Weight");
        }


        Log.d("TextType", "total: " + totalTareWeight + " NetWeight: " + netWeight);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ComingData", "1: " + prefrencesData.getBoolean(mContext, SharedPrefrencesData.WEIGHT_LOADED));
        if (prefrencesData.getBoolean(mContext, SharedPrefrencesData.WEIGHT_LOADED)) {
            Log.d("ComingData", "2: Inside if ");
            setWeightAdapter();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_btn_save:
                if (doValidation()) {
                    AppUtils.showCustomOkCancelDialog(mContext, "", getString(R.string.next_count_alert), "YES", "NO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pushDataInGrid();

                                    spinnerCount.setSelection(0);
                                }
                            },
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pushDataInGrid();

                                }
                            });
                }
                break;

            case R.id.txt_btn_complete:
                startActivity(new Intent(mContext, ShowInsertedDetails.class).putExtra(OPEN_WEIGHT_DETAILS_SCREEN, activityStatus));
                break;
            case R.id.txt_total_weight_view:
                startActivityForResult(new Intent(mContext, WeightLoadMachine.class), REQ_CODE);
                break;
            case R.id.txt_total_weight:
                switch (activityStatus) {
                    case WeightDetails.COMING_FOR_FACTORY:
                        startActivityForResult(new Intent(mContext, WeightLoadMachine.class), REQ_CODE);
                        break;
                }
                break;
        }
    }


    /**
     * clear text
     */
    void clearText() {
        ed_NumberOfNets.setText("");
        ed_TotalWeight.setText("");
        txt_TotalTareWeight.setText("");
        txt_netWeight.setText("");
        txt_total_weight_view.setText("Get weight");
        ed_TotalWeight.setText(null);
        ed_TareWeight.setText(null);
        ed_NumberOfNets.requestFocus();
    }

    /**
     * show data in table
     */
    void pushDataInGrid() {
        WeightData data = new WeightData();

        data.setDateTime(weightDateTime.getText().toString().trim());
        data.setNumberOfNets(ed_NumberOfNets.getText().toString().trim());

        data.setTareWeight(String.valueOf(Math.round(Float.parseFloat(ed_TareWeight.getText().toString().trim()))));
        Log.i("comingfrom", activityStatus);
        Log.i("entertotalvalue", String.valueOf(ed_TotalWeight.getText().toString().trim()));
        if (activityStatus.equalsIgnoreCase(COMING_FOR_SITE)) {
            data.setTotalWeight(String.valueOf(Math.round(Float.parseFloat(ed_TotalWeight.getText().toString().trim()) * 100.0) / 100.0));
        } else if (activityStatus.equalsIgnoreCase(COMING_FOR_FACTORY)) {
            data.setTotalWeight(String.valueOf(Math.round(Float.parseFloat(txt_total_weight_view.getText().toString().trim()) * 100.0) / 100.0));
        } else if (activityStatus.equalsIgnoreCase(COMING_FOR_FACTORY_MANUAL)) {
            data.setTotalWeight(String.valueOf(Math.round(Float.parseFloat(ed_TotalWeight.getText().toString().trim()) * 100.0) / 100.0));
        }
        data.setTotalTareWeight(String.valueOf(Math.round(Float.parseFloat(txt_TotalTareWeight.getText().toString().trim()) * 100.0) / 100.0));
        data.setNetWeight((float) (Math.round(Float.parseFloat(txt_netWeight.getText().toString().trim()) * 100.0) / 100.0));
        data.setProductVarietyCount(item);

        dataList = dataBase.showWeight();
        Log.d("cumulativeWeights", "" + dataList.size());
        for (WeightData weightData : dataList) {
            totalCommulativeWeightList.add(weightData.getComulativeWeight());
        }
        if (dataList.size() > 0) {
            cumulativeWeights = Collections.max(totalCommulativeWeightList);
        }

        cumulativeWeights = cumulativeWeights + Double.parseDouble(txt_netWeight.getText().toString().trim());
        data.setComulativeWeight(cumulativeWeights);


        weightList.add(data); // add value in list
        Log.d("cumulativeWeights", "" + cumulativeWeights);
        // save list in preference

        dataBase.insertData(data);
        prefrencesData.saveListData(mContext, weightList, SharedPrefrencesData.WEIGHT_DATA_LIST);
        prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_LOADED);

        setWeightAdapter(); // set Adapter
        // clear edit field
        clearText();
    }

    void setWeightAdapter() {
        //set adapter
        try {

            weightDataList.addAll(dataBase.showWeight());

            detailsAdapter = new WeightDetailsAdapter(mContext, dataBase.showWeight());
            weightView.setAdapter(detailsAdapter);

            /*for (int i = 0 ; i <sortBy().size();i++){
                Log.d("ProductDataCount","2 "+weightDataList.get(i).getProductVarietyCount());
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner_nav:
                // do
                if (!spinnerCount.getSelectedItem().equals("Select")) {
                    item = parent.getItemAtPosition(position).toString();

                } else {
                    AppUtils.showToast(mContext, "Select Count");
                }
                spinner_layout.setBackground(ContextCompat.getDrawable(mContext, R.color.color_transparent));

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onVarietyDownload() {

    }

    // Handling multiple edit text
    class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.d("TextEmpty", "1: " + charSequence.toString() + "," + i + "," + i1 + "," + i2);
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.d("TextEmpty", "2: " + charSequence.toString() + "," + i + "," + i1 + "," + i2);
        }

        public void afterTextChanged(Editable editable) {
            String text;
            if (editable.toString() != null && !editable.toString().equals("")) {
                text = editable.toString();
            } else {
                text = "0.0";
            }
            switch (view.getId()) {
                case R.id.txt_no_nets:
                    numberOfNets = text;
                    calculateWeight();
                    break;
                case R.id.txt_tare_weight:
                    tareWeight = Double.parseDouble(text);
                    calculateWeight();
                    break;
                case R.id.txt_total_weight:
                    totalWeight = Double.parseDouble(text);
                    calculateWeight();
                    break;
            }
        }
    }

    boolean doValidation() {
        validate = true;
        if (spinnerCount.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            validate = false;
            spinner_layout.setBackground(ContextCompat.getDrawable(mContext, R.color.color_alert));
            AppUtils.showToast(mContext, "Select Count");
        } else if (ed_NumberOfNets.getText().toString().trim().length() == 0) {
            validate = false;
            ed_NumberOfNets.setError("Enter Number Of Net");
            ed_NumberOfNets.requestFocus();

        } else if (ed_TareWeight.getText().toString().trim().length() == 0) {
            validate = false;
            ed_TareWeight.requestFocus();
            ed_TareWeight.setError("Enter Tare Weight");
        } else if (activityStatus.equalsIgnoreCase(COMING_FOR_SITE)) {
            if (ed_TotalWeight.getText().toString().trim().length() == 0) {
                validate = false;
                ed_TotalWeight.requestFocus();
                ed_TotalWeight.setError("Enter Total Weight");
            }
        } else if (activityStatus.equalsIgnoreCase(COMING_FOR_FACTORY_MANUAL)) {
            if (ed_TotalWeight.getText().toString().trim().length() == 0) {
                validate = false;
                ed_TotalWeight.requestFocus();
                ed_TotalWeight.setError("Enter Total Weight");
            }
        } else if (txt_netWeight.getText().toString().trim().length() == 0) {
            validate = false;
            AppUtils.showToast(mContext, "Get Total Weight");
        }

        return validate;
    }

    public interface OnCalculateTotalWeight {
        void comulativeWeight(String totalWeight);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("AkashValue", "2: " + data.getStringExtra("weight"));
        if (requestCode == REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d("AkashValue", "3: " + data.getStringExtra("weight"));
                txt_total_weight_view.setText(data.getStringExtra("weight"));
                totalWeight = Double.parseDouble(txt_total_weight_view.getText().toString().trim());
                calculateWeight();
            }
        }

    }
}
