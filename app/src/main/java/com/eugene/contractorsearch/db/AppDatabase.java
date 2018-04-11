package com.eugene.contractorsearch.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {ContractorShortInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContractorDao contractorDao();
}
