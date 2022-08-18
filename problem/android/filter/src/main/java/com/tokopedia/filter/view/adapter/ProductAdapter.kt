package com.tokopedia.filter.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tokopedia.filter.view.model.Product

class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private var productList = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: ArrayList<Product>){
        productList = newData
        notifyDataSetChanged()
    }
}