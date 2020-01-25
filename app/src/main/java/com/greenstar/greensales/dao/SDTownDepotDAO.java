package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDTownDepot;

import java.util.List;

@Dao
public interface SDTownDepotDAO {
    @Insert
    void insertMultiple (List<SDTownDepot> objects);

    @Query("SELECT * FROM SDTownDepot")
    List<SDTownDepot> getAll();

    @Query("DELETE FROM SDTownDepot")
    public void nukeTable();

    @Query("SELECT * FROM SDTownDepot WHERE townId=:townId")
    List<SDTownDepot> getAllByTownId(String townId);
}
