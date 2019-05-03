package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Akash on 3/26/2018.
 */

public class ProductCount {
    @SerializedName("VarietyCountCodes")
    @Expose
    private String varietyCounts;

    public String getVarietyCounts() {
        return varietyCounts;
    }

    public void setVarietyCounts(String varietyCounts) {
        this.varietyCounts = varietyCounts;
    }
}
