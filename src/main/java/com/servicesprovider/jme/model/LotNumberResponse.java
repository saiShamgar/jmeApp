package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muheeb on 02-Apr-18.
 */

public class LotNumberResponse
{
    @SerializedName("Code")
    @Expose
    private int responseCode;

    @SerializedName("Message")
    @Expose
    private String responseMessage;

    @SerializedName("LstLotNumbers")
    @Expose
    private List<LotNumber> lotNumberList = new ArrayList<>();

    public List<LotNumber> getLotNumberList() {
        return lotNumberList;
    }

    public void setLotNumberList(List<LotNumber> lotNumberList) {
        this.lotNumberList = lotNumberList;
    }

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
