package com.eugene.contractorsearch.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContractorDao {
    @Insert
    void insert(ContractorShortInfo contractorShortInfo);

    @Delete
    void deleteContractor(ContractorShortInfo contractorShortInfo);

    @Update
    int updateContractor(ContractorShortInfo contractorShortInfo);

    @Query("SELECT * FROM contractorshortinfo WHERE hid = :hid LIMIT 1")
    ContractorShortInfo getContractorById(String hid);

    @Query("SELECT * FROM contractorshortinfo")
    List<ContractorShortInfo> getAll();
}
