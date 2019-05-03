package com.servicesprovider.jme.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.controller.SiteWeighmentIsertService;
import com.servicesprovider.jme.model.InsertSiteWeightmentResponse;
import com.servicesprovider.jme.model.JmeDataBase;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.WeightData;
import com.servicesprovider.jme.model.WeightDataBody;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.view.adapters.InsertedDetailsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowInsertedDetails extends BaseActivity implements View.OnClickListener, SiteWeighmentIsertService.OnSiteInsertListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.txt_show_nets_number)
    TextView txt_show_nets_number;
    @BindView(R.id.txt_show_net_total_wt)
    TextView txt_show_net_total_wt;
    @BindView(R.id.txt_show_total_wt)
    TextView txt_show_total_wt;
    @BindView(R.id.txt_btn_save)
    FloatingActionButton txt_btn_save;

    private List<Double> totalWeight = new ArrayList<>();


    private Context mContext;
    private JmeDataBase dataBase;
    private List<WeightData> dataList;
    private List<WeightDataBody> dataBodyList = new ArrayList<>();
    private InsertedDetailsAdapter insertedDetailsAdapter = null;

    private SiteWeighmentIsertService siteWeighmentIsertService = null;
    private float totalNetWeightForEveryCount = 0;
    private int totalNumberOfNetsForEveryCount = 0;
    private float totalWeightForEveryCount = 0;
    private String activityStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_inserted_details);
        ButterKnife.bind(this);
        mContext = ShowInsertedDetails.this;

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
        dataBase = new JmeDataBase(mContext);

        init();

    }

    void init() {

        activityStatus = getIntent().getStringExtra(WeightDetails.OPEN_WEIGHT_DETAILS_SCREEN);
        Log.d("CheckStatus", getIntent().getStringExtra(WeightDetails.OPEN_WEIGHT_DETAILS_SCREEN));
        txt_btn_save.setOnClickListener(this);

        dataList = dataBase.showWeight();
        sortBy();

        recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        recycler_view.setHasFixedSize(true);

        insertedDetailsAdapter = new InsertedDetailsAdapter(mContext, dataList);
        recycler_view.setAdapter(insertedDetailsAdapter);

        // pass all data one array to server array
        for (WeightData data : dataList) {

            totalWeight.add(data.getComulativeWeight());
            Log.d("TareWeight", "TotalWeight " + data.getTotalWeight() + " No of Nets " + data.getNumberOfNets());

            // sum of totalWeight and numberOfNets
            totalNumberOfNetsForEveryCount = totalNumberOfNetsForEveryCount + Integer.parseInt(data.getNumberOfNets());

            totalWeightForEveryCount = totalWeightForEveryCount + Float.parseFloat(data.getTotalWeight());
            totalNetWeightForEveryCount = totalNetWeightForEveryCount + data.getNetWeight();

            WeightDataBody dataBody = new WeightDataBody();
            switch (activityStatus) {
                case WeightDetails.COMING_FOR_FACTORY:
                    dataBody.setFactoryNumberOfNets(data.getNumberOfNets());
                    dataBody.setFactoryNetWeight(String.valueOf(data.getNetWeight()));
                    dataBody.setFactoryTareWeight(data.getTareWeight());
                    dataBody.setFactoryTotalTareWeight(data.getTotalTareWeight());
                    dataBody.setFactoryTotalWeight(data.getTotalWeight());
                    dataBody.setFactoryWeighmentDateTime(data.getDateTime());
                    dataBody.setVarietyCount(data.getProductVarietyCount());
                    break;
                case WeightDetails.COMING_FOR_FACTORY_MANUAL:
                    dataBody.setFactoryNumberOfNets(data.getNumberOfNets());
                    dataBody.setFactoryNetWeight(String.valueOf(data.getNetWeight()));
                    dataBody.setFactoryTareWeight(data.getTareWeight());
                    dataBody.setFactoryTotalTareWeight(data.getTotalTareWeight());
                    dataBody.setFactoryTotalWeight(data.getTotalWeight());
                    dataBody.setFactoryWeighmentDateTime(data.getDateTime());
                    dataBody.setVarietyCount(data.getProductVarietyCount());
                    break;

                case WeightDetails.COMING_FOR_SITE:
                    dataBody.setNumberOfNets(data.getNumberOfNets());
                    dataBody.setSiteNetWeight(String.valueOf(data.getNetWeight()));
                    dataBody.setSiteTareWeight(data.getTareWeight());
                    dataBody.setSiteTotalTareWeight(data.getTotalTareWeight());
                    dataBody.setSiteTotalWeight(data.getTotalWeight());
                    dataBody.setSiteWeighmentDateTime(data.getDateTime());
                    dataBody.setVarietyCount(data.getProductVarietyCount());
                    break;
            }
            dataBodyList.add(dataBody);
        }
        if (dataList.size() > 0) {
//            totalNetWeightForEveryCount = Collections.max(totalWeight);

            txt_show_nets_number.setText("No. Of Nets\n" + String.valueOf(totalNumberOfNetsForEveryCount));
            txt_show_total_wt.setText("Total Weight\n" + Math.round((totalWeightForEveryCount) * 100.0) / 100.0 + " kgs");
            txt_show_net_total_wt.setText("Total Net Weight\n" + Math.round((totalNetWeightForEveryCount) * 100.0) / 100.0 + " kgs");
            SharedPrefrencesData.newInstance().saveString(mContext, String.valueOf(Math.round(totalWeightForEveryCount)), SharedPrefrencesData.TOTAL_WEIGHT_FOR_ALL_COUNT);

            Log.d("TotalWeight", "TotalNetWeight-->  " + totalNetWeightForEveryCount +
                    "\n TotalWeight--> " + totalWeightForEveryCount
                    + "\n Total numberOfNets --> " + totalNumberOfNetsForEveryCount);
        }

    }

    public List<WeightData> sortBy() {
        Log.d("ProductDataCount", "sortBy");
        Collections.sort(dataList, new Comparator<WeightData>() {
            @Override
            public int compare(WeightData lhs, WeightData rhs) {
                double lhsCount = Double.parseDouble(lhs.getProductVarietyCount());
                double rhsCount = Double.parseDouble(rhs.getProductVarietyCount());
                return (lhsCount < rhsCount) ? 1 : -1;
            }
        });

        return dataList;
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
                callInserService();
                break;
        }
    }

    /**
     * Method call service insert data
     */

    void callInserService() {

        if (AppUtils.isNetworkAvailable(mContext)) {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            siteWeighmentIsertService = new SiteWeighmentIsertService(mContext, this);
            siteWeighmentIsertService.getResponse(dataBodyList, activityStatus);
        } else {
            AppUtils.showToast(mContext, getString(R.string.error_network));
        }
    }

    @Override
    public void onSiteWeightSuccess(InsertSiteWeightmentResponse response) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if (response != null) {
            if (response.getResponseCode() == AppConstant.SUCCESS) {
                AppUtils.showCustomOkDialog(mContext, "", "Successfully uploaded ", "OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataBase.delete();
                        Intent intent = new Intent(mContext, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                AppUtils.showCustomOkDialog(mContext, "", response.getResponseMessage(), "OK", null);
            }
        } else {
            AppUtils.showCustomOkDialog(mContext, "", getResources().getString(R.string.error_default), "OK", null);
        }

    }

    @Override
    public void onSiteWeightFailure(Throwable error) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
    }
}
