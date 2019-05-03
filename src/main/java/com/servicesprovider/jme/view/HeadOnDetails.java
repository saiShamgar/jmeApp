package com.servicesprovider.jme.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.EvenBusClass.Events;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.model.HeadOnWeightmentResponse;
import com.servicesprovider.jme.model.JmeDataBase;
import com.servicesprovider.jme.model.SaveDataSingletone;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.VarietyCount;
import com.servicesprovider.jme.model.WeightData;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.view.adapters.WeightDetailsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadOnDetails extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    @BindView(R.id.txt_btn_complete)
    TextView btnNext;

    @BindView(R.id.txt_btn_save)
    TextView btnNextWeight;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.txt_weight_date_time)
    @Expose
    TextView tvDate;

    @BindView(R.id.txt_no_nets)
    @Expose
    EditText etNoOfNets;

    @BindView(R.id.txt_total_weight)
    @Expose
    EditText total_weight_manual;

    @BindView(R.id.txt_total_weight_view)
    @Expose
    TextView tvTotalWeight;

    @BindView(R.id.txt_tare_weight)
    @Expose
    EditText etTareWeight;

    @BindView(R.id.txt_total_tare_wt)
    @Expose
    TextView totalTareWeight;

    @BindView(R.id.txt_net_weight)
    @Expose
    TextView netWeight;

    @BindView(R.id.spinner_nav)
    @Expose
    Spinner spinnerCount;

    @BindView(R.id.spinner_layout)
    @Expose
    LinearLayout spinnerLayout;

    @BindView(R.id.recycler_view)
    @Expose
    RecyclerView recyclerViewFactory;

    private Context mContext;
    private List<VarietyCount> listVarityCount = new ArrayList<>();
    private SharedPrefrencesData prefrencesData;
    private String TAG = HeadOnDetails.class.getSimpleName();
    private ArrayAdapter<String> countAdapter;
    private String numberOfNets = "0.0";
    private float tareWeight = 0;
    private float totalWeight = 0;
    private float totTareWeight = 0;
    private float netTotalWeight = 0;
    private String item = "";
    private ArrayList<WeightData> weightList = new ArrayList<>();
    private List<WeightData> weightDataList = new ArrayList<>();
    private JmeDataBase dataBase;
    private WeightDetailsAdapter detailsAdapter;
    private boolean validate;
    private List<WeightData> dataList;
    private List<Double> totalCommulativeWeightList = new ArrayList<>();
    private Double cumulativeWeights = 0.0;
    private int FROM_ACTIVTY_STATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_head_on_weight_details);
        setContentView(R.layout.activity_weight_details);

        mContext = HeadOnDetails.this;
        ButterKnife.bind(this);


        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText(R.string.weight_details);
        }

        init();
        if (FROM_ACTIVTY_STATUS == 1 || FROM_ACTIVTY_STATUS == 2) {
            total_weight_manual.setVisibility(View.GONE);
            tvTotalWeight.setVisibility(View.VISIBLE);
            tvTotalWeight.setClickable(true);

        } else {
            total_weight_manual.setVisibility(View.VISIBLE);
            tvTotalWeight.setVisibility(View.GONE);
            tvTotalWeight.setClickable(false);
        }

        total_weight_manual.addTextChangedListener(new TextWatcher() {
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ComingData", "1: " + prefrencesData.getBoolean(mContext, SharedPrefrencesData.WEIGHT_HEAD_ON_LOADED));
        if (prefrencesData.getBoolean(mContext, SharedPrefrencesData.WEIGHT_HEAD_ON_LOADED)) {
            Log.d("ComingData", "2: Inside if ");
            setWeightAdapter();

        }
    }

    private void init() {
        FROM_ACTIVTY_STATUS = getIntent().getIntExtra(AppConstant.FROM_ACTIVITY, 0);
        prefrencesData = SharedPrefrencesData.newInstance();
        Gson gson = new Gson();

        if (FROM_ACTIVTY_STATUS == 1) {

            if (prefrencesData.getBoolean(this, SharedPrefrencesData.IS_HEAD_LESS_WEIGHT)) {
                HeadOnWeightmentResponse response = gson.fromJson(prefrencesData.getString(this, SharedPrefrencesData.HEAD_LESS_WEIGHT), HeadOnWeightmentResponse.class);
                setSpinner(response);
            }

        } else if (FROM_ACTIVTY_STATUS == 2) {
            if (prefrencesData.getBoolean(this, SharedPrefrencesData.IS_HEAD_ON_WEIGHT)) {
                HeadOnWeightmentResponse response = gson.fromJson(prefrencesData.getString(this, SharedPrefrencesData.HEAD_ON_WEIGHT), HeadOnWeightmentResponse.class);
                setSpinner(response);
            }

        } else if (FROM_ACTIVTY_STATUS == 3) {
            if (prefrencesData.getBoolean(this, SharedPrefrencesData.IS_HEAD_ON_WEIGHT)) {
                HeadOnWeightmentResponse response = gson.fromJson(prefrencesData.getString(this, SharedPrefrencesData.HEAD_ON_WEIGHT), HeadOnWeightmentResponse.class);
                setSpinner(response);
            }
        } else if (FROM_ACTIVTY_STATUS == 4) {

            if (prefrencesData.getBoolean(this, SharedPrefrencesData.IS_HEAD_LESS_WEIGHT)) {
                HeadOnWeightmentResponse response = gson.fromJson(prefrencesData.getString(this, SharedPrefrencesData.HEAD_LESS_WEIGHT), HeadOnWeightmentResponse.class);
                setSpinner(response);
            }

        }
       /* if (checkFromHeadOn())
        {
            if (prefrencesData.getBoolean(this, SharedPrefrencesData.IS_HEAD_ON_WEIGHT))
            {
                HeadOnWeightmentResponse response = gson.fromJson(prefrencesData.getString(this, SharedPrefrencesData.HEAD_ON_WEIGHT), HeadOnWeightmentResponse.class);
                setSpinner(response);
            }
        }
        else
        {
            if (prefrencesData.getBoolean(this, SharedPrefrencesData.IS_HEAD_LESS_WEIGHT))

            {
                HeadOnWeightmentResponse response = gson.fromJson(prefrencesData.getString(this, SharedPrefrencesData.HEAD_LESS_WEIGHT), HeadOnWeightmentResponse.class);
                setSpinner(response);
            }
        }
*/
//        tvDate.setText(AppUtils.getCurrentDate());
        dataBase = new JmeDataBase(this);

        recyclerViewFactory.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewFactory.setHasFixedSize(true);

        etNoOfNets.addTextChangedListener(new HeadOnDetails.GenericTextWatcher(etNoOfNets));
        etTareWeight.addTextChangedListener(new HeadOnDetails.GenericTextWatcher(etTareWeight));
//        tvTotalWeight.addTextChangedListener(new HeadOnDetails.GenericTextWatcher(tvTotalWeight));


        btnNext.setOnClickListener(this);
        btnNextWeight.setOnClickListener(this);
        tvTotalWeight.setOnClickListener(this);

    }

    private void setSpinner(HeadOnWeightmentResponse response) {
        listVarityCount = response.getVarietyCountList();
        List<String> list = new ArrayList<>();
        list.add("Select");
        for (VarietyCount s : listVarityCount) {
            list.add(s.getVarietyCountCode());
        }

        countAdapter = new ArrayAdapter<String>(this, R.layout.show_count, list);
        spinnerLayout.setVisibility(View.VISIBLE);
        spinnerCount.setAdapter(countAdapter);
        spinnerCount.setOnItemSelectedListener(this);
    }

    private boolean checkFromHeadOn() {
        boolean flag = false;
        switch (FROM_ACTIVTY_STATUS) {
            case AppConstant.FLAG_FROM_HEAD_ON:
                flag = true;
                break;

            case AppConstant.FLAG_FROM_HEAD_LESS:
                flag = false;
                break;

        }

        return flag;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_btn_complete:
                startActivity(new Intent(HeadOnDetails.this, HeadOnInsertedDetails.class).putExtra(AppConstant.FROM_ACTIVITY, FROM_ACTIVTY_STATUS));
                break;

            case R.id.txt_btn_save:
                if (doValidation()) {
                    AppUtils.showCustomOkCancelDialog(mContext, "", getString(R.string.next_count_alert), "YES", "NO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setGridData();
                                    spinnerCount.setSelection(0);
                                }
                            },
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    setGridData();
                                }
                            });
                }
                break;

            case R.id.txt_total_weight_view:
                startActivityForResult(new Intent(HeadOnDetails.this, WeightLoadMachine.class), 100);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "onActivityResult: " + data.getStringExtra("weight"));
                tvTotalWeight.setText(data.getStringExtra("weight"));
                totalWeight = Float.parseFloat(tvTotalWeight.getText().toString().trim());

                Log.i(TAG, "onActivityResult: Total Weight" + totalWeight);
                calculateWeight();
            }
        }
    }

    private void setGridData() {
        WeightData data = new WeightData();
        data.setDateTime(tvDate.getText().toString().trim());
        data.setNumberOfNets(etNoOfNets.getText().toString().trim());
        data.setTareWeight(etTareWeight.getText().toString().trim());
        if (FROM_ACTIVTY_STATUS == 1 || FROM_ACTIVTY_STATUS == 2) {
            data.setTotalWeight(tvTotalWeight.getText().toString().trim());

        } else if (FROM_ACTIVTY_STATUS == 3 || FROM_ACTIVTY_STATUS == 4) {
            data.setTotalWeight(total_weight_manual.getText().toString().trim());

        }
        data.setTotalTareWeight(String.valueOf(Math.round(Float.parseFloat(totalTareWeight.getText().toString().trim()) * 100.0) / 100.0));
        data.setNetWeight(Float.parseFloat(netWeight.getText().toString().trim()));
        data.setProductVarietyCount(item);
        data.setComulativeWeight(SaveDataSingletone.getInstance().getComulativeWeight() + Double.parseDouble(netWeight.getText().toString().trim()));

        double test = SaveDataSingletone.getInstance().getComulativeWeight() + Double.parseDouble(netWeight.getText().toString().trim());
        Log.d("ProductDataCount", "1: " + item);

        if (FROM_ACTIVTY_STATUS == 1) {
            dataList = dataBase.showHeadLessWeight();

        } else if (FROM_ACTIVTY_STATUS == 2) {
            dataList = dataBase.showHeadOnWeight();

        } else if (FROM_ACTIVTY_STATUS == 3) {
            dataList = dataBase.showHeadOnWeight();

        } else if (FROM_ACTIVTY_STATUS == 4) {
            dataList = dataBase.showHeadLessWeight();

        }

       /* if (checkFromHeadOn())
            dataList = dataBase.showHeadOnWeight();
        else
            dataList = dataBase.showHeadLessWeight();
*/

        Log.d("cumulativeWeights", "" + dataList.size());
        for (WeightData weightData : dataList) {
            totalCommulativeWeightList.add(weightData.getComulativeWeight());
//            Log.d("cumulativeWeights","database "+weightData.getComulativeWeight()+" cc "+cumulativeWeights);
//            cumulativeWeights = weightData.getComulativeWeight();
        }
        if (dataList.size() > 0) {
            cumulativeWeights = Collections.max(totalCommulativeWeightList);
        }

        cumulativeWeights = cumulativeWeights + Double.parseDouble(netWeight.getText().toString().trim());
        data.setComulativeWeight(cumulativeWeights);


        weightList.add(data); // add value in list

        if (FROM_ACTIVTY_STATUS == 1) {
            dataBase.insertHeadLessData(data);
            prefrencesData.saveListData(mContext, weightList, SharedPrefrencesData.WEIGHT_HEAD_LESS_DATA_LIST);
            prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_HEAD_LESS_LOADED);
            setWeightAdapter();
        } else if (FROM_ACTIVTY_STATUS == 2) {
            dataBase.insertHeadOnData(data);
            prefrencesData.saveListData(mContext, weightList, SharedPrefrencesData.WEIGHT_HEAD_ON_DATA_LIST);
            prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_HEAD_ON_LOADED);
            setWeightAdapter();

        } else if (FROM_ACTIVTY_STATUS == 3) {
            dataBase.insertHeadOnData(data);
            prefrencesData.saveListData(mContext, weightList, SharedPrefrencesData.WEIGHT_HEAD_ON_DATA_LIST);
            prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_HEAD_ON_LOADED);
            setWeightAdapter();
        } else if (FROM_ACTIVTY_STATUS == 4) {
            dataBase.insertHeadLessData(data);
            prefrencesData.saveListData(mContext, weightList, SharedPrefrencesData.WEIGHT_HEAD_LESS_DATA_LIST);
            prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_HEAD_LESS_LOADED);
            setWeightAdapter();
        }

        // save list in preference
       /* if (checkFromHeadOn()) {
            dataBase.insertHeadOnData(data);
            prefrencesData.saveListData(mContext, weightList, SharedPrefrencesData.WEIGHT_HEAD_ON_DATA_LIST);
            prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_HEAD_ON_LOADED);
            setWeightAdapter();
        } else {
            dataBase.insertHeadLessData(data);
            prefrencesData.saveListData(mContext, weightList, SharedPrefrencesData.WEIGHT_HEAD_LESS_DATA_LIST);
            prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_HEAD_LESS_LOADED);
            setWeightAdapter();
        }*/

        clearText();
    }

    private void clearText() {
        etNoOfNets.setText(null);
        tvTotalWeight.setText("Get Weight");
        total_weight_manual.setText(null);
        totalTareWeight.setText(null);
        netWeight.setText(null);
        etTareWeight.setText(null);
    }

    boolean doValidation() {
        validate = true;
        if (spinnerCount.getSelectedItem().toString().equalsIgnoreCase("Select")) {
            validate = false;
            spinnerLayout.setBackground(ContextCompat.getDrawable(mContext, R.color.color_alert));
            AppUtils.showToast(mContext, "Select Count");
        } else if (etNoOfNets.getText().toString().trim().length() == 0) {
            validate = false;
            etNoOfNets.setError("Enter Number Of Net");
            etNoOfNets.requestFocus();

        } else if (etTareWeight.getText().toString().trim().length() == 0) {
            validate = false;
            etTareWeight.requestFocus();
            etTareWeight.setError("Enter Tare Weight");

        } else if (FROM_ACTIVTY_STATUS == 1 || FROM_ACTIVTY_STATUS == 2) {
            if (tvTotalWeight.getText().toString().equalsIgnoreCase("Get Weight")) {
                validate = false;
                tvTotalWeight.requestFocus();
                AppUtils.showToast(mContext, "Invalid Total Weight");
            }
        } else if (FROM_ACTIVTY_STATUS == 3 || FROM_ACTIVTY_STATUS == 4) {
            if (total_weight_manual.getText().toString().trim().length() == 0) {
                validate = false;
                total_weight_manual.requestFocus();
                AppUtils.showToast(mContext, "Invalid Total Weight");
            }
        }
        /*
        else if (tvTotalWeight.getText().toString().equalsIgnoreCase("Get Weight")) {
            validate = false;
            tvTotalWeight.requestFocus();
            AppUtils.showToast(mContext, "Invalid Total Weight");
        } else if (total_weight_manual.getText().toString().trim().length() == 0) {
            validate = false;
            total_weight_manual.requestFocus();
            AppUtils.showToast(mContext, "Invalid Total Weight");
        } */
        else if (netWeight.getText().toString().trim().length() == 0) {
            validate = false;
            AppUtils.showToast(mContext, "Invalid Total Weight");
        }

        return validate;
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
        tvDate.setText(message.getTime());
        Log.d("EventMessage", message.getLatitude() + " Time " + message.getTime());

    }

    void setWeightAdapter() {
        try {
            if (FROM_ACTIVTY_STATUS == 1) {
                detailsAdapter = new WeightDetailsAdapter(mContext, dataBase.showHeadLessWeight());

            } else if (FROM_ACTIVTY_STATUS == 2) {
                detailsAdapter = new WeightDetailsAdapter(mContext, dataBase.showHeadOnWeight());

            } else if (FROM_ACTIVTY_STATUS == 3) {
                detailsAdapter = new WeightDetailsAdapter(mContext, dataBase.showHeadOnWeight());

            } else if (FROM_ACTIVTY_STATUS == 4) {
                detailsAdapter = new WeightDetailsAdapter(mContext, dataBase.showHeadLessWeight());

            }


          /*  if (checkFromHeadOn())
                detailsAdapter = new WeightDetailsAdapter(mContext, dataBase.showHeadOnWeight());
            else
                detailsAdapter = new WeightDetailsAdapter(mContext, dataBase.showHeadLessWeight());
*/
            recyclerViewFactory.setAdapter(detailsAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateWeight() {
        totTareWeight = Float.parseFloat(numberOfNets) * tareWeight;
        totalTareWeight.setText(String.valueOf(Math.round(totTareWeight * 100.0) / 100.0));

        Log.i(TAG, "calculateWeight: " + totalWeight);

        if (totalWeight > 0) {
            Log.i(TAG, "calculateWeight: Inside " + totalWeight);
            netTotalWeight = totalWeight - totTareWeight;
            netWeight.setText(String.valueOf(Math.round(netTotalWeight * 100.0) / 100.0));
        } else {
            netWeight.setText("");
        }

        Log.d("TextType", "total: " + totalTareWeight + " NetWeight: " + netWeight);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        switch (parent.getId()) {
            case R.id.spinner_nav:
                AppUtils.showToast(mContext, "" + spinnerCount.getSelectedItem() + " Count");
                item = parent.getItemAtPosition(i).toString();
                spinnerLayout.setBackground(ContextCompat.getDrawable(mContext, R.color.color_transparent));
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                    tareWeight = Float.parseFloat(text);
                    calculateWeight();
                    break;
                case R.id.txt_total_weight_view:
                    Log.i(TAG, "afterTextChanged: " + text);
                    totalWeight = Float.parseFloat(text);
                    calculateWeight();
                    break;
                case R.id.txt_total_weight:
                    Log.i(TAG, "afterTextChanged: " + text);
                    totalWeight = Float.parseFloat(text);
                    calculateWeight();
                    break;
            }
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

}
