package com.capstone.badi.menuHome.myNotes.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.capstone.badi.menuHome.myProduct.database.Product
import com.capstone.badi.menuHome.myProduct.database.ProductDao
import com.capstone.badi.menuHome.myProduct.database.ProductRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProductRepository(application: Application) {
    private val mProductDao: ProductDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = ProductRoomDatabase.getDatabase(application)
        mProductDao = db.productDao()
    }

    fun getAllProduct(): LiveData<List<Product>> = mProductDao.getAllProduct()

    fun insert(product: Product) {
        executorService.execute { mProductDao.insert(product) }
    }

    fun delete(product: Product) {
        executorService.execute { mProductDao.delete(product) }
    }

    fun update(product: Product) {
        executorService.execute { mProductDao.update(product) }
    }
}