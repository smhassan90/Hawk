package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.greenstar.greensales.dal.CompleteOrder;
import com.greenstar.greensales.model.Orderr;
import com.greenstar.greensales.model.ProductOrder;
import com.greenstar.greensales.model.ProductOrderRelationship;
import com.greenstar.greensales.model.SDDepot;

import java.util.List;

@Dao
public abstract class ProductOrderDAO {
    public void insertCompleteOrder(long orderId, List<ProductOrder> productOrders){
        for(ProductOrder productOrder : productOrders){
            productOrder.setOrderId(orderId);
        }
        _insertAll(productOrders);
    }

    @Query("DELETE FROM ProductOrder WHERE orderId=:orderId")
    public abstract void deleteProductOrder(int orderId);

    @Insert
    abstract void _insertAll(List<ProductOrder> products);

    @Query("SELECT * FROM Orderr")
    public abstract List<ProductOrderRelationship> loadProductOrder();

    @Query("SELECT * FROM ProductOrder")
    public abstract List<ProductOrder> getAll();

    @Query("SELECT Orderr.id as orderId, SDStatus.status, SDSKUGroup.grpName as productName," +
            " ProductOrder.quantity as quantity, Orderr.custCode, SDCustomer.custName, Orderr.visitDate as visitDate " +
            " FROM ProductOrder" +
            " LEFT JOIN SDSKUGroup ON SDSKUGroup.grpId = ProductOrder.productId \n " +
            " LEFT JOIN Orderr ON Orderr.id = ProductOrder.orderId \n" +
            " LEFT JOIN SDCustomer ON SDCustomer.custCode = Orderr.custCode \n" +
            " LEFT JOIN SDStatus ON SDStatus.id = Orderr.statusId")
    public abstract List<CompleteOrder> loadCompleteOrder();

}
