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
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Product
import com.example.productfeature.databinding.ProductItemBinding

class ProductsAdapter internal constructor(data: List<Product>, val click: (Product) -> Unit) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {


    private val mData: List<Product> = data

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_item,
                parent,
                false
            )
        )
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
    inner class ViewHolder internal constructor(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Product) {

            if (item != null) {
                binding.productItemTitleTv.text = item.name_ar
                Glide.with(binding.productIv.context).load(item.image)
                    .into(binding.productIv)
            }

            binding.moreBtn.setOnClickListener {
                click(item)
            }
        }

    }


}