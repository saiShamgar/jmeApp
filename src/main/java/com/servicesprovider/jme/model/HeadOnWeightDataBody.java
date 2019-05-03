package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muheeb on 04-Apr-18.
 */

public class HeadOnWeightDataBody
{
    @SerializedName("HOGR_Weighment_Date_Time")
    @Expose
    private String headOnWeighmentDateTime;
    @SerializedName("HOG_No_of_Nets")
    @Expose
    private String numberOfNets;

    @SerializedName("HOG_Total_Weight")
    @Expose
    private String headOnTotalWeight;

    @SerializedName("HOG_Tare_Weight")
    @Expose
    private String headOnTareWeight;

    @SerializedName("HOG_Total_Tare_Weight")
    @Expose
    private String headOnTotalTareWeight;

    @SerializedName("HOG_Net_Weight")
    @Expose
    private String headOnNetWeight;

    @SerializedName("Variety_Count")
    @Expose
    private String varietyCount;

    public String getHeadOnWeighmentDateTime() {
        return headOnWeighmentDateTime;
    }

    public void setHeadOnWeighmentDateTime(String headOnWeighmentDateTime) {
        this.headOnWeighmentDateTime = headOnWeighmentDateTime;
    }

    public String getNumberOfNets() {
        return numberOfNets;
    }

    public void setNumberOfNets(String numberOfNets) {
        this.numberOfNets = numberOfNets;
    }

    public String getHeadOnTotalWeight() {
        return headOnTotalWeight;
    }

    public void setHeadOnTotalWeight(String headOnTotalWeight) {
        this.headOnTotalWeight = headOnTotalWeight;
    }

    public String getHeadOnTareWeight() {
        return headOnTareWeight;
    }

    public void setHeadOnTareWeight(String headOnTareWeight) {
        this.headOnTareWeight = headOnTareWeight;
    }

    public String getHeadOnTotalTareWeight() {
        return headOnTotalTareWeight;
    }

    public void setHeadOnTotalTareWeight(String headOnTotalTareWeight) {
        this.headOnTotalTareWeight = headOnTotalTareWeight;
    }

    public String getHeadOnNetWeight() {
        return headOnNetWeight;
    }

    public void setHeadOnNetWeight(String headOnNetWeight) {
        this.headOnNetWeight = headOnNetWeight;
    }

    public String getVarietyCount() {
        return varietyCount;
    }

    public void setVarietyCount(String varietyCount) {
        this.varietyCount = varietyCount;
    }
}
