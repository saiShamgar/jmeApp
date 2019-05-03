package com.servicesprovider.jme;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.servicesprovider.jme.utils.CustomProgressDialog;

public class BaseActivity extends AppCompatActivity {

    public CustomProgressDialog mCustomProgressDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomProgressDialog = new CustomProgressDialog(this , false);
    }

    /**
     * Method to return the CustomProgressDialog.
     * @return
     */
    public CustomProgressDialog getCustomProgressDialog(){

        return this.mCustomProgressDialog;
    }

}
