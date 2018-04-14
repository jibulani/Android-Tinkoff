package com.eugene.contractorsearch.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.eugene.contractorsearch.model.Contractor;

import java.util.Date;

@Entity
public class ContractorShortInfo {

    @NonNull
    @PrimaryKey
    String hid;
    String value;
    String managerName;
    String managerPost;
    String kpp;
    String fullName;
    String inn;
    String ogrn;
    String address;
    Date lastRequestDate;
    boolean isFavourite;

    public ContractorShortInfo() {}

    public ContractorShortInfo(Contractor contractor, boolean isFavourite) {
        hid = contractor.getData().getHid();
        value = contractor.getValue();
        if (contractor.getData().getManagement() != null) {
            if (contractor.getData().getManagement().getName() != null) {
                managerName = contractor.getData().getManagement().getName();
            }
            if (contractor.getData().getManagement().getPost() != null) {
                managerPost = contractor.getData().getManagement().getPost();
            }
        }
        managerName = contractor.getData().getManagement().getName();
        managerPost = contractor.getData().getManagement().getPost();
        kpp = contractor.getData().getKpp();
        fullName = contractor.getData().getName().getFullWithOpf();
        inn = contractor.getData().getInn();
        ogrn = contractor.getData().getOgrn();
        address = contractor.getData().getAddress().getValue();
        lastRequestDate = new Date();
        this.isFavourite = isFavourite;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPost() {
        return managerPost;
    }

    public void setManagerPost(String managerPost) {
        this.managerPost = managerPost;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLastRequestDate() {
        return lastRequestDate;
    }

    public void setLastRequestDate(Date lastRequestDate) {
        this.lastRequestDate = lastRequestDate;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
