package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/29/2018.
 */

public class InsertSiteWeightmentResponse {
    @SerializedName("Code")
    @Expose
    private int responseCode;

    @SerializedName("Message")
    @Expose
    private String responseMessage;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
