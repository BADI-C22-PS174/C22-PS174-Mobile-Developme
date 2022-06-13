package com.capstone.badi.menuHome.myProduct.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("SELECT * from product ORDER BY id ASC")
    fun getAllProduct(): LiveData<List<Product>>
}