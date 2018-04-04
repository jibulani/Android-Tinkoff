package com.eugene.contractorsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class State {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("actuality_date")
    @Expose
    private Integer actualityDate;
    @SerializedName("registration_date")
    @Expose
    private Integer registrationDate;
    @SerializedName("liquidation_date")
    @Expose
    private Object liquidationDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getActualityDate() {
        return actualityDate;
    }

    public void setActualityDate(Integer actualityDate) {
        this.actualityDate = actualityDate;
    }

    public Integer getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Integer registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Object getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(Object liquidationDate) {
        this.liquidationDate = liquidationDate;
    }
}
