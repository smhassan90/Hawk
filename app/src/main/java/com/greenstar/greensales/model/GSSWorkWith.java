package com.greenstar.greensales.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class GSSWorkWith {
    @PrimaryKey
    private int id;
    private String workWith;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkWith() {
        return workWith;
    }

    public void setWorkWith(String workWith) {
        this.workWith = workWith;
    }
}
