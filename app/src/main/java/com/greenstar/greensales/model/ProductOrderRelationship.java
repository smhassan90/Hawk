package com.greenstar.greensales.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class ProductOrderRelationship {
    @Embedded
    private Orderr order;
    @Relation(parentColumn = "id", entityColumn = "orderId", entity =   ProductOrder.class)
    private List<ProductOrder> productOrders;

    public Orderr getOrder() {
        return order;
    }

    public void setOrder(Orderr order) {
        this.order = order;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }
}
