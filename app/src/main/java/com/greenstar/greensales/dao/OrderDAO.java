package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.greenstar.greensales.model.Orderr;
import com.greenstar.greensales.model.SDDepot;
import com.greenstar.greensales.model.SDTown;

import java.util.List;

@Dao
public interface OrderDAO {
    @Insert
    long insert(Orderr order);

    @Query("DELETE FROM Orderr")
    public void nukeTable();

    @Query("SELECT * FROM Orderr")
    List<Orderr> getAll();

    @Query("DELETE FROM Orderr WHERE id=:orderId")
    public void deleteOrder(int orderId);
}
