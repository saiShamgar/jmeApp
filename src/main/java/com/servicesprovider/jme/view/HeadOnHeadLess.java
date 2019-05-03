package com.servicesprovider.jme.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.controller.HeadOnWeightmentService;
import com.servicesprovider.jme.controller.LotNumberService;
import com.servicesprovider.jme.model.HeadOnWeightmentResponse;
import com.servicesprovider.jme.model.LotNumber;
import com.servicesprovider.jme.model.LotNumberResponse;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadOnHeadLess extends BaseActivity implements View.OnClickListener, LotNumberService.OnLotNoListener,
                                                    AdapterView.OnItemSelectedListener, HeadOnWeightmentService.OnFactoryWeightmentListener
{
    @BindView(R.id.btn_Ho_Hl_next)
    Button btnNext;
    @BindView(R.id.btn_Ho_Hl_new)
    Button btnNew;
    @BindView(R.id.btn_Ho_Hl_save)
    Button btnSave;

    @BindView(R.id.Ho_Hl_spinner_lot_no)
    Spinner spinnerLotNo;

    @BindView(R.id.tv_Ho_Hl_lot_date)
    TextView tvLotDate;
    @BindView(R.id.tv_Ho_Hl_recieved_date)
    TextView tvRecievedDate;
    @BindView(R.id.tv_Ho_Hl_material_group)
    TextView tvMaterialGroup;
    @BindView(R.id.tv_Ho_Hl_variety_name)
    TextView tvVarietyName;
    @BindView(R.id.Ho_Hl_spinner_layout)
    LinearLayout spinner_layout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    Context mContext;
    private LotNumberService lotNumberService;
    private String TAG;
    private List<String> lotNumberList = new ArrayList<>();
    private ArrayAdapter<String> lotAdapter;
    private HeadOnWeightmentService factoryWeightmentService;
    private boolean validate;
    private int FROM_ACTIVTY_STATUS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_on_head_less);
        ButterKnife.bind(this);
        mContext = HeadOnHeadLess.this;
        FROM_ACTIVTY_STATUS = getIntent().getIntExtra(AppConstant.FROM_ACTIVITY, 0);

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (FROM_ACTIVTY_STATUS == 5) {
                toolbar_title.setText("HON to HL - Weighing Scale");


            } else if (FROM_ACTIVTY_STATUS == 6) {
                toolbar_title.setText("HON to HL - Manual");

            }

        }

        init();
    }

    private void init()
    {
        TAG = HeadOnHeadLess.class.getSimpleName();
        btnNext.setOnClickListener(this);
        spinnerLotNo.setOnItemSelectedListener(this);

        callLotNoService();
    }

    @Override
    public void onClick(View view) {

        switch(view.getId())
        {
            case R.id.btn_Ho_Hl_next:
                if (doValidation())
                {
                    if(FROM_ACTIVTY_STATUS==5){
                        startActivity(new Intent(HeadOnHeadLess.this, HeadOnHeadLessDetails.class).putExtra(AppConstant.FROM_ACTIVITY,AppConstant.FLAG_FROM_HEAD_ON_HEAD_LESS));

                    }else if(FROM_ACTIVTY_STATUS==6) {
                        startActivity(new Intent(HeadOnHeadLess.this, HeadOnHeadLessDetails.class).putExtra(AppConstant.FROM_ACTIVITY,AppConstant.FLAG_FROM_HEAD_ON_HEAD_LESS_MANUAL));

                    }
                }
                break;

        }
    }

    private void callLotNoService()
    {
        if (AppUtils.isNetworkAvailable(mContext))
        {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            lotNumberService = new LotNumberService(mContext, this);

            lotNumberService.getResponse(0);


        }else {
            AppUtils.showToast(mContext,getString(R.string.error_network));
        }
    }

    @Override
    public void onLotSuccess(LotNumberResponse response)
    {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if (response != null)
        {
            if (response.getResponseCode()== AppConstant.SUCCESS){

                lotNumberList.add(0,"Select Lot No");
                for (LotNumber lotNumber : response.getLotNumberList())
                {
                    lotNumberList.add(lotNumber.getLotNumber());
                }
                Log.d("ComingResponse",""+2);
                lotAdapter = new ArrayAdapter<String>(this,R.layout.name_list_row,lotNumberList);
                spinnerLotNo.setAdapter(lotAdapter);
            }else {
                AppUtils.showCustomOkDialog(mContext, "", response.getResponseMessage(), "OK", null);
            }
        }else {
            Log.d("ComingResponse",""+3);
            AppUtils.showCustomOkDialog(mContext,"",getResources().getString(R.string.error_default),"OK",null);
        }
    }

    @Override
    public void onLotFailure(Throwable error)
    {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        if (parent.getId() == R.id.Ho_Hl_spinner_lot_no)
        {
            if (!spinnerLotNo.getSelectedItem().equals("Select Lot No"))
            {
                spinner_layout.setBackground(ContextCompat.getDrawable(mContext, R.color.color_transparent));

                Log.i(TAG, "onItemSelected: HeadOn"+spinnerLotNo.getSelectedItem());
                callWeightmentService(String.valueOf(spinnerLotNo.getSelectedItem()));
            }
            else
            {
                clearFields();
            }
        }
    }

    private void clearFields()
    {
        tvRecievedDate.setText(null);
        tvVarietyName.setText(null);
        tvLotDate.setText(null);
        tvMaterialGroup.setText(null);
        spinnerLotNo.setSelection(0);
    }

    private void callWeightmentService(String lotNo)
    {
        if (AppUtils.isNetworkAvailable(mContext))
        {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            factoryWeightmentService = new HeadOnWeightmentService(mContext,this);
            factoryWeightmentService.getResponse(lotNo, 0);

        }else {
            AppUtils.showToast(mContext,getString(R.string.error_network));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onWeighmentSuccess(HeadOnWeightmentResponse response)
    {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if (response != null)
        {
            if (response.getResponseCode()== AppConstant.SUCCESS)
            {
                if (response.getVarietyCountList().size() > 0){
                    setDetails(response);
                    saveDetails(response);
                }else {
                    AppUtils.showCustomOkDialog(mContext,"","Sorry we didn't found variety Count for this LOT.","Ok",null);
                    spinnerLotNo.setSelection(0);
                }

            }
            else
            {
                AppUtils.showCustomOkDialog(mContext, "", response.getResponseMessage(), "OK", null);
            }
        }
        else
        {
            AppUtils.showCustomOkDialog(mContext,"",getResources().getString(R.string.error_default),"OK",null);
        }
    }

    @Override
    public void onWeighmentFailure(Throwable error)
    {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
    }

    private void setDetails(HeadOnWeightmentResponse response)
    {
        tvMaterialGroup.setText(response.getMaterialGroup());
        tvLotDate.setText(response.getLotDate());
        tvVarietyName.setText(response.getVarietyName());
        tvRecievedDate.setText(AppUtils.getCurrentDate());
    }

    private void saveDetails(HeadOnWeightmentResponse response)
    {
        response.setRecievedDate(AppUtils.getCurrentDate());
        SharedPrefrencesData preferences = SharedPrefrencesData.newInstance();
        Gson gson = new Gson();
        String weightDetails = gson.toJson(response);
        preferences.saveString(this, weightDetails, SharedPrefrencesData.HEAD_ON_HEAD_LESS_WEIGHT);
        preferences.saveBoolean(this, true, SharedPrefrencesData.IS_HEAD_ON_HEAD_LESS_WEIGHT);
    }

    boolean doValidation()
    {
        validate = true;
        if (spinnerLotNo.getSelectedItem() != null) {
            if (spinnerLotNo.getSelectedItem().toString().equalsIgnoreCase("Select Lot No")) {
                validate = false;
                spinner_layout.setBackground(ContextCompat.getDrawable(mContext, R.color.app_color));
                spinner_layout.requestFocus();
                AppUtils.showToast(mContext, "Select Lot No");
            }
        }
        else
        {
            validate = false;
            AppUtils.showToast(mContext, "No Lot number availible");
        }

        return validate;
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
