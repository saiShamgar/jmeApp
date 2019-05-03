package com.servicesprovider.jme.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.EvenBusClass.Events;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.model.HeadOnWeightmentResponse;
import com.servicesprovider.jme.model.HoHlWeightData;
import com.servicesprovider.jme.model.JmeDataBase;
import com.servicesprovider.jme.model.SaveDataSingletone;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.VarietyCount;
import com.servicesprovider.jme.model.WeightData;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.view.adapters.HoHlWeightDetailsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadOnHeadLessDetails extends BaseActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    @BindView(R.id.txt_ho_hl_weight_btn_complete)
    TextView btnNext;

    @BindView(R.id.txt_ho_hl_weight_btn_save)
    TextView btnNextWeight;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.txt_ho_hl_weight_date_time)
    @Expose
    TextView tvDate;

    @BindView(R.id.txt_ho_hl_weight_no_nets)
    @Expose
    EditText etNoOfNets;

    @BindView(R.id.txt_ho_hl_weight_total_weight_view)
    @Expose
    TextView tvTotalWeight;

    @BindView(R.id.txt_total_weight)
    @Expose
    EditText tvTotalWeightManual;

    @BindView(R.id.txt_ho_hl_weight_tare_weight)
    @Expose
    EditText etTareWeight;

    @BindView(R.id.et_group_person_name)
    @Expose
    EditText etPersonName;

    @BindView(R.id.et_group_no)
    @Expose
    EditText etGroupNo;

    @BindView(R.id.txt_ho_hl_weight_total_tare_wt)
    @Expose
    TextView totalTareWeight;

    @BindView(R.id.txt_ho_hl_weight_net_weight)
    @Expose
    TextView netWeight;

    @BindView(R.id.spinner_nav)
    @Expose
    Spinner spinnerCount;

    @BindView(R.id.spinner_layout)
    @Expose
    LinearLayout spinnerLayout;

    @BindView(R.id.ho_hl_weight_recycler_view)
    @Expose
    RecyclerView recyclerViewFactory;

    Context mContext;
    private SharedPrefrencesData prefrencesData;
    private JmeDataBase dataBase;
    private String numberOfNets = "0.0";
    private double tareWeight = 0.0;
    private double totalWeight = 0.0;
    private String totTareWeight = "";
    private String netTotalWeight = "";
    private String TAG;
    private List<VarietyCount> listVarityCount = new ArrayList<>();
    private ArrayAdapter<String> countAdapter;
    private boolean validate;
    private String item;
    private List<HoHlWeightData> dataList;
    private List<Double> totalCommulativeWeightList = new ArrayList<>();
    private Double cumulativeWeights = 0.0;
    private ArrayList<HoHlWeightData> weightList = new ArrayList<>();
    private HoHlWeightDetailsAdapter detailsAdapter;
    private int FROM_ACTIVTY_STATUS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_on_head_less_details);
        ButterKnife.bind(this);
        mContext = HeadOnHeadLessDetails.this;

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText(R.string.weight_details);
        }
        FROM_ACTIVTY_STATUS = getIntent().getIntExtra(AppConstant.FROM_ACTIVITY, 0);

        init();
        if (FROM_ACTIVTY_STATUS == 5) {
            tvTotalWeightManual.setVisibility(View.GONE);
            tvTotalWeight.setVisibility(View.VISIBLE);
            tvTotalWeight.setClickable(true);

        } else {
            tvTotalWeightManual.setVisibility(View.VISIBLE);
            tvTotalWeight.setVisibility(View.GONE);
            tvTotalWeight.setClickable(false);
        }
        tvTotalWeightManual.addTextChangedListener(new TextWatcher() {
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
        Log.d("ComingData","1: "+prefrencesData.getBoolean(mContext,SharedPrefrencesData.WEIGHT_HEAD_ON_HEAD_LESS_LOADED));
        if (prefrencesData.getBoolean(mContext,SharedPrefrencesData.WEIGHT_HEAD_ON_HEAD_LESS_LOADED))
        {
            Log.d("ComingData","2: Inside if ");
            setWeightAdapter();

        }
    }

    private void init()
    {
        TAG = HeadOnHeadLessDetails.class.getSimpleName();
        prefrencesData = SharedPrefrencesData.newInstance();
        Gson gson = new Gson();

        if (prefrencesData.getBoolean(this, SharedPrefrencesData.IS_HEAD_ON_HEAD_LESS_WEIGHT))
        {
            HeadOnWeightmentResponse response = gson.fromJson(prefrencesData.getString(this, SharedPrefrencesData.HEAD_ON_HEAD_LESS_WEIGHT), HeadOnWeightmentResponse.class);
            setSpinner(response);
        }


//        tvDate.setText(AppUtils.getCurrentDate());
        dataBase = new JmeDataBase(this);

        recyclerViewFactory.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewFactory.setHasFixedSize(true);

        etNoOfNets.addTextChangedListener(new HeadOnHeadLessDetails.GenericTextWatcher(etNoOfNets));
        etTareWeight.addTextChangedListener(new HeadOnHeadLessDetails.GenericTextWatcher(etTareWeight));

        btnNext.setOnClickListener(this);
        btnNextWeight.setOnClickListener(this);
        tvTotalWeight.setOnClickListener(this);
    }

    private void setSpinner(HeadOnWeightmentResponse response)
    {
        listVarityCount = response.getVarietyCountList();
        List<String> list = new ArrayList<>();
        list.add("Select");
        for (VarietyCount s : listVarityCount)
        {
            list.add(s.getVarietyCountCode());
        }

        countAdapter = new ArrayAdapter<String>(this,R.layout.show_count,list);
        spinnerLayout.setVisibility(View.VISIBLE);
        spinnerCount.setAdapter(countAdapter);
        spinnerCount.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.txt_ho_hl_weight_btn_complete:
                startActivity(new Intent(HeadOnHeadLessDetails.this, HeadOnHeadLessInsertedDetails.class));
                break;

            case R.id.txt_ho_hl_weight_btn_save:
                if (doValidation())
                {
                        AppUtils.showCustomOkCancelDialog(mContext, "", getString(R.string.next_count_alert), "YES", "NO", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v) {
                                        setGridData();
                                        spinnerCount.setSelection(0);
                                    }
                                },
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        setGridData();
                                    }
                                });
                }
                break;

            case R.id.txt_ho_hl_weight_total_weight_view:
                startActivityForResult(new Intent(HeadOnHeadLessDetails.this, WeightLoadMachine.class), 100);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                Log.i(TAG, "onActivityResult: "+data.getStringExtra("weight"));
                tvTotalWeight.setText(data.getStringExtra("weight"));
                totalWeight = Math.round(Double.parseDouble(tvTotalWeight.getText().toString().trim()));

                Log.i(TAG, "onActivityResult: Total Weight"+totalWeight);
                calculateWeight();
            }
        }
    }

    private boolean doValidation()
    {
        validate = true;
        if (spinnerCount.getSelectedItem()!= null && spinnerCount.getSelectedItem().toString().equalsIgnoreCase("Select")){
            validate = false;
            spinnerLayout.setBackground(ContextCompat.getDrawable(mContext,R.color.color_alert));
            AppUtils.showToast(mContext,"Select Count");
        }
        else if (etPersonName.getText().toString().trim().length() == 0)
        {
            validate = false;
            etPersonName.setError("Enter Person Name");
            etPersonName.requestFocus();
        }
        else if (etGroupNo.getText().toString().trim().length() == 0)
        {
            validate = false;
            etGroupNo.setError("Enter Group No");
            etGroupNo.requestFocus();
        }
        else if (etNoOfNets.getText().toString().trim().length() == 0){
            validate = false;
            etNoOfNets.setError("Enter Number Of Net");
            etNoOfNets.requestFocus();

        }else if (etTareWeight.getText().toString().trim().length() == 0){
            validate = false;
            etTareWeight.requestFocus();
            etTareWeight.setError("Enter Tare Weight");
        }
        else if(FROM_ACTIVTY_STATUS==5){
            if (tvTotalWeight.getText().toString().equalsIgnoreCase("Get Weight")) {
                validate = false;
                tvTotalWeight.requestFocus();
                AppUtils.showToast(mContext, "Invalid Total Weight");
            }
        }else if(FROM_ACTIVTY_STATUS==6){
            if (tvTotalWeightManual.getText().toString().trim().length() == 0) {
                validate = false;
                tvTotalWeightManual.requestFocus();
                AppUtils.showToast(mContext, "Invalid Total Weight");
            }
        }/*else if (tvTotalWeight.getText().toString().equalsIgnoreCase("Get Weight")) {
            validate = false;
            tvTotalWeight.requestFocus();
            AppUtils.showToast(mContext, "Invalid Total Weight");
        }*/
        else if (netWeight.getText().toString().trim().length()==0){
            validate = false;
            AppUtils.showToast(mContext,"Invalid Total Weight");
        }

        return validate;
    }

    private void setGridData()
    {
        HoHlWeightData data = new HoHlWeightData();
        data.setDateTime(tvDate.getText().toString().trim());
        data.setNumberOfNets(etNoOfNets.getText().toString().trim());
        data.setTareWeight(etTareWeight.getText().toString().trim());
        if (FROM_ACTIVTY_STATUS == 5) {
            data.setTotalWeight(tvTotalWeight.getText().toString().trim());

        } else if (FROM_ACTIVTY_STATUS == 6) {
            data.setTotalWeight(tvTotalWeightManual.getText().toString().trim());

        }
        data.setTotalTareWeight(totalTareWeight.getText().toString().trim());
        data.setNetWeight(Float.parseFloat(netWeight.getText().toString().trim()));
        data.setProductVarietyCount(item);
        data.setPersonName(etPersonName.getText().toString().trim());
        data.setTeamNo(etGroupNo.getText().toString().trim());
        data.setComulativeWeight(SaveDataSingletone.getInstance().getComulativeWeight() + Double.parseDouble(netWeight.getText().toString().trim()));

        dataList = dataBase.showHeadOnHeadLessWeight();
        for (HoHlWeightData weightData : dataList){
            totalCommulativeWeightList.add(weightData.getComulativeWeight());
//            Log.d("cumulativeWeights","database "+weightData.getComulativeWeight()+" cc "+cumulativeWeights);
//            cumulativeWeights = weightData.getComulativeWeight();
        }

        if (dataList.size() > 0) {
            cumulativeWeights =  Collections.max(totalCommulativeWeightList);
        }

        cumulativeWeights = cumulativeWeights + Double.parseDouble(netWeight.getText().toString().trim());
        data.setComulativeWeight(cumulativeWeights);

        weightList.add(data);

        dataBase.insertHeadOnHeadLessData(data);
        prefrencesData.saveHoHlListData(mContext, weightList, SharedPrefrencesData.WEIGHT_HEAD_ON_HEAD_LESS_DATA_LIST);
        prefrencesData.saveBoolean(mContext, true, SharedPrefrencesData.WEIGHT_HEAD_ON_HEAD_LESS_LOADED);
        setWeightAdapter();

        clearText();
    }

    private void clearText()
    {
        etNoOfNets.setText(null);
        tvTotalWeight.setText("Get Weight");
        totalTareWeight.setText(null);
        tvTotalWeightManual.setText(null);
        netWeight.setText(null);
        etGroupNo.setText(null);
        etPersonName.setText(null);
    }

    private void setWeightAdapter()
    {
        try{
            detailsAdapter = new HoHlWeightDetailsAdapter(mContext,dataBase.showHeadOnHeadLessWeight());
            recyclerViewFactory.setAdapter(detailsAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l)
    {
        switch (parent.getId()){
            case R.id.spinner_nav:
                AppUtils.showToast(mContext,""+spinnerCount.getSelectedItem()+" Count");
                item = parent.getItemAtPosition(i).toString();
                spinnerLayout.setBackground(ContextCompat.getDrawable(mContext,R.color.color_transparent));
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
                Log.d("TextEmpty","1: "+charSequence.toString()+","+i+","+i1+","+i2);
            }
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("TextEmpty","2: "+charSequence.toString()+","+i+","+i1+","+i2);
            }

            public void afterTextChanged(Editable editable) {
                String text;
                if (editable.toString()!=null && !editable.toString().equals("")){
                    text = editable.toString();
                }else {
                    text = "0.0";
                }
                switch(view.getId())
                {
                    case R.id.txt_ho_hl_weight_no_nets:
                        numberOfNets = text;
                        calculateWeight();
                        break;
                    case R.id.txt_ho_hl_weight_tare_weight:
                        tareWeight = Double.parseDouble(text);
                        calculateWeight();
                        break;
                    case R.id.txt_ho_hl_weight_total_weight_view:
                        Log.i(TAG, "afterTextChanged: "+text);
                        totalWeight = Double.parseDouble(text);
                        calculateWeight();
                        break;
                }
            }
        }

    private void calculateWeight(){

//        totTareWeight = String.valueOf((Math.round(Double.parseDouble(numberOfNets) * tareWeight)));
        totTareWeight = String.valueOf(Math.round((Float.parseFloat(numberOfNets) * tareWeight)*100.0)/100.0);
        totalTareWeight.setText(totTareWeight);

        Log.i(TAG, "calculateWeight: "+totalWeight);

        if (totalWeight>0)
        {
            Log.i(TAG, "calculateWeight: Inside "+totalWeight);
//            netTotalWeight = String.valueOf(totalWeight - Double.parseDouble(totTareWeight));
            netTotalWeight = String.valueOf(Math.round((totalWeight - Float.parseFloat(totTareWeight))*100.0)/100.0);
            netWeight.setText(netTotalWeight);
        }else {
            netWeight.setText("");
        }

        Log.d("TextType","total: "+totalTareWeight+" NetWeight: "+netWeight);

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
    public void getMessage(Events.ActivityServiceMessage message)
    {
        tvDate.setText(message.getTime());
        Log.d("EventMessage",message.getLatitude()+" Time "+message.getTime());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
