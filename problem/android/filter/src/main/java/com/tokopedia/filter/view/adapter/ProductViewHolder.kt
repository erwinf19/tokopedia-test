package com.tokopedia.filter.view.adapter

import android.graphics.Paint
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tokopedia.filter.R
import com.tokopedia.filter.view.model.Product
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ProductViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.product_item, parent, false)) {

    private var ivProduct: ImageView? = null
    private var tvTitle: TextView? = null
    private var tvPrice: TextView? = null
    private var tvDiscPercentage: TextView? = null
    private var tvPriceSlashed: TextView? = null
    private var tvCityStore: TextView? = null
    private var cvDiscPercentage: CardView? = null
    private var containerMain: ConstraintLayout? = null

    init {
        ivProduct = itemView.findViewById(R.id.iv_product)
        tvTitle = itemView.findViewById(R.id.tv_title)
        tvPrice = itemView.findViewById(R.id.tv_price)
        cvDiscPercentage = itemView.findViewById(R.id.cv_disc_percentage)
        tvDiscPercentage = itemView.findViewById(R.id.tv_disc_percentage)
        tvPriceSlashed = itemView.findViewById(R.id.tv_price_slashed)
        tvCityStore = itemView.findViewById(R.id.tv_city_store)
        containerMain = itemView.findViewById(R.id.container_main)
    }

    fun bind(product: Product) {
        val discPercentage = product.discountPercentage.toString() + "%"
        ivProduct?.let { Glide.with(itemView.context).load(product.imageUrl).into(it) }
        tvTitle?.text = product.name
        tvPrice?.text = convertDecimalFormat(product.priceInt.toString())
        if (product.discountPercentage > 0) {
            tvDiscPercentage?.text = discPercentage
            cvDiscPercentage?.visibility = View.VISIBLE
            tvPriceSlashed?.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            tvPriceSlashed?.visibility = View.VISIBLE
        } else {
            cvDiscPercentage?.visibility = View.GONE
            tvPriceSlashed?.visibility = View.GONE
        }
        tvPriceSlashed?.text = convertDecimalFormat(product.slashedPriceInt.toString())
        tvCityStore?.text = product.shop.city
    }

    private fun convertDecimalFormat(population: String): String {
        val locale = Locale("in", "id")
        val symbols = DecimalFormatSymbols.getInstance(locale)
        symbols.groupingSeparator = '.'
        symbols.monetaryDecimalSeparator = ','
        val df = DecimalFormat.getCurrencyInstance(locale) as DecimalFormat
        val numberFormat = DecimalFormat(df.toPattern(), symbols)
        numberFormat.maximumFractionDigits = 0

        return if (TextUtils.isEmpty(population)) {
            numberFormat.format(0)
        } else {
            numberFormat.format(java.lang.Double.parseDouble(population))
        }
    }
}