package com.eugene.contractorsearch.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suggestions {

    @SerializedName("suggestions")
    @Expose
    private List<Contractor> contractors = null;

    public List<Contractor> getContractors() {
        return contractors;
    }

    public void setContractors(List<Contractor> contractors) {
        this.contractors = contractors;
    }

}