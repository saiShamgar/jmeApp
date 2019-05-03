package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muheeb on 02-Apr-18.
 */

public class HeadOnWeightmentResponse
{
    @SerializedName("Code")
    @Expose
    private int responseCode;

    @SerializedName("Message")
    @Expose
    private String responseMessage;

    @SerializedName("Site_Weighment_Date_Time")
    @Expose
    private String lotDate;

    @SerializedName("Lot_No")
    @Expose
    private String lotNo;

    @SerializedName("Material_Group_Name")
    @Expose
    private String materialGroup;

    @SerializedName("Product_Variety_Name")
    @Expose
    private String varietyName;

    @SerializedName("LstVarietyCountCodes")
    @Expose
    private List<VarietyCount> varietyCountList = new ArrayList<>();

    private String recievedDate;

    public String getRecievedDate() {
        return recievedDate;
    }

    public void setRecievedDate(String recievedDate) {
        this.recievedDate = recievedDate;
    }

    public List<VarietyCount> getVarietyCountList() {
        return varietyCountList;
    }

    public void setVarietyCountList(List<VarietyCount> varietyCountList) {
        this.varietyCountList = varietyCountList;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
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

    public String getLotDate() {
        return lotDate;
    }

    public void setLotDate(String lotDate) {
        this.lotDate = lotDate;
    }

    public String getMaterialGroup() {
        return materialGroup;
    }

    public void setMaterialGroup(String materialGroup) {
        this.materialGroup = materialGroup;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public void setVarietyName(String varietyName) {
        this.varietyName = varietyName;
    }
}
