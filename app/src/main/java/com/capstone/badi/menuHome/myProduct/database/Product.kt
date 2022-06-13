package com.capstone.badi.menuHome.myProduct.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Product (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nama_barang")
    var nama_barang: String? = null,

    @ColumnInfo(name = "harga")
    var harga: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable
