package com.capstone.badi.menuHome.myProduct.ui.main

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.capstone.badi.R
import com.capstone.badi.model.ProductModel
import com.squareup.picasso.Picasso

class ProductAdapter (private val listProduct: List<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.your_products_adapter_view,
            parent, false)
        return ProductViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct=listProduct[position]
        val v=holder.itemView
        val context=holder.itemView.context
        holder.productName.text=currentProduct.p_name
        holder.productSellingPrice.text= Html.fromHtml("<u>Price Rp.</u><font color=#000000><b>  ${currentProduct.p_selling_price}</b></font>",
            Html.FROM_HTML_MODE_COMPACT)
        holder.productPriceMarket.text= Html.fromHtml("<u>Market Price Rp.</u><font color=#000000><b>  ${currentProduct.p_market_price}</b></font>",
            Html.FROM_HTML_MODE_COMPACT)
        holder.productStock.text= Html.fromHtml("<u>In stock :</u><font color=#1565C0><b> ${currentProduct.p_stock}</b></font>",
            Html.FROM_HTML_MODE_COMPACT)
        holder.productCategory.text=currentProduct.p_category
        Picasso.get()
            .load(currentProduct.imgUrl)
            .error(R.drawable.no_image_icon)
            .fit()
            .into(holder.productImg)
        holder.descriptionText.text= Html.fromHtml("<u>Description :</u><font color=#1565C0><b> ${currentProduct.p_description}</b></font>",
            Html.FROM_HTML_MODE_COMPACT)
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
    inner class ProductViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val productImg: ImageView = itemView.findViewById(R.id.p_image)
        val productPriceMarket: TextView = itemView.findViewById(R.id.p_market_price)
        val productName: TextView =itemView.findViewById(R.id.p_name)
        val productSellingPrice: TextView =itemView.findViewById(R.id.p_price)
        val productStock: TextView =itemView.findViewById(R.id.p_stock)
        val productCategory: TextView =itemView.findViewById(R.id.p_category)
        val descriptionText: TextView =itemView.findViewById(R.id.tv_description)

//        val updateStockBtn: Button =itemView.p_update_stock_btn
//        val deleteProductBtn: Button =itemView.findViewById(R.id.p_delete_btn)


    }
}