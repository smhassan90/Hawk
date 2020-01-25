package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDDepotStaff;

import java.util.List;

@Dao
public interface SDDepotStaffDAO {
    @Insert
    void insertMultiple (List<SDDepotStaff> objects);

    @Query("SELECT * FROM SDDepotStaff")
    List<SDDepotStaff> getAll();

    @Query("DELETE FROM SDDepotStaff")
    public void nukeTable();
}
