package com.greenstar.greensales.dal;

import com.greenstar.greensales.model.Dashboard;
import com.greenstar.greensales.model.GSSWorkWith;
import com.greenstar.greensales.model.SDCustomer;
import com.greenstar.greensales.model.SDDepot;
import com.greenstar.greensales.model.SDSKUGroup;
import com.greenstar.greensales.model.SDStatus;
import com.greenstar.greensales.model.SDTown;
import com.greenstar.greensales.model.SDTownCustomer;
import com.greenstar.greensales.model.SDTownDepot;

import java.util.ArrayList;
import java.util.List;

public class Data {
    ArrayList<SDDepot> depots;
    ArrayList<SDTown> towns;
    ArrayList<SDCustomer> customers;
    ArrayList<SDTownDepot> townDepots;
    ArrayList<SDTownCustomer> townCustomers;
    ArrayList<SDStatus> statuses;
    ArrayList<SDSKUGroup> skuGroup;
    ArrayList<GSSWorkWith> workWiths;
    Dashboard dashboard;

    public ArrayList<SDDepot> getDepots() {
        return depots;
    }

    public void setDepots(ArrayList<SDDepot> depots) {
        this.depots = depots;
    }

    public ArrayList<SDTown> getTowns() {
        return towns;
    }

    public void setTowns(ArrayList<SDTown> towns) {
        this.towns = towns;
    }

    public ArrayList<SDCustomer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<SDCustomer> customers) {
        this.customers = customers;
    }

    public ArrayList<SDTownDepot> getTownDepots() {
        return townDepots;
    }

    public void setTownDepots(ArrayList<SDTownDepot> townDepots) {
        this.townDepots = townDepots;
    }

    public ArrayList<SDTownCustomer> getTownCustomers() {
        return townCustomers;
    }

    public void setTownCustomers(ArrayList<SDTownCustomer> townCustomers) {
        this.townCustomers = townCustomers;
    }

    public ArrayList<SDStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(ArrayList<SDStatus> statuses) {
        this.statuses = statuses;
    }

    public ArrayList<SDSKUGroup> getSkuGroup() {
        return skuGroup;
    }

    public void setSkuGroup(ArrayList<SDSKUGroup> skuGroup) {
        this.skuGroup = skuGroup;
    }

    public ArrayList<GSSWorkWith> getWorkWiths() {
        return workWiths;
    }

    public void setWorkWiths(ArrayList<GSSWorkWith> workWiths) {
        this.workWiths = workWiths;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }
}
