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

class ProductsAdapter internal constructor(context: Context, data: List<Product>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {


    private val mData: List<Product> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null
    private val context: Context = context

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]
        if (item != null) {
            holder.myTextView.text = item.name_ar
            Glide.with(holder.productImageView.context).load(item.image)
                .into(holder.productImageView)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var myTextView: TextView = itemView.findViewById(R.id.product_item_title_tv)
        var moreButton: Button = itemView.findViewById(R.id.more_btn)
        var productImageView: ImageView = itemView.findViewById(R.id.product_iv)
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            moreButton.setOnClickListener(this)
        }
    }

    // convenience method for getting data at click position
    fun getItem(id: Int): Product {
        return mData[id]
    }

    // allows clicks events to be caught
    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}