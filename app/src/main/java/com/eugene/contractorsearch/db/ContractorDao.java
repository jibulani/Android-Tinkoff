package com.eugene.contractorsearch.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface ContractorDao {
    @Insert
    void insert(ContractorShortInfo contractorShortInfo);

    @Query("SELECT * FROM contractorshortinfo WHERE hid = :hid LIMIT 1")
    ContractorShortInfo getContractorById(String hid);
}
