package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muheeb on 05-Apr-18.
 */

public class HeadLessWeightDataBody
{
    @SerializedName("HLW_Weighment_Date_Time")
    @Expose
    private String headLessWeighmentDateTime;
    @SerializedName("HLG_No_of_Nets")
    @Expose
    private String numberOfNets;

    @SerializedName("HLG_Total_Weight")
    @Expose
    private String headLessTotalWeight;

    @SerializedName("HLG_Tare_Weight")
    @Expose
    private String headLessTareWeight;

    @SerializedName("HLG_Total_Tare_Weight")
    @Expose
    private String headLessTotalTareWeight;

    @SerializedName("HLG_Net_Weight")
    @Expose
    private String headLessNetWeight;

    @SerializedName("Variety_Count")
    @Expose
    private String varietyCount;

    public String getHeadLessWeighmentDateTime() {
        return headLessWeighmentDateTime;
    }

    public void setHeadLessWeighmentDateTime(String headLessWeighmentDateTime) {
        this.headLessWeighmentDateTime = headLessWeighmentDateTime;
    }

    public String getNumberOfNets() {
        return numberOfNets;
    }

    public void setNumberOfNets(String numberOfNets) {
        this.numberOfNets = numberOfNets;
    }

    public String getHeadLessTotalWeight() {
        return headLessTotalWeight;
    }

    public void setHeadLessTotalWeight(String headLessTotalWeight) {
        this.headLessTotalWeight = headLessTotalWeight;
    }

    public String getHeadLessTareWeight() {
        return headLessTareWeight;
    }

    public void setHeadLessTareWeight(String headLessTareWeight) {
        this.headLessTareWeight = headLessTareWeight;
    }

    public String getHeadLessTotalTareWeight() {
        return headLessTotalTareWeight;
    }

    public void setHeadLessTotalTareWeight(String headLessTotalTareWeight) {
        this.headLessTotalTareWeight = headLessTotalTareWeight;
    }

    public String getHeadLessNetWeight() {
        return headLessNetWeight;
    }

    public void setHeadLessNetWeight(String headLessNetWeight) {
        this.headLessNetWeight = headLessNetWeight;
    }

    public String getVarietyCount() {
        return varietyCount;
    }

    public void setVarietyCount(String varietyCount) {
        this.varietyCount = varietyCount;
    }
}
