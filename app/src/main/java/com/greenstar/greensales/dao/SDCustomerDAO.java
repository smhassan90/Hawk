package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.SDCustomer;

import java.util.List;
@Dao
public interface SDCustomerDAO {
    @Insert
    void insertMultiple (List<SDCustomer> customers);

    @Query("SELECT * FROM SDCustomer")
    List<SDCustomer> getAll();

    @Query("SELECT * FROM SDCustomer WHERE type!='STREET SALES'")
    List<SDCustomer> getAllSDCustomer();

    @Query("SELECT SDCustomer.custCode,SDCustomer.custName, SDCustomer.custAdd FROM SDCustomer INNER JOIN SDTownCustomer ON SDTownCustomer.customerId=SDCustomer.custCode AND SDTownCustomer.townId=:townId")
    List<SDCustomer> getSpecificCustomers(String townId);

    @Query("SELECT SDCustomer.custCode,SDCustomer.custName, SDCustomer.custAdd, SDCustomer.type FROM SDCustomer INNER JOIN SDTownCustomer ON SDTownCustomer.customerId=SDCustomer.custCode AND SDTownCustomer.townId=:townId WHERE SDCustomer.type!='STREET SALES'")
    List<SDCustomer> getSpecificSDCustomers(String townId);

    @Query("DELETE FROM SDCustomer")
    public void nukeTable();

}
