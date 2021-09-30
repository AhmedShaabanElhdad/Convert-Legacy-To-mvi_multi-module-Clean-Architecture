package com.example.productfeature.productdetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.productfeature.R
import com.bumptech.glide.Glide
import com.example.productfeature.databinding.ImageViewItemBinding
import com.example.productfeature.databinding.ProductItemBinding

class ImagesAdapter internal constructor( data: List<String>) :
    RecyclerView.Adapter<ImagesAdapter.ViewHolder>() {
    private val mData: List<String> = data

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.image_view_item,
            parent,
            false
        ))
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
    inner class ViewHolder internal constructor(val binding: ImageViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            Glide.with(binding.productImageIV.context).load(item).into(binding.productImageIV)
        }


    }

}