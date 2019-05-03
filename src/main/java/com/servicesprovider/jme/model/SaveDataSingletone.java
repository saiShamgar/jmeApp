package com.servicesprovider.jme.model;

/**
 * Created by Akash on 3/22/2018.
 */
public class SaveDataSingletone {
    private static SaveDataSingletone ourInstance = null;

    private double comulativeWeight = 0.0;

    public static SaveDataSingletone getInstance() {
        if (ourInstance == null){
            ourInstance = new SaveDataSingletone();
        }
        return ourInstance;
    }

    private SaveDataSingletone() {
    }

    public double getComulativeWeight() {
        return comulativeWeight;
    }

    public void setComulativeWeight(double comulativeWeight) {
        this.comulativeWeight = comulativeWeight;
    }
}
