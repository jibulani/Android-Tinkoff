package com.eugene.contractorsearch.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddressData {

    @SerializedName("geo_lat")
    @Expose
    private String geoLat;

    @SerializedName("geo_lon")
    @Expose
    private String geoLon;

    public String getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(String geoLat) {
        this.geoLat = geoLat;
    }

    public String getGeoLon() {
        return geoLon;
    }

    public void setGeoLon(String geoLon) {
        this.geoLon = geoLon;
    }
}
