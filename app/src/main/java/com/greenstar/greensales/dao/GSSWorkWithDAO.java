package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.GSSWorkWith;
import com.greenstar.greensales.model.SDDepot;

import java.util.List;

@Dao
public interface GSSWorkWithDAO {
    @Insert
    void insertMultiple (List<GSSWorkWith> workWiths);

    @Query("SELECT * FROM GSSWorkWith")
    List<GSSWorkWith> getAll();

    @Query("DELETE FROM GSSWorkWith")
    public void nukeTable();
}
