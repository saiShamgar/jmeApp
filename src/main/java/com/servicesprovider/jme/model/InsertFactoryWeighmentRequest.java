package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 4/3/2018.
 */

public class InsertFactoryWeighmentRequest {

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
}
