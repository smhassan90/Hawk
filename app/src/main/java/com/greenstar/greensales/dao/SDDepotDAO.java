package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.Product;
import com.greenstar.greensales.model.SDDepot;

import java.util.List;

@Dao
public interface SDDepotDAO {
    @Insert
    void insertMultiple (List<SDDepot> depots);

    @Query("SELECT * FROM SDDepot")
    List<SDDepot> getAll();

    @Query("DELETE FROM SDDepot")
    public void nukeTable();

    @Query("SELECT * FROM SDDepot where depotCode=:depotCode")
    List<SDDepot> getSpecificDepot(int depotCode);
}
