package com.servicesprovider.jme.model;

import java.io.Serializable;

/**
 * Created by Akash on 3/22/2018.
 */

public class WeightData  {
    private String DateTime = null;
    private String numberOfNets = null;
    private String tareWeight = null;
    private String totalWeight = null;
    private String totalTareWeight = null;
    private float netWeight = 0;
    private String productVarietyCount;
    private double comulativeWeight = 0.0;

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getNumberOfNets() {
        return numberOfNets;
    }

    public void setNumberOfNets(String numberOfNets) {
        this.numberOfNets = numberOfNets;
    }
    public String getTotalTareWeight() {
        return totalTareWeight;
    }

    public void setTotalTareWeight(String totalTareWeight) {
        this.totalTareWeight = totalTareWeight;
    }

    public float getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(float netWeight) {
        this.netWeight = netWeight;
    }

    public String getTareWeight() {
        return tareWeight;
    }

    public void setTareWeight(String tareWeight) {
        this.tareWeight = tareWeight;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getComulativeWeight() {
        return comulativeWeight;
    }

    public void setComulativeWeight(double comulativeWeight) {
        this.comulativeWeight = comulativeWeight;
    }

    public String getProductVarietyCount() {
        return productVarietyCount;
    }

    public void setProductVarietyCount(String productVarietyCount) {
        this.productVarietyCount = productVarietyCount;
    }
}
