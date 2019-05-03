package com.servicesprovider.jme.utils;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.servicesprovider.jme.EvenBusClass.Events;
import com.servicesprovider.jme.model.SharedPrefrencesData;


import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * Created by Akash
 */
public class GpsLocation extends Service implements LocationListener {

    private final Context mContext;

    PendingIntent pendingIntent;

    private SharedPrefrencesData sharedPrefrencesData;
    private Geocoder geocoder;
    private List<Address> addresses;
    private Address returnAddress;
    AlarmManager alarmManager;
    Intent alarmIntent;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;


    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
//    private static final long MIN_TIME_BW_UPDATES = 10000; // 2 Second
    private static final long MIN_TIME_BW_UPDATES = 1000 * 5; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GpsLocation(Context context) {
        this.mContext = context;
    }

    public GpsLocation() {
//        mContext = getApplicationContext();
        mContext = this;
        Log.d("myLocation", "GpsLocation constructor called");
//        getLocation();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            Log.d("myLocation", "Gps " + isGPSEnabled + " Newrk " + isNetworkEnabled);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;

                // First get location from GPS Provider

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }

                }

                if (isNetworkEnabled) {
                    Log.d("myLocation", "isNetworkEnabled " + isGPSEnabled + " Newrk " + isNetworkEnabled);
                    if (location == null) {

                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("myLocation", "exception " + e);
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsLocation.this);
        }
    }

    /**
     * method for stop service
     */

    public void stopRunningService() {

        alarmIntent = new Intent(mContext, GpsLocation.class);
        pendingIntent = PendingIntent.getService(mContext, 0, alarmIntent, 0);
        pendingIntent.cancel();
        stopSelf();
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
//        Log.d("myLocation","GPSLOCATION "+location.getLatitude()+","+location.getLongitude()+" provider "+location.getProvider());

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("myLocation", "onLocationChanged " + location.getProvider()+latitude+" time "+AppUtils.timeFormat(location.getTime()));

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("myLocation", "Provider Disabled");
    }

    @Override
    public void onProviderEnabled(String provider) {

        Log.d("myLocation", "Provider Enabled");

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("myLocation", "onStatusChanged " + provider);

    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d("myLocation", "IBinder onBind");

        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        userLocationService = new UserLocationService();
        sharedPrefrencesData = SharedPrefrencesData.newInstance();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.d("myLocation", "Service onStart");
        LocationManager manager;
        String userAddress = " ";
        long interval = 5 * 1000;

//        if (!SharedPrefrencesData.newInstance().getBoolean(this, SharedPrefrencesData.LOGOUT_CHECK)) {
            alarmIntent = new Intent(this, GpsLocation.class);
            pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            getLocation(); // calling location

            geocoder = new Geocoder(this, Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 5);
//                addresses = geocoder.getFromLocation(12.9066,77.6309,1); // bangalore
            } catch (IOException e) {
                e.printStackTrace();
            }
        Log.d("myLocation", "Service "+latitude);
            if (geocoder.isPresent()) {
                if (addresses != null) {
                    try {
                        returnAddress = addresses.get(0);
                        userAddress = returnAddress.getAddressLine(0) + " " + returnAddress.getAddressLine(1) + " " + returnAddress.getAddressLine(2) + ", " + returnAddress.getCountryName();
                        Log.d("CurrentLocation", "string add " + userAddress);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else {
                Log.d("UserAddress", "GeoCoder no available");
            }

        EventBus.getDefault().post(new Events.ActivityServiceMessage(String.valueOf(latitude),String.valueOf(longitude),userAddress,AppUtils.convertServerFormatDateTime(calendar)));

//        if (location != null){
//            Log.d("CurrentLocation", "lat:" + latitude + " lang:" + longitude + " Time: " + AppUtils.timeFormat(location.getTime()));
//            EventBus.getDefault().post(new Events.ActivityServiceMessage(String.valueOf(latitude),String.valueOf(longitude),userAddress,AppUtils.timeFormat(location.getTime())));
//        }

            // Managing alarm for 5 second after 5 second service will restart
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + interval - SystemClock.elapsedRealtime() % 1000, pendingIntent);
//        }
//        else {
//            stopSelf(); // stop service
//            Log.d("itsMe", " service stopped: userLogout--> " + SharedPrefrencesData.newInstance().getBoolean(mContext,
//                    SharedPrefrencesData.LOGOUT_CHECK));
//        }
        return Service.START_STICKY;

    }

}