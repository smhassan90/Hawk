package com.greenstar.greensales.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.greenstar.greensales.model.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Insert
    void insertProducts (Product product);
    @Insert
    void insertMultipleProducts (List<Product> products);
    @Update
    void updateProduct (Product product);
    @Delete
    void deleteProduct(Product product);

    @Query("SELECT * FROM Product")
    List<Product> getAll();

    @Query("DELETE FROM ProductOrder")
    public void nukeTable();
}
