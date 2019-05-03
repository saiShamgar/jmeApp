package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 3/29/2018.
 */

public class InsertSiteWeightmentRequest {

    @SerializedName("ScheduleNumber")
    @Expose
    private String scheduleNumber;

    @SerializedName("Enquiry_No")
    @Expose
    private String enquiryNo;

    @SerializedName("Lot_No")
    @Expose
    private String lotNumber;

    @SerializedName("Emp_id")
    @Expose
    private String empId;
    @SerializedName("AgentName")
    @Expose
    private String agentName;
    @SerializedName("FarmerName")
    @Expose
    private String farmerName;
    @SerializedName("FarmerPondNo")
    @Expose
    private String farmerPondNo;
    @SerializedName("FarmerLocation")
    @Expose
    private String farmerLocation;
    @SerializedName("Farmer_Pond_Navigation_Details")
    @Expose
    private String farmerPondNavigation;
    @SerializedName("Material_Group_Name")
    @Expose
    private String materialGroupName;

    @SerializedName("Product_Variety_Name")
    @Expose
    private String productVarietyName;

    @SerializedName("Total_Weight")
    @Expose
    private String totalWeightAllCounts;

    @SerializedName("ProductImage")
    @Expose
    private String productImage;



    @SerializedName("ListOfWeighment")
    @Expose
    private List<WeightDataBody> listOfWeighment = new ArrayList<>() ;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getFarmerPondNo() {
        return farmerPondNo;
    }

    public void setFarmerPondNo(String farmerPondNo) {
        this.farmerPondNo = farmerPondNo;
    }

    public String getFarmerLocation() {
        return farmerLocation;
    }

    public void setFarmerLocation(String farmerLocation) {
        this.farmerLocation = farmerLocation;
    }

    public String getFarmerPondNavigation() {
        return farmerPondNavigation;
    }

    public void setFarmerPondNavigation(String farmerPondNavigation) {
        this.farmerPondNavigation = farmerPondNavigation;
    }

    public String getMaterialGroupName() {
        return materialGroupName;
    }

    public void setMaterialGroupName(String materialGroupName) {
        this.materialGroupName = materialGroupName;
    }

    public String getProductVarietyName() {
        return productVarietyName;
    }

    public void setProductVarietyName(String productVarietyName) {
        this.productVarietyName = productVarietyName;
    }

    public String getTotalWeightAllCounts() {
        return totalWeightAllCounts;
    }

    public void setTotalWeightAllCounts(String totalWeightAllCounts) {
        this.totalWeightAllCounts = totalWeightAllCounts;
    }

    public List<WeightDataBody> getListOfWeighment() {
        return listOfWeighment;
    }

    public void setListOfWeighment(List<WeightDataBody> listOfWeighment) {
        this.listOfWeighment = listOfWeighment;
    }

    public String getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(String scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public String getEnquiryNo() {
        return enquiryNo;
    }

    public void setEnquiryNo(String enquiryNo) {
        this.enquiryNo = enquiryNo;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
