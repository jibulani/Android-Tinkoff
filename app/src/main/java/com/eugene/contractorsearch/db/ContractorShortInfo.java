package com.eugene.contractorsearch.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class ContractorShortInfo {

    @PrimaryKey(autoGenerate = true)
    long id;
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
}
