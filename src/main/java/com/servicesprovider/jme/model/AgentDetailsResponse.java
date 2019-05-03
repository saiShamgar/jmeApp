package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akash on 3/26/2018.
 */

public class AgentDetailsResponse {
    @SerializedName("Code")
    @Expose
    private int responseCode;

    @SerializedName("Message")
    @Expose
    private String responseMessage;

    @SerializedName("FarmerName")
    @Expose
    private String farmerName;

    @SerializedName("AgentName")
    @Expose
    private String agentName;
    @SerializedName("PlaceName")
    @Expose
    private String placeName;

    @SerializedName("Product_Variety_Name")
    @Expose
    private String product_Variety_Name;

    @SerializedName("Material_Group_Name")
    @Expose
    private String materialGroupName;

    @SerializedName("Lot_No")
    @Expose
    private String lotNumber;

    @SerializedName("LstVarietyCountCodes")
    @Expose
    private List<ProductCount> productVarietyCountList = new ArrayList<>();

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

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getProduct_Variety_Name() {
        return product_Variety_Name;
    }

    public void setProduct_Variety_Name(String product_Variety_Name) {
        this.product_Variety_Name = product_Variety_Name;
    }

    public List<ProductCount> getProductVarietyCountList() {
        return productVarietyCountList;
    }

    public void setProductVarietyCountList(List<ProductCount> productVarietyCountList) {
        this.productVarietyCountList = productVarietyCountList;
    }

    public String getMaterialGroupName() {
        return materialGroupName;
    }

    public void setMaterialGroupName(String materialGroupName) {
        this.materialGroupName = materialGroupName;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }
}
