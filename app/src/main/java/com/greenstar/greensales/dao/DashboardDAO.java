package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.Dashboard;

import java.util.List;

@Dao
public interface DashboardDAO {

    @Insert
    void insertMultiple (Dashboard dashboard);

    @Query("SELECT * FROM Dashboard where id=1")
    Dashboard getAll();

    @Query("DELETE FROM Dashboard")
    public void nukeTable();
}
