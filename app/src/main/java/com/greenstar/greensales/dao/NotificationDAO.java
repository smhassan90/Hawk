package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.model.Dashboard;
import com.greenstar.greensales.model.Notification;

import java.util.List;

@Dao
public interface NotificationDAO {

    @Insert
    void insertMultiple (Notification notification);

    @Query("SELECT * FROM Notification order by id desc")
    List<Notification> getAll();

    @Query("DELETE FROM Notification")
    public void nukeTable();

    @Query("DELETE FROM Notification WHERE id=:id")
    public void deleteNotification(long id);
}
