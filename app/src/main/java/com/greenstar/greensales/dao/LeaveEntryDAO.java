package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.LeaveEntry;
import com.greenstar.greensales.model.UnapprovedSDCustomer;

import java.util.List;

@Dao
public interface LeaveEntryDAO {
    @Insert
    void insert (LeaveEntry object);

    @Query("SELECT * FROM LeaveEntry")
    List<LeaveEntry> getAll();

    @Query("DELETE FROM LeaveEntry")
    public void nukeTable();
}
