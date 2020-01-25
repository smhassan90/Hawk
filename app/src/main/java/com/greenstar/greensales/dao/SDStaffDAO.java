package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDStaff;

import java.util.List;

@Dao
public interface SDStaffDAO {
    @Insert
    void insertMultiple (List<SDStaff> objects);

    @Query("SELECT * FROM SDStaff")
    List<SDStaff> getAll();

    @Query("DELETE FROM SDStaff")
    public void nukeTable();
}
