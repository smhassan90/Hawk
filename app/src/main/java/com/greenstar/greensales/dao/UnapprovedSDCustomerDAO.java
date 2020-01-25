package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDTownStaff;
import com.greenstar.greensales.model.UnapprovedSDCustomer;

import java.util.List;
@Dao
public interface UnapprovedSDCustomerDAO {

    @Insert
    void insert (UnapprovedSDCustomer object);

    @Query("SELECT * FROM UnapprovedSDCustomer")
    List<UnapprovedSDCustomer> getAll();

    @Query("DELETE FROM UnapprovedSDCustomer")
    public void nukeTable();
}
