package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.greenstar.greensales.model.SDStatus;
import com.greenstar.greensales.model.SDTownCustomer;

import java.util.List;

@Dao
public interface SDStatusDAO {

    @Insert
    void insertMultiple (List<SDStatus> objects);

    @Query("SELECT * FROM SDStatus")
    List<SDStatus> getAll();

    @Query("DELETE FROM SDStatus")
    public void nukeTable();
}
