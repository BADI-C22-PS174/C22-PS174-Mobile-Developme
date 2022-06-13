package com.capstone.badi.menuHome.myProduct.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.badi.menuHome.myNotes.repository.ProductRepository
import com.capstone.badi.menuHome.myProduct.database.Product


class MyProductViewModel (application: Application) : ViewModel() {
    private val mProductRepository: ProductRepository = ProductRepository(application)

    fun getAllProduct(): LiveData<List<Product>> = mProductRepository.getAllProduct()
}