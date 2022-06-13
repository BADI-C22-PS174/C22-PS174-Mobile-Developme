package com.capstone.badi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductModel(
    val imgUrl : String? = "",
    val p_market_price: String? = "",
    val p_name: String? = "",
    var p_category: String? = "",
    val p_selling_price: String? = "",
    val p_stock: String? = "",
    val p_description: String? = "",

//    var alat_tulis: String,
//    var beras: String,
//    var barang_curah: String,
//    var keperluan_dapur: String,
//    var kantong_kresek: String,
//    var sabun: String,
//    var jajanan: String,
//    var kopi: String,
//    var gas_elpigi: String,
//    var sandal_jepit: String,
//    var mie_instan: String,
//    var minuman: String,
//    var obat: String,
//    var plastik: String,
//    var perlengkapan_rumah_tangga: String,
//    var susu: String,
//    var teh: String,
//    var pulsa_elektrik: String,
//    var lain_lain: String,
) : Parcelable
