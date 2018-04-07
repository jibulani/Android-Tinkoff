package com.eugene.contractorsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class State {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("actuality_date")
    @Expose
    private Long actualityDate;
    @SerializedName("registration_date")
    @Expose
    private Long registrationDate;
    @SerializedName("liquidation_date")
    @Expose
    private Long liquidationDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getActualityDate() {
        return actualityDate;
    }

    public void setActualityDate(Long actualityDate) {
        this.actualityDate = actualityDate;
    }

    public Long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Long registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Long getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(Long liquidationDate) {
        this.liquidationDate = liquidationDate;
    }
}
