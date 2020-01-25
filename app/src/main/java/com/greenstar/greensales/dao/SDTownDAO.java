package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDTown;

import java.util.List;

@Dao
public interface SDTownDAO {
    @Insert
    void insertMultiple (List<SDTown> objects);

    @Query("SELECT * FROM SDTown")
    List<SDTown> getAll();

    @Query("SELECT * FROM SDTown INNER JOIN SDTownDepot ON SDTownDepot.townId=SDTown.townId WHERE SDTownDepot.depotId=:depotId")
    List<SDTown> getSpecificDepotTowns(int depotId);

    @Query("SELECT * FROM SDTown where townId=:townId")
    SDTown getSpecificTown(String townId);

    @Query("DELETE FROM SDTown")
    public void nukeTable();
}
