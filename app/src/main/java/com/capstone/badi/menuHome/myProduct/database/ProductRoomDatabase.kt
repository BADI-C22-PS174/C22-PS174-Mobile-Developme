package com.capstone.badi.menuHome.myProduct.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1)
abstract class ProductRoomDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    companion object {
        @Volatile
        private var INSTANCE: ProductRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): ProductRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ProductRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ProductRoomDatabase::class.java, "product_database")
                        .build()
                }
            }
            return INSTANCE as ProductRoomDatabase
        }
    }
}