package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/29/2018.
 */

public class WeightDataBody {
    @SerializedName("Site_Weighment_Date_Time")
    @Expose
    private String siteWeighmentDateTime;
    @SerializedName("SW_No_of_Nets")
    @Expose
    private String numberOfNets;

    @SerializedName("SW_Total_Weight")
    @Expose
    private String siteTotalWeight;

    @SerializedName("SW_Tare_Weight")
    @Expose
    private String siteTareWeight;

    @SerializedName("SW_Total_Tare_Weight")
    @Expose
    private String siteTotalTareWeight;

    @SerializedName("SW_Net_Weight")
    @Expose
    private String siteNetWeight;

    @SerializedName("Variety_Count")
    @Expose
    private String varietyCount;


    @SerializedName("Factory_Weighment_Date_Time")
    @Expose
    private String factoryWeighmentDateTime;
    @SerializedName("FW_No_of_Nets")
    @Expose
    private String factoryNumberOfNets;

    @SerializedName("FW_Total_Weight")
    @Expose
    private String factoryTotalWeight;

    @SerializedName("FW_Tare_Weight")
    @Expose
    private String factoryTareWeight;

    @SerializedName("FW_Total_Tare_Weight")
    @Expose
    private String factoryTotalTareWeight;

    @SerializedName("FW_Net_Weight")
    @Expose
    private String factoryNetWeight;


    public String getSiteWeighmentDateTime() {
        return siteWeighmentDateTime;
    }

    public void setSiteWeighmentDateTime(String siteWeighmentDateTime) {
        this.siteWeighmentDateTime = siteWeighmentDateTime;
    }

    public String getNumberOfNets() {
        return numberOfNets;
    }

    public void setNumberOfNets(String numberOfNets) {
        this.numberOfNets = numberOfNets;
    }

    public String getSiteTotalWeight() {
        return siteTotalWeight;
    }

    public void setSiteTotalWeight(String siteTotalWeight) {
        this.siteTotalWeight = siteTotalWeight;
    }

    public String getSiteTareWeight() {
        return siteTareWeight;
    }

    public void setSiteTareWeight(String siteTareWeight) {
        this.siteTareWeight = siteTareWeight;
    }

    public String getSiteTotalTareWeight() {
        return siteTotalTareWeight;
    }

    public void setSiteTotalTareWeight(String siteTotalTareWeight) {
        this.siteTotalTareWeight = siteTotalTareWeight;
    }

    public String getSiteNetWeight() {
        return siteNetWeight;
    }

    public void setSiteNetWeight(String siteNetWeight) {
        this.siteNetWeight = siteNetWeight;
    }

    public String getVarietyCount() {
        return varietyCount;
    }

    public void setVarietyCount(String varietyCount) {
        this.varietyCount = varietyCount;
    }

    public String getFactoryWeighmentDateTime() {
        return factoryWeighmentDateTime;
    }

    public void setFactoryWeighmentDateTime(String factoryWeighmentDateTime) {
        this.factoryWeighmentDateTime = factoryWeighmentDateTime;
    }

    public String getFactoryNumberOfNets() {
        return factoryNumberOfNets;
    }

    public void setFactoryNumberOfNets(String factoryNumberOfNets) {
        this.factoryNumberOfNets = factoryNumberOfNets;
    }

    public String getFactoryTotalWeight() {
        return factoryTotalWeight;
    }

    public void setFactoryTotalWeight(String factoryTotalWeight) {
        this.factoryTotalWeight = factoryTotalWeight;
    }

    public String getFactoryTareWeight() {
        return factoryTareWeight;
    }

    public void setFactoryTareWeight(String factoryTareWeight) {
        this.factoryTareWeight = factoryTareWeight;
    }

    public String getFactoryTotalTareWeight() {
        return factoryTotalTareWeight;
    }

    public void setFactoryTotalTareWeight(String factoryTotalTareWeight) {
        this.factoryTotalTareWeight = factoryTotalTareWeight;
    }

    public String getFactoryNetWeight() {
        return factoryNetWeight;
    }

    public void setFactoryNetWeight(String factoryNetWeight) {
        this.factoryNetWeight = factoryNetWeight;
    }
}
