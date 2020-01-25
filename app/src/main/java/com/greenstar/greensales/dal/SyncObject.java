package com.greenstar.greensales.dal;

import com.greenstar.greensales.model.LeaveEntry;
import com.greenstar.greensales.model.ProductOrderRelationship;
import com.greenstar.greensales.model.UnapprovedSDCustomer;

import java.util.List;

public class SyncObject {
    List<ProductOrderRelationship> orderProduct;
    List<UnapprovedSDCustomer> unapprovedSDCustomers;
    List<LeaveEntry> leaveEntries;

    public List<ProductOrderRelationship> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(List<ProductOrderRelationship> orderProduct) {
        this.orderProduct = orderProduct;
    }

    public List<UnapprovedSDCustomer> getUnapprovedSDCustomers() {
        return unapprovedSDCustomers;
    }

    public void setUnapprovedSDCustomers(List<UnapprovedSDCustomer> unapprovedSDCustomers) {
        this.unapprovedSDCustomers = unapprovedSDCustomers;
    }

    public List<LeaveEntry> getLeaveEntries() {
        return leaveEntries;
    }

    public void setLeaveEntries(List<LeaveEntry> leaveEntries) {
        this.leaveEntries = leaveEntries;
    }
}
