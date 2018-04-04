package com.eugene.contractorsearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Opf {
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("full")
    @Expose
    private String full;
    @SerializedName("short")
    @Expose
    private String _short;

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
