package com.servicesprovider.jme.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import com.servicesprovider.jme.controller.HoHlWeighmentInsertService;
import com.servicesprovider.jme.model.HeadOnInsertResponse;
import com.servicesprovider.jme.model.HoHlWeightData;
import com.servicesprovider.jme.model.HoHlWeightDataBody;
import com.servicesprovider.jme.model.JmeDataBase;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.view.adapters.HoHlInsertedDetailsAdapter;
import com.servicesprovider.jme.view.adapters.InsertedDetailsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeadOnHeadLessInsertedDetails extends BaseActivity implements View.OnClickListener, HoHlWeighmentInsertService.OnHeadOnHeadLessInsertListener
{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.recycler_view_ho_hl_details)
    RecyclerView recyclerView;

    @BindView(R.id.tv_ho_hl_nets_number)
    TextView noOfNets;

    @BindView(R.id.tv_ho_hl_show_total_wt)
    TextView totalWeight;

    @BindView(R.id.tv_ho_hl_show_net_total_wt)
    TextView netWeight;

    @BindView(R.id.btnHoHlUpload)
    FloatingActionButton btnSave;
    private JmeDataBase dataBase;
    private List<HoHlWeightData> dataList;
    private HoHlInsertedDetailsAdapter insertedDetailsAdapter;
    private List<Double> totalWeightList = new ArrayList<>();
    private int totalNumberOfNetsForEveryCount = 0;
    private float totalWeightForEveryCount = 0;
    private float totalNetWeightForEveryCount = 0;
    private List<HoHlWeightDataBody> dataBodyListHOHL = new ArrayList<>();
    private HoHlWeighmentInsertService hohlWeighmentInsertService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_on_head_less_inserted_details);
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

    private void init()
    {

        dataBase = new JmeDataBase(this);
        dataList = dataBase.showHeadOnHeadLessWeight();

        sortBy();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        insertedDetailsAdapter = new HoHlInsertedDetailsAdapter(this,dataList);
        recyclerView.setAdapter(insertedDetailsAdapter);

        //Calculate and Displays Weights
        calculateWeights();

        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.btnHoHlUpload)
            callHoHlInsertService();
    }

    private void callHoHlInsertService()
    {
        if (AppUtils.isNetworkAvailable(this))
        {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            hohlWeighmentInsertService = new HoHlWeighmentInsertService(HeadOnHeadLessInsertedDetails.this, this);
            hohlWeighmentInsertService.getResponse(dataBodyListHOHL);
        }else {
            AppUtils.showToast(HeadOnHeadLessInsertedDetails.this,getString(R.string.error_network));
        }
    }


    private void calculateWeights()
    {
        // pass all data one array to server array
        for(HoHlWeightData data : dataList)
        {

            totalWeightList.add(data.getComulativeWeight());
            Log.d("TareWeight","TotalWeight "+data.getTotalWeight()+" No of Nets "+data.getNumberOfNets());

            // sum of totalWeight and numberOfNets
            totalNumberOfNetsForEveryCount = totalNumberOfNetsForEveryCount + Integer.parseInt(data.getNumberOfNets());
            totalWeightForEveryCount = totalWeightForEveryCount + Float.parseFloat(data.getTotalWeight());
            totalNetWeightForEveryCount = totalNetWeightForEveryCount + data.getNetWeight();

            HoHlWeightDataBody dataBody = setHOHLWeightBodyData(data);
            dataBodyListHOHL.add(dataBody);
        }

        if (dataList.size() > 0)
        {
            noOfNets.setText("No. Of Nets\n"+String.valueOf(totalNumberOfNetsForEveryCount));

            totalWeight.setText("Total Weight\n"+String.valueOf(Math.round((totalWeightForEveryCount)*100.0)/100.0)+" kgs");
            netWeight.setText("Total Net Weight\n"+String.valueOf(Math.round((totalNetWeightForEveryCount)*100.0)/100.0)+" kgs");
            SharedPrefrencesData.newInstance().saveString(this,String.valueOf(totalWeightForEveryCount), SharedPrefrencesData.TOTAL_WEIGHT_OF_HEAD_ON_HEAD_LESS_FOR_ALL_COUNT);
        }
    }

    private HoHlWeightDataBody setHOHLWeightBodyData(HoHlWeightData data)
    {
        HoHlWeightDataBody dataBody = new HoHlWeightDataBody();
        dataBody.setNumberOfNets(data.getNumberOfNets());
        dataBody.setHohlNetWeight(String.valueOf(data.getNetWeight()));
        dataBody.setHohlTareWeight(data.getTareWeight());

        Log.i("calculateWeights: ", data.getTareWeight());

        dataBody.setHohlTotalTareWeight(data.getTotalTareWeight());
        dataBody.setHohlTotalWeight(data.getTotalWeight());
        dataBody.setHohlWeighmentDateTime(data.getDateTime());
        dataBody.setVarietyCount(data.getProductVarietyCount());
        dataBody.setPersonName(data.getPersonName());
        dataBody.setTeamNo(data.getTeamNo());
        return dataBody;
    }

    public List<HoHlWeightData> sortBy()
    {
        Collections.sort(dataList, new Comparator<HoHlWeightData>() {
            @Override
            public int compare(HoHlWeightData lhs, HoHlWeightData rhs) {
                double lhsCount = Double.parseDouble(lhs.getProductVarietyCount());
                double rhsCount = Double.parseDouble(rhs.getProductVarietyCount());
                return (lhsCount < rhsCount)? 1: -1;
            }
        });

        return dataList;
    }

    @Override
    public void onHOHLWeightSuccess(HeadOnInsertResponse response) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if(response != null){
            if (response.getResponseCode() == AppConstant.SUCCESS)
            {
                AppUtils.showCustomOkDialog(HeadOnHeadLessInsertedDetails.this, "", "Successfully uploaded ", "OK", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dataBase.deleteHeadOnHeadLessDb();
                        Intent intent = new Intent(HeadOnHeadLessInsertedDetails.this, MenuActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }else {
                AppUtils.showCustomOkDialog(HeadOnHeadLessInsertedDetails.this, "", response.getResponseMessage(), "OK", null);
            }
        }else {
            AppUtils.showCustomOkDialog(HeadOnHeadLessInsertedDetails.this,"",getResources().getString(R.string.error_default),"OK",null);
        }
    }

    @Override
    public void onHOHLWeightFailure(Throwable error)
    {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
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
