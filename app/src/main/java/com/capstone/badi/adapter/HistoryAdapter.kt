package com.capstone.badi.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.badi.R
import com.capstone.badi.model.HistoryModel

class HistoryAdapter(var listProduct: ArrayList<HistoryModel>, var context: Activity?
) : RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.tvDate)
        var items: TextView = itemView.findViewById(R.id.tvItems)
        var price: TextView = itemView.findViewById(R.id.tvPrice)
        var status: TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (date, items, price, status) = listProduct[position]
        holder.date.text = date
        holder.items.text = items
        holder.price.text = price
        holder.status.text = status
        holder.itemView.setOnClickListener {
        }

    }

    override fun getItemCount(): Int = listProduct.size


}
