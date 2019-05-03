package com.servicesprovider.jme.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.EvenBusClass.Events;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.controller.AgentDetailsService;
import com.servicesprovider.jme.controller.FactoryScheduleNumberService;
import com.servicesprovider.jme.controller.FactoryWeighmentService;
import com.servicesprovider.jme.controller.ScheduleNumberService;
import com.servicesprovider.jme.controller.SiteWeighmentService;
import com.servicesprovider.jme.model.AgentDetailsResponse;
import com.servicesprovider.jme.model.EnquiryNumber;
import com.servicesprovider.jme.model.JmeDataBase;
import com.servicesprovider.jme.model.ProductCount;
import com.servicesprovider.jme.model.SaveDataSingletone;
import com.servicesprovider.jme.model.ScheduleNumber;
import com.servicesprovider.jme.model.ScheduleResponse;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.model.SiteWeighmentResponse;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.utils.GpsLocation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactoryWeighment extends BaseActivity implements View.OnClickListener, FactoryScheduleNumberService.OnFactoryScheduleListener,
        FactoryWeighmentService.OnFactoryWeighmentListener, AgentDetailsService.OnAgentdetailsListener, AppUtils.OnSelectedMyItem {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.txt_save)
    TextView txt_save;
    @BindView(R.id.txt_next)
    TextView txt_next;
    @BindView(R.id.txt_new)
    TextView txt_new;
    @BindView(R.id.ed_pond_no)
    EditText ed_pond_no;
    @BindView(R.id.ed_farm_location)
    EditText ed_farm_location;
    @BindView(R.id.spinner_schedule)
    Spinner spinner_schedule;
    @BindView(R.id.spinner_enquiry)
    Spinner spinner_enquiry;
    @BindView(R.id.schdule_spinner_layout)
    LinearLayout schdule_spinner_layout;
    @BindView(R.id.enquiry_spinner_layout)
    LinearLayout enquiry_spinner_layout;
    @BindView(R.id.txt_schedule_date)
    TextView txt_schedule_date;
    @BindView(R.id.txt_lot_number)
    TextView txt_lot_number;
    @BindView(R.id.txt_grader_name)
    TextView txt_grader_name;

    @BindView(R.id.txt_schedule_number)
    TextView txt_schedule_number;
    @BindView(R.id.txt_enquiry_number)
    TextView txt_enquiry_number;
    @BindView(R.id.txt_helper_name_first)
    TextView txt_helper_name_first;

    @BindView(R.id.txt_helper_name_two)
    TextView txt_helper_name_two;
    @BindView(R.id.txt_place)
    TextView txt_place;
    @BindView(R.id.txt_vehicle_number)
    TextView txt_vehicle_number;
    @BindView(R.id.txt_agent_name)
    TextView txt_agent_name;
    @BindView(R.id.txt_farmer_name)
    TextView txt_farmer_name;
    @BindView(R.id.txt_gps_location)
    TextView txt_gps_location;

    @BindView(R.id.txt_number_box)
    TextView txt_number_box;
    @BindView(R.id.txt_number_nets)
    TextView txt_number_nets;
    @BindView(R.id.txt_material_group)
    TextView txt_material_group_name;
    @BindView(R.id.txt_variety_name)
    TextView txt_variety_name;

    @BindView(R.id.txt_total_weight)
    TextView txt_total_weight;

    private Context mContext;
    private boolean validate;
    private JmeDataBase dataBase;

    private ArrayAdapter<String> scheduleAdapter;
    private List<String> scheduleNumberList = new ArrayList<>();
    private ArrayAdapter<String> enquiryAdapter;
    private List<String> enquriryList = new ArrayList<>();
    private List<String> varietyCountList = new ArrayList<>();

    private FactoryScheduleNumberService scheduleNumberService = null;
    private FactoryWeighmentService siteWeighmentService = null;
    private AgentDetailsService agentDetailsService = null;

    private SharedPrefrencesData prefrencesData = null;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_weighment);
        from = getIntent().getExtras().getString("from");

        ButterKnife.bind(this);
        mContext = FactoryWeighment.this;

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            if (from.equalsIgnoreCase("factoryweighment")) {
                toolbar_title.setText("Factory Weight - Weighing Scale");

            } else {
                toolbar_title.setText("Factory Weight – Manual");

            }
        }

        init();
    }

    void init() {

        dataBase = new JmeDataBase(mContext);
        prefrencesData = SharedPrefrencesData.newInstance();

        startService(new Intent(mContext, GpsLocation.class));

        txt_new.setOnClickListener(this);
        txt_next.setOnClickListener(this);
        txt_save.setOnClickListener(this);

        schdule_spinner_layout.setOnClickListener(this);
        enquiry_spinner_layout.setOnClickListener(this);

//        spinner_schedule.setOnItemSelectedListener(this);
//        spinner_enquiry.setOnItemSelectedListener(this);

        prefrencesData.saveBoolean(mContext, false, SharedPrefrencesData.COMING_FROM_WEIGHT_DEATILS_ACTIVITY);
        prefrencesData.saveString(mContext, "", SharedPrefrencesData.TOTAL_WEIGHT_FOR_ALL_COUNT);
        callScheduleService();

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
        txt_gps_location.setText(message.getAddress());
        Log.d("EventMessage", message.getLatitude() + " Time " + message.getTime());

    }


    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch (v.getId()) {
            case R.id.txt_save:
                if (doValidation()) {
                    SharedPrefrencesData.newInstance().saveBoolean(mContext, false, SharedPrefrencesData.WEIGHT_LOADED);
                    SaveDataSingletone.getInstance().setComulativeWeight(0.0);
                    SharedPrefrencesData.newInstance().saveListData(mContext, null, SharedPrefrencesData.WEIGHT_DATA_LIST);
                    dataBase.delete();
                }
                AppUtils.showToast(mContext, "clicked");
                break;
            case R.id.txt_next:
                if (doValidation()) {
                    saveServerData();
                    dataBase.delete();
                    if (from.equalsIgnoreCase("factoryweighment")) {
                        WeightDetails.openWeightDetalisActivity(mContext, WeightDetails.COMING_FOR_FACTORY);

                    } else {
                        WeightDetails.openWeightDetalisActivity(mContext, WeightDetails.COMING_FOR_FACTORY_MANUAL);

                    }
//                    startActivity(new Intent(mContext,WeightDetails.class));
                }
                break;
            case R.id.txt_new:
                AppUtils.showToast(mContext, "clicked");
                break;
            case R.id.schdule_spinner_layout:
//                popUpMenu();
                AppUtils.showAlertDailog(mContext, scheduleNumberList, txt_schedule_number, this);
                break;
            case R.id.enquiry_spinner_layout:
                AppUtils.showAlertDailog(mContext, enquriryList, txt_enquiry_number, this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    boolean doValidation() {
        validate = true;
        if (txt_schedule_number.getText().toString().trim().equalsIgnoreCase("") || txt_schedule_number.getText().toString().trim().equalsIgnoreCase("Select Schedule")) {
            validate = false;
            txt_schedule_number.setBackground(ContextCompat.getDrawable(mContext, R.color.color_red));
            AppUtils.showToast(mContext, "Select Schedule Number");
        } else if (txt_enquiry_number.getText().toString().trim().equalsIgnoreCase("") || txt_enquiry_number.getText().toString().trim().equalsIgnoreCase("Select Enquiry")) {
            validate = false;
            AppUtils.showToast(mContext, "Select Enquiry Number");

            txt_enquiry_number.setBackground(ContextCompat.getDrawable(mContext, R.color.color_red));
        } else if (ed_pond_no.getText().toString().trim().length() == 0) {
            validate = false;
            ed_pond_no.setError("Enter Pond Number");
            ed_pond_no.requestFocus();

        } else if (ed_farm_location.getText().toString().trim().length() == 0) {
            validate = false;
            ed_farm_location.requestFocus();
            ed_farm_location.setError("Enter Farm Location");
        }

        return validate;
    }

    /**
     * method to save all view data
     */

    public void saveServerData() {
        prefrencesData.saveString(mContext, txt_schedule_number.getText().toString().trim(), SharedPrefrencesData.SCHEDULE_NUMBER);
        prefrencesData.saveString(mContext, txt_enquiry_number.getText().toString().trim(), SharedPrefrencesData.ENQUIRY_NUMBER);
        prefrencesData.saveString(mContext, txt_lot_number.getText().toString().trim(), SharedPrefrencesData.LOT_NUMBER);
        prefrencesData.saveString(mContext, txt_agent_name.getText().toString().trim(), SharedPrefrencesData.AGENT_NAME);
        prefrencesData.saveString(mContext, txt_farmer_name.getText().toString().trim(), SharedPrefrencesData.FARMER_NAME);
        prefrencesData.saveString(mContext, ed_pond_no.getText().toString().trim(), SharedPrefrencesData.FARMER_POND_NUMBER);
        prefrencesData.saveString(mContext, ed_farm_location.getText().toString().trim(), SharedPrefrencesData.FARMER_LOCATION);
        prefrencesData.saveString(mContext, txt_material_group_name.getText().toString().trim(), SharedPrefrencesData.MATERIAL_GROUP_NAME);
        prefrencesData.saveString(mContext, txt_variety_name.getText().toString().trim(), SharedPrefrencesData.PRODUCT_VARIETY_NAME);
    }

    void callScheduleService() {
        if (AppUtils.isNetworkAvailable(mContext)) {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            scheduleNumberService = new FactoryScheduleNumberService(mContext, this);
            scheduleNumberService.getResponse();

        } else {
            AppUtils.showToast(mContext, getString(R.string.error_network));
        }
    }

    void callWeighmentService(String scheduleNos) {
        if (AppUtils.isNetworkAvailable(mContext)) {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            siteWeighmentService = new FactoryWeighmentService(mContext, this);
            siteWeighmentService.getResponse(scheduleNos);
        } else {
            AppUtils.showToast(mContext, getString(R.string.error_network));
        }
    }

    void callAgentService(String enquiryNos) {
        if (AppUtils.isNetworkAvailable(mContext)) {
            AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");
            agentDetailsService = new AgentDetailsService(mContext, this);
            agentDetailsService.getResponse(enquiryNos, "2");
        } else {
            AppUtils.showToast(mContext, getString(R.string.error_network));
        }
    }

    @Override
    public void onScheduleSuccess(ScheduleResponse response) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        Log.i("response", String.valueOf(response));

        if (response != null) {
            Log.d("ComingResponse", "" + 1);
            if (response.getResponseCode() == AppConstant.SUCCESS) {
                scheduleNumberList.clear();
                scheduleNumberList.add(0, "Select Schedule");
                for (ScheduleNumber scheduleNumber : response.getScheduleList()) {
                    scheduleNumberList.add(scheduleNumber.getScheduleNumber());
                }
                Log.d("ComingResponse", "" + 2);
//                AppUtils.showAlertDailog(mContext,scheduleNumberList,txt_enquiry_number);
//                scheduleAdapter = new ArrayAdapter<String>(this,R.layout.name_list_row,scheduleNumberList);
//                spinner_schedule.setAdapter(scheduleAdapter);
            } else {
                AppUtils.showCustomOkDialog(mContext, "", response.getResponseMessage(), "OK", null);
            }
        } else {
            Log.d("ComingResponse", "" + 3);
            AppUtils.showCustomOkDialog(mContext, "", getResources().getString(R.string.error_default), "OK", null);
        }
    }

    @Override
    public void onScheduleFailure(Throwable error) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        Log.d("ComingResponse", "" + 4);
        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    @Override
    public void onWeighmentSuccess(SiteWeighmentResponse response) {

        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if (response != null) {
            if (response.getResponseCode() == AppConstant.SUCCESS) {

                enquriryList.clear();
                enquriryList.add(0, "Select Enquiry");
                for (EnquiryNumber enquiryNumber : response.getEnquiryNumberList()) {
                    enquriryList.add(enquiryNumber.getEnquriyNumber());
                }
                // set UI according to coming data
//                enquiryAdapter = new ArrayAdapter<String>(this,R.layout.name_list_row,enquriryList);
//                spinner_enquiry.setAdapter(enquiryAdapter);

                txt_schedule_date.setText(AppUtils.dateFormat(response.getProcurementSchduleDate()));
                txt_grader_name.setText(response.getGraderEmpName());
                txt_helper_name_first.setText(response.getHelperEmpNameOne());
                txt_helper_name_two.setText(response.getHelperEmpNameTwo());
                txt_vehicle_number.setText(response.getVechileID());
                txt_number_box.setText(response.getBoxRequired());
                txt_number_nets.setText(response.getNetsRequired());
//                AppUtils.showAlertDailog(mContext,enquriryList,txt_schedule_number);

            } else {
                AppUtils.showCustomOkDialog(mContext, "", response.getResponseMessage(), "OK", null);
            }
        } else {
            AppUtils.showCustomOkDialog(mContext, "", getResources().getString(R.string.error_default), "OK", null);
        }
    }

    @Override
    public void onWeighmentFailure(Throwable error) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
    }

    @Override
    public void onAgentSuccess(AgentDetailsResponse response) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);
        if (response != null) {
            if (response.getResponseCode() == AppConstant.SUCCESS) {
                varietyCountList.clear();
                varietyCountList.add(0, "Select");
                for (ProductCount productCount : response.getProductVarietyCountList()) {
                    varietyCountList.add(productCount.getVarietyCounts());
                }
                prefrencesData.saveStringList(mContext, varietyCountList, SharedPrefrencesData.VARIETY_COUNT_LIST);
                txt_farmer_name.setText(response.getFarmerName());
                txt_agent_name.setText(response.getAgentName());
                txt_place.setText(response.getPlaceName());
                txt_material_group_name.setText(response.getMaterialGroupName());
                txt_variety_name.setText(response.getProduct_Variety_Name());
                txt_lot_number.setText(response.getLotNumber());
            } else {
                AppUtils.showCustomOkDialog(mContext, "", response.getResponseMessage(), "OK", null);
            }
        } else {
            AppUtils.showCustomOkDialog(mContext, "", getResources().getString(R.string.error_default), "OK", null);
        }
    }

    @Override
    public void onAgentFailure(Throwable error) {
        AppUtils.dismissCustomProgress(mCustomProgressDialog);

        AppUtils.showCustomOkDialog(this,
                "",
                getString(R.string.error_default),
                "OK", null);
    }

    @Override
    public void onSelectionItem(View view) {
        switch (view.getId()) {
            case R.id.txt_schedule_number:
                if (!txt_schedule_number.getText().toString().trim().equalsIgnoreCase("Select Schedule")) {
                    AppUtils.showToast(mContext, txt_schedule_number.getText().toString());
                    txt_schedule_number.setBackground(ContextCompat.getDrawable(mContext, R.color.color_transparent));
                    callWeighmentService(txt_schedule_number.getText().toString());
                }
                break;
            case R.id.txt_enquiry_number:
                if (!txt_enquiry_number.getText().toString().trim().equalsIgnoreCase("Select Enquiry")) {
                    AppUtils.showToast(mContext, txt_enquiry_number.getText().toString());
                    txt_enquiry_number.setBackground(ContextCompat.getDrawable(mContext, R.color.color_transparent));
                    callAgentService(txt_enquiry_number.getText().toString());
                }

                break;
        }
    }
}
