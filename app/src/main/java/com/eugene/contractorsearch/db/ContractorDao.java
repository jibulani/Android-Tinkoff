package com.eugene.contractorsearch.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface ContractorDao {
    @Insert
    long insert(ContractorShortInfo contractorShortInfo);

    @Query("SELECT * FROM contractorshortinfo WHERE id = :id LIMIT 1")
    ContractorShortInfo getContractorById(long id);
}
