package com.servicesprovider.jme.view;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.model.SharedPrefrencesData;
import com.servicesprovider.jme.utils.AppConstant;
import com.servicesprovider.jme.utils.AppUtils;
import com.servicesprovider.jme.utils.GpsLocation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends BaseActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
// toolbar

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar_version)
    TextView app_version;

    @BindView(R.id.img_site_weight)
    ImageView img_site_weight;
    //@BindView(R.id.img_loading_state)
    ImageView img_loading_st;
    @BindView(R.id.img_ho_pre)
    ImageView img_head_on_pre;

    @BindView(R.id.img_ho_pre_manual)
    ImageView img_head_on_pre_manual;

    @BindView(R.id.img_hl_pre)
    ImageView img_head_less_pre;

    @BindView(R.id.img_hl_pre_manual)
    ImageView img_head_less_pre_manual;

    @BindView(R.id.img_factory)
    ImageView img_factory;

    @BindView(R.id.img_factory_manual)
    ImageView img_factory_manual;
    //@BindView(R.id.img_indent)
    ImageView img_indent;


    @BindView(R.id.img_ho_hl)
    ImageView img_hl_ho;

    @BindView(R.id.img_ho_hl_manual)
    ImageView img_hl_ho_manual;

    private Context mContext;
    private GoogleApiClient googleApiClient = null;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    private ImageView img_value_edition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        mContext = MenuActivity.this;

        img_value_edition=findViewById(R.id.img_value_edition);

        Log.i("run: ", "Here 5");

        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setIcon(R.drawable.ic_menu);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar_title.setText("HOME");
            app_version.setVisibility(View.VISIBLE);
            app_version.setText("v " + AppUtils.getAppVersion(mContext));
        }


        checkLocation();
        init();
    }

    void init() {
        enableGps();
        startService(new Intent(mContext, GpsLocation.class));
        img_site_weight.setOnClickListener(this);
        // img_loading_st.setOnClickListener(this);
        img_head_on_pre.setOnClickListener(this);
        img_head_on_pre_manual.setOnClickListener(this);
        img_head_less_pre.setOnClickListener(this);
        img_head_less_pre_manual.setOnClickListener(this);
        img_factory.setOnClickListener(this);
        img_factory_manual.setOnClickListener(this);
        img_value_edition.setOnClickListener(this);

//        img_indent.setOnClickListener(this);

        img_hl_ho.setOnClickListener(this);
        img_hl_ho_manual.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_site_weight:
//                startActivity(new Intent(mContext,SiteWeight.class));
                startActivity(new Intent(mContext, SiteWeighment.class));
                break;
            // case R.id.img_loading_state:
//                startActivity(new Intent(mContext,FirstActivity.class));
            // break;
            case R.id.img_factory:
                startActivity(new Intent(mContext, FactoryWeighment.class).putExtra("from", "factoryweighment"));
                break;
            case R.id.img_factory_manual:
                startActivity(new Intent(mContext, FactoryWeighment.class).putExtra("from", "factoryweighmentmanual"));
                break;

            case R.id.img_ho_pre:
                startActivity(new Intent(mContext, HeadOnWeight.class).putExtra(AppConstant.FROM_ACTIVITY, AppConstant.FLAG_FROM_HEAD_ON));
                break;
            case R.id.img_ho_pre_manual:
                startActivity(new Intent(mContext, HeadOnWeight.class).putExtra(AppConstant.FROM_ACTIVITY, AppConstant.FLAG_FROM_HEAD_ON_MANUAL));
                break;

            case R.id.img_hl_pre:
                startActivity(new Intent(mContext, HeadOnWeight.class).putExtra(AppConstant.FROM_ACTIVITY, AppConstant.FLAG_FROM_HEAD_LESS));
                break;

            case R.id.img_hl_pre_manual:
                startActivity(new Intent(mContext, HeadOnWeight.class).putExtra(AppConstant.FROM_ACTIVITY, AppConstant.FLAG_FROM_HEAD_LESS_MANUAL));
                break;

            //    case R.id.img_indent:

            //   break;


            case R.id.img_ho_hl:
                startActivity(new Intent(mContext, HeadOnHeadLess.class).putExtra(AppConstant.FROM_ACTIVITY,AppConstant.FLAG_FROM_HEAD_ON_HEAD_LESS));
                break;

            case R.id.img_ho_hl_manual:
                startActivity(new Intent(mContext, HeadOnHeadLess.class).putExtra(AppConstant.FROM_ACTIVITY,AppConstant.FLAG_FROM_HEAD_ON_HEAD_LESS_MANUAL));
                break;

            case R.id.img_value_edition:



        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                enableGps();
            } else {
                checkLocation();
            }
        }

    }

    void checkLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 2);
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void enableGps() {

        googleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient


        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MenuActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logout:
                AppUtils.showCustomOkCancelDialog(mContext, "", "Do you want to logout ?", "Yes", "No",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SharedPrefrencesData.newInstance().saveBoolean(mContext,
                                        false, SharedPrefrencesData.LOGIN_CHECK);

                                Intent intentLandingPage = new Intent(mContext, LoginActivity.class);
                                intentLandingPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentLandingPage);
                                finish();

                            }
                        }, null);

                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
//                        startLocationUpdates();
                        checkLocation();
                        Log.d("myLocation", "onActivityResult");
                        break;
                    case Activity.RESULT_CANCELED:
//                        settingsrequest();//keep asking if imp or do whatever
//                        finish();
                        AppUtils.showToast(mContext, "Please turn on location");
                        enableGps();
                        break;
                }
                break;
        }
    }
}
