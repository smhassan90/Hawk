package com.greenstar.greensales.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class SDTownDepot {
    @PrimaryKey
    @NonNull
    private String townId;
    private String depotId;

    @NonNull
    public String getTownId() {
        return townId;
    }

    public void setTownId(@NonNull String townId) {
        this.townId = townId;
    }

    public String getDepotId() {
        return depotId;
    }

    public void setDepotId(String depotId) {
        this.depotId = depotId;
    }
}
