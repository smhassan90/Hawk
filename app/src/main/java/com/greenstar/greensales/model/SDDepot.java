package com.greenstar.greensales.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class SDDepot {
    @PrimaryKey
    @NonNull
    private int depotCode;

    private String depotName;

    @NonNull
    public int getDepotCode() {
        return depotCode;
    }

    public void setDepotCode(@NonNull int depotCode) {
        this.depotCode = depotCode;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    @Override
    public String toString() {
        return getDepotName();
    }
}
