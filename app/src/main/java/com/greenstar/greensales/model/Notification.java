package com.greenstar.greensales.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Notification {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String shortNote;
    private String detailNote;
    private String notificationDate;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShortNote() {
        return shortNote;
    }

    public void setShortNote(String shortNote) {
        this.shortNote = shortNote;
    }

    public String getDetailNote() {
        return detailNote;
    }

    public void setDetailNote(String detailNote) {
        this.detailNote = detailNote;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
