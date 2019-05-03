package com.servicesprovider.jme.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.controller.HeadOnWeightInsertService;
import com.servicesprovider.jme.model.HeadLessWeightDataBody;
import com.servicesprovider.jme.model.HeadOnInsertResponse;
import com.servicesprovider.jme.model.HeadOnWeightDataBody;
import com.servicesprovider.jme.model.JmeDataBase;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.WeightData;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.view.adapters.InsertedDetailsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadOnInsertedDetails extends BaseActivity implements View.OnClickListener, HeadOnWeightInsertService.OnHeadOnInsertListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recycler_view_head_on_details)
    RecyclerView recyclerView;

    @BindView(R.id.tv_head_on_nets_number)
    TextView noOfNets;

    @BindView(R.id.tv_head_on_show_total_wt)
    TextView totalWeight;

    @BindView(R.id.tv_head_on_show_net_total_wt)
    TextView netWeight;

    @BindView(R.id.btnHeadOnUpload)
    FloatingActionButton btnSave;

    private List<WeightData> dataList = new ArrayList<>();
    private JmeDataBase dataBase;
    private InsertedDetailsAdapter insertedDetailsAdapter;
    private int totalNumberOfNetsForEveryCount = 0;
    private float totalWeightForEveryCount = 0;
    private float totalNetWeightForEveryCount = 0;
    private List<Double> totalWeightList = new ArrayList<>();
    private List<HeadOnWeightDataBody> dataBodyListHO = new ArrayList<>();
    private List<HeadLessWeightDataBody> dataBodyListHL = new ArrayList<>();
    private HeadOnWeightInsertService headOnWeighmentInsertService;
    private int FROM_ACTIVITY_STATUS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_on_inserted_details);
        ButterKnife.bind(this);

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText("Weight Details");
        }

        init();
    }

    private void init() {
        FROM_ACTIVITY_STATUS = getIntent().getIntExtra(AppConstant.FROM_ACTIVITY, 0);
        dataBase = new JmeDataBase(this);
        if (FROM_ACTIVITY_STATUS == 1) {
            dataList = dataBase.showHeadLessWeight();


        } else if (FROM_ACTIVITY_STATUS == 2) {
            dataList = dataBase.showHeadOnWeight();


        } else if (FROM_ACTIVITY_STATUS == 3) {
            dataList = dataBase.showHeadOnWeight();

        }else if (FROM_ACTIVITY_STATUS == 4) {
            dataList = dataBase.showHeadLessWeight();

        }


        /*if (checkFromHeadOn())
            dataList = dataBase.showHeadOnWeight();
        else
            dataList = dataBase.showHeadLessWeight();*/

        sortBy();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        insertedDetailsAdapter = new InsertedDetailsAdapter(this, dataList);
        recyclerView.setAdapter(insertedDetailsAdapter);

        //Calculate and Displays Weights
        calculateWeights();

        btnSave.setOnClickListener(this);
    }

    private boolean checkFromHeadOn() {
        boolean flag = false;
        switch (FROM_ACTIVITY_STATUS) {
            case AppConstant.FLAG_FROM_HEAD_ON:
                flag = true;
                break;

            case AppConstant.FLAG_FROM_HEAD_LESS:
                flag = false;
                break;

        }

        return flag;
    }

    private void calculateWeights() {
        // pass all data one array to server array
        for (WeightData data : dataList) {

            totalWeightList.add(data.getComulativeWeight());
            Log.d("TareWeight", "TotalWeight " + data.getTotalWeight() + " No of Nets " + data.getNumberOfNets());

            // sum of totalWeight and numberOfNets
            totalNumberOfNetsForEveryCount = totalNumberOfNetsForEveryCount + Integer.parseInt(data.getNumberOfNets());
            totalWeightForEveryCount = totalWeightForEveryCount + Float.parseFloat(data.getTotalWeight());
            totalNetWeightForEveryCount = totalNetWeightForEveryCount + data.getNetWeight();
            Log.i("ComingFROM", String.valueOf(FROM_ACTIVITY_STATUS));
            if (FROM_ACTIVITY_STATUS == 1) {
                HeadLessWeightDataBody dataBody = setHeadLessWeightBodyData(data);
                dataBodyListHL.add(dataBody);
            } else if (FROM_ACTIVITY_STATUS == 2) {
                HeadOnWeightDataBody dataBody = setHeadOnWeightBodyData(data);
                dataBodyListHO.add(dataBody);
            } else if (FROM_ACTIVITY_STATUS == 3) {
                HeadOnWeightDataBody dataBody = setHeadOnWeightBodyData(data);
                dataBodyListHO.add(dataBody);
            }else if (FROM_ACTIVITY_STATUS == 4) {
                HeadLessWeightDataBody dataBody = setHeadLessWeightBodyData(data);
                dataBodyListHL.add(dataBody);
            }

          /*  if (checkFromHeadOn()) {
                HeadOnWeightDataBody dataBody = setHeadOnWeightBodyData(data);
                dataBodyListHO.add(dataBody);
            } else {
                HeadLessWeightDataBody dataBody = setHeadLessWeightBodyData(data);
                dataBodyListHL.add(dataBody);

            }*/
        }

        if (dataList.size() > 0) {
            noOfNets.setText("No. Of Nets\n" + String.valueOf(totalNumberOfNetsForEveryCount));
            totalWeight.setText("Total Weight\n" + Math.round((totalWeightForEveryCount) * 100.0) / 100.0 + " kgs");
            netWeight.setText("Total Net Weight\n" + Math.round((totalNetWeightForEveryCount) * 100.0) / 100.0 + " kgs");
            Log.i("comingFrom", String.valueOf(FROM_ACTIVITY_STATUS));
            if (FROM_ACTIVITY_STATUS == 1) {
                SharedPrefrencesData.newInstance().saveString(this, String.valueOf(totalWeightForEveryCount), SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_LESS_FOR_ALL_COUNT);

            } else if (FROM_ACTIVITY_STATUS == 2) {
                SharedPrefrencesData.newInstance().saveString(this, String.valueOf(totalWeightForEveryCount), SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_ON_FOR_ALL_COUNT);

            } else if (FROM_ACTIVITY_STATUS == 3) {
                SharedPrefrencesData.newInstance().saveString(this, String.valueOf(totalWeightForEveryCount), SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_ON_FOR_ALL_COUNT);

            }else if (FROM_ACTIVITY_STATUS == 4) {
                SharedPrefrencesData.newInstance().saveString(this, String.valueOf(totalWeightForEveryCount), SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_LESS_FOR_ALL_COUNT);

            }

          /*  if (checkFromHeadOn())
                SharedPrefrencesData.newInstance().saveString(this, String.valueOf(totalWeightForEveryCount), SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_ON_FOR_ALL_COUNT);
            else
                SharedPrefrencesData.newInstance().saveString(this, String.valueOf(totalWeightForEveryCount), SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_LESS_FOR_ALL_COUNT);
       */
        }
    }

    private HeadLessWeightDataBody setHeadLessWeightBodyData(WeightData data) {
        HeadLessWeightDataBody dataBody = new HeadLessWeightDataBody();
        dataBody.setNumberOfNets(data.getNumberOfNets());
        dataBody.setHeadLessNetWeight(String.valueOf(data.getNetWeight()));
        dataBody.setHeadLessTareWeight(data.getTareWeight());

        Log.i("calculateWeights: ", data.getTareWeight());

        dataBody.setHeadLessTotalTareWeight(data.getTotalTareWeight());
        dataBody.setHeadLessTotalWeight(data.getTotalWeight());
        dataBody.setHeadLessWeighmentDateTime(data.getDateTime());
        dataBody.setVarietyCount(data.getProductVarietyCount());

        return dataBody;
    }

    private HeadOnWeightDataBody setHeadOnWeightBodyData(WeightData data) {
        HeadOnWeightDataBody dataBody = new HeadOnWeightDataBody();
        dataBody.setNumberOfNets(data.getNumberOfNets());
        dataBody.setHeadOnNetWeight(String.valueOf(data.getNetWeight()));
        dataBody.setHeadOnTareWeight(data.getTareWeight());

        Log.i("calculateWeights: ", data.getTareWeight());

        dataBody.setHeadOnTotalTareWeight(data.getTotalTareWeight());
        dataBody.setHeadOnTotalWeight(data.getTotalWeight());
        dataBody.setHeadOnWeighmentDateTime(data.getDateTime());
        dataBody.setVarietyCount(data.getProductVarietyCount());

        return dataBody;
    }

    public List<WeightData> sortBy() {
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
    public void onClick(View view) {
        if (view.getId() == R.id.btnHeadOnUpload)
            callInsertHeadOnService();
    }

    private void callInsertHeadOnService() {
        if (AppUtils.isNetworkAvailable(this)) {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            headOnWeighmentInsertService = new HeadOnWeightInsertService(HeadOnInsertedDetails.this, FROM_ACTIVITY_STATUS, this);

            if (FROM_ACTIVITY_STATUS == 1) {
                headOnWeighmentInsertService.getResponseHl(dataBodyListHL);

            } else if (FROM_ACTIVITY_STATUS == 2) {
                headOnWeighmentInsertService.getResponse(dataBodyListHO);

            } else if (FROM_ACTIVITY_STATUS == 3) {
                headOnWeighmentInsertService.getResponse(dataBodyListHO);
            }
            else if (FROM_ACTIVITY_STATUS == 4) {
                headOnWeighmentInsertService.getResponseHl(dataBodyListHL);
            }
          /*  if (checkFromHeadOn())
                headOnWeighmentInsertService.getResponse(dataBodyListHO);
            else
                headOnWeighmentInsertService.getResponseHl(dataBodyListHL);*/
        } else {
            AppUtils.showToast(HeadOnInsertedDetails.this, getString(R.string.error_network));
        }
    }

    @Override
    public void onHeadOnWeightSuccess(HeadOnInsertResponse response) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if (response != null) {
            if (response.getResponseCode() == AppConstant.SUCCESS) {
                AppUtils.showCustomOkDialog(HeadOnInsertedDetails.this, "", "Successfully uploaded ", "OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dataBase.deleteHeadOnDb();
                        Intent intent = new Intent(HeadOnInsertedDetails.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            } else {
                AppUtils.showCustomOkDialog(HeadOnInsertedDetails.this, "", response.getResponseMessage(), "OK", null);
            }
        } else {
            AppUtils.showCustomOkDialog(HeadOnInsertedDetails.this, "", getResources().getString(R.string.error_default), "OK", null);
        }
    }

    @Override
    public void onHeadOnWeightFailure(Throwable error) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
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
