package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/26/2018.
 */

public class EnquiryNumber {
    @SerializedName("EnquiryNos")
    @Expose
    private String enquriyNumber;

    public String getEnquriyNumber() {
        return enquriyNumber;
    }

    public void setEnquriyNumber(String enquriyNumber) {
        this.enquriyNumber = enquriyNumber;
    }
}

