package com.eugene.contractorsearch.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("unrestricted_value")
    @Expose
    private String unrestrictedValue;
    @SerializedName("data")
    @Expose
    private AddressData addressData;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnrestrictedValue() {
        return unrestrictedValue;
    }

    public void setUnrestrictedValue(String unrestrictedValue) {
        this.unrestrictedValue = unrestrictedValue;
    }

    public AddressData getAddressData() {
        return addressData;
    }

    public void setAddressData(AddressData addressData) {
        this.addressData = addressData;
    }
}
