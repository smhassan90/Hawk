package com.greenstar.greensales.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class SDSKUGroup {
    @PrimaryKey
    @NonNull
    private String grpId;

    private String grpName;

    @NonNull
    public String getGrpId() {
        return grpId;
    }

    public void setGrpId(@NonNull String grpId) {
        this.grpId = grpId;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpName) {
        this.grpName = grpName;
    }

    @Override
    public String toString() {
        return getGrpName();
    }
}
