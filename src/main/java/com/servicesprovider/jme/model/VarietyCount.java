package com.servicesprovider.jme.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by muheeb on 02-Apr-18.
 */

public class VarietyCount
{
    @SerializedName("VarietyCountCodes")
    @Expose
    private String VarietyCountCode;

    public String getVarietyCountCode() {
        return VarietyCountCode;
    }

    public void setVarietyCountCode(String varietyCountCode) {
        VarietyCountCode = varietyCountCode;
    }
}
