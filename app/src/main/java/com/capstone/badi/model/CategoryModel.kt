package com.capstone.badi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryModel (
    var nama_kategori: String,
    var deskripsi: String,
    var photo: Int
) : Parcelable