package com.capstone.badi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryModel (
    var date: String,
    var items: String,
    var price: String,
    var status: String
) : Parcelable