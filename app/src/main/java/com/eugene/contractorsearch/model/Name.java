package com.eugene.contractorsearch.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Name {

    @SerializedName("full_with_opf")
    @Expose
    private String fullWithOpf;
    @SerializedName("short_with_opf")
    @Expose
    private String shortWithOpf;
    @SerializedName("latin")
    @Expose
    private Object latin;
    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("short")
    @Expose
    private String _short;

    public String getFullWithOpf() {
        return fullWithOpf;
    }

    public void setFullWithOpf(String fullWithOpf) {
        this.fullWithOpf = fullWithOpf;
    }

    public String getShortWithOpf() {
        return shortWithOpf;
    }

    public void setShortWithOpf(String shortWithOpf) {
        this.shortWithOpf = shortWithOpf;
    }

    public Object getLatin() {
        return latin;
    }

    public void setLatin(Object latin) {
        this.latin = latin;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getShort() {
        return _short;
    }

    public void setShort(String _short) {
        this._short = _short;
    }
}
