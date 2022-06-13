//package com.capstone.badi.menuHome.myProduct.helper
//
//import androidx.recyclerview.widget.DiffUtil
//import com.capstone.badi.menuHome.myProduct.database.Product
//import com.capstone.badi.model.ProductModel
//
//class ProductDiffCallback(private val mOldProductList: ArrayList<ProductModel>, private val mNewProductList: List<ProductModel>) : DiffUtil.Callback() {
//    override fun getOldListSize(): Int {
//        return mOldProductList.size
//    }
//
//    override fun getNewListSize(): Int {
//        return mNewProductList.size
//    }
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return mOldProductList[oldItemPosition].id == mNewProductList[newItemPosition].id
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldEmployee = mOldProductList[oldItemPosition]
//        val newEmployee = mNewProductList[newItemPosition]
//        return oldEmployee.nama_barang == newEmployee.nama_barang && oldEmployee.harga == newEmployee.harga
//    }
//}