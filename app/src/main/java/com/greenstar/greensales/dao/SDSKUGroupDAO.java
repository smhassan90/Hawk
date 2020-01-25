package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDSKUGroup;

import java.util.List;

@Dao
public interface SDSKUGroupDAO {
    @Insert
    void insertMultiple (List<SDSKUGroup> objects);

    @Query("SELECT * FROM SDSKUGroup")
    List<SDSKUGroup> getAll();

    @Query("DELETE FROM SDSKUGroup")
    public void nukeTable();
}
