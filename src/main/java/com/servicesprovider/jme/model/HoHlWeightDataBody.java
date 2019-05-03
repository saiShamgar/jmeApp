package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muheeb on 11-Apr-18.
 */

public class HoHlWeightDataBody
{
    @SerializedName("HOHL_Weighment_Date_Time")
    @Expose
    private String hohlWeighmentDateTime;
    @SerializedName("HOHL_No_of_Nets")
    @Expose
    private String numberOfNets;
    @SerializedName("HOHL_Total_Weight")
    @Expose
    private String hohlTotalWeight;

    @SerializedName("HOHL_Tare_Weight")
    @Expose
    private String hohlTareWeight;

    @SerializedName("HOHL_Total_Tare_Weight")
    @Expose
    private String hohlTotalTareWeight;

    @SerializedName("HOHL_Net_Weight")
    @Expose
    private String hohlNetWeight;

    @SerializedName("Variety_Count")
    @Expose
    private String varietyCount;

    @SerializedName("HOHL_Group_Team_Nos")
    @Expose
    private String teamNo;

    @SerializedName("HOHL_Group_Person_Name")
    @Expose
    private String personName;

    public String getHohlWeighmentDateTime() {
        return hohlWeighmentDateTime;
    }

    public void setHohlWeighmentDateTime(String hohlWeighmentDateTime) {
        this.hohlWeighmentDateTime = hohlWeighmentDateTime;
    }

    public String getNumberOfNets() {
        return numberOfNets;
    }

    public void setNumberOfNets(String numberOfNets) {
        this.numberOfNets = numberOfNets;
    }

    public String getHohlTotalWeight() {
        return hohlTotalWeight;
    }

    public void setHohlTotalWeight(String hohlTotalWeight) {
        this.hohlTotalWeight = hohlTotalWeight;
    }

    public String getHohlTareWeight() {
        return hohlTareWeight;
    }

    public void setHohlTareWeight(String hohlTareWeight) {
        this.hohlTareWeight = hohlTareWeight;
    }

    public String getHohlTotalTareWeight() {
        return hohlTotalTareWeight;
    }

    public void setHohlTotalTareWeight(String hohlTotalTareWeight) {
        this.hohlTotalTareWeight = hohlTotalTareWeight;
    }

    public String getHohlNetWeight() {
        return hohlNetWeight;
    }

    public void setHohlNetWeight(String hohlNetWeight) {
        this.hohlNetWeight = hohlNetWeight;
    }

    public String getVarietyCount() {
        return varietyCount;
    }

    public void setVarietyCount(String varietyCount) {
        this.varietyCount = varietyCount;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
