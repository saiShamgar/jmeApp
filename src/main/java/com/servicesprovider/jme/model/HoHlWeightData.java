package com.servicesprovider.jme.model;

/**
 * Created by muheeb on 10-Apr-18.
 */

public class HoHlWeightData
{
    private String DateTime = null;
    private String numberOfNets = null;
    private String tareWeight = null;
    private String totalWeight = null;
    private String totalTareWeight = null;
    private float netWeight = 0;
    private String productVarietyCount;
    private double comulativeWeight = 0.0;
    private String personName = "";
    private String teamNo = "";

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

    public String getProductVarietyCount() {
        return productVarietyCount;
    }

    public void setProductVarietyCount(String productVarietyCount) {
        this.productVarietyCount = productVarietyCount;
    }

    public double getComulativeWeight() {
        return comulativeWeight;
    }

    public void setComulativeWeight(double comulativeWeight) {
        this.comulativeWeight = comulativeWeight;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(String teamNo) {
        this.teamNo = teamNo;
    }
}
