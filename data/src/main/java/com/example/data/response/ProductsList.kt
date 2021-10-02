package com.example.data.response

import com.example.entity.Product

data class ProductsList(
    val status: String,
    val message: String="",
    val products: List<Product> = emptyList()
)