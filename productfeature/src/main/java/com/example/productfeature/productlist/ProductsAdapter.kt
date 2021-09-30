package com.example.productfeature.productlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.productfeature.R
import com.bumptech.glide.Glide
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.productfeature.productdetails.ProductDetailsFragment
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Product

class ProductsAdapter internal constructor(context: Context, data: List<Product>,val click:(Product)->Unit) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {


    private val mData: List<Product> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val context: Context = context

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mData[position]
        holder.bind(item)


    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var myTextView: TextView = itemView.findViewById(R.id.product_item_title_tv)
        var moreButton: Button = itemView.findViewById(R.id.more_btn)
        var productImageView: ImageView = itemView.findViewById(R.id.product_iv)


        fun bind(item: Product) {

            if (item != null) {
                myTextView.text = item.name_ar
                Glide.with(productImageView.context).load(item.image)
                    .into(productImageView)
            }

            moreButton.setOnClickListener{
                click(item)
            }
        }

    }



}