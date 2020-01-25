package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.Product;
import com.greenstar.greensales.model.SDTownStaff;

import java.util.List;

@Dao
public interface SDTownStaffDAO {
    @Insert
    void insertMultiple (List<SDTownStaff> objects);

    @Query("SELECT * FROM SDTownStaff")
    List<SDTownStaff> getAll();

    @Query("DELETE FROM SDTownStaff")
    public void nukeTable();
}
