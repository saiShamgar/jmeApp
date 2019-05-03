package com.servicesprovider.jme.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.servicesprovider.jme.BaseActivity;
import com.servicesprovider.jme.R;
import com.servicesprovider.jme.model.SharedPrefrencesData;

public class SplashScreen extends BaseActivity {

    private static int SPLASH_TIME_OUT = 1500;
    private Context mContext;
    private int status;
    private Intent browserIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = SplashScreen.this;

//        status = GooglePlayServicesUtil.isGooglePlayServicesAvailable( getApplicationContext());
//        splashScreen();

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        status = googleAPI.isGooglePlayServicesAvailable(this);


        }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayService();
        Log.d("playserviceStatus",""+status);
    }

    /**
     *  Method to check play service
     */
    void checkPlayService(){

            switch (status){
                case ConnectionResult.SUCCESS:

                    try {
                        splashScreen();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    break;
                case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:

                    AlertDialog alertDialog=new AlertDialog.Builder(mContext).setMessage("Please Update Your Google Play Service to Continue")
                            .setCancelable(false)
                            .setPositiveButton("Update Play Service", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.gms&hl=en"));
                                    startActivity(browserIntent);
                                    dialog.dismiss();
                                }
                            }).show();

                    break;
                case ConnectionResult.CANCELED:
                    break;
            }

        }
    public void splashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Log.i( "run: ", "Here 1");
                Intent Loging;
                if (SharedPrefrencesData.newInstance().getBoolean(mContext,
                        SharedPrefrencesData.LOGIN_CHECK))
                {
                    Loging = new Intent(mContext, MenuActivity.class);
                    Log.i( "run: ", "Here 2");
                } else {
                    Loging = new Intent(mContext, LoginActivity.class);
                    Log.i( "run: ", "Here 3");
                }

//                startActivity(new Intent(mContext, LoginActivity.class));
                startActivity(Loging);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
