package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDTownCustomer;

import java.util.List;

@Dao
public interface SDTownCustomerDAO {
    @Insert
    void insertMultiple (List<SDTownCustomer> objects);

    @Query("SELECT * FROM SDTownCustomer")
    List<SDTownCustomer> getAll();

    @Query("DELETE FROM SDTownCustomer")
    public void nukeTable();

    @Query("SELECT * FROM SDTownCustomer WHERE customerId=:custId")
    public SDTownCustomer getTownId(String custId);
}
