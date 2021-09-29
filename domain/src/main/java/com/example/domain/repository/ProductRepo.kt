package com.example.domain.repository

import com.example.domain.core.ViewState
import com.example.entity.Product
import com.example.entity.Profile
import kotlinx.coroutines.flow.Flow

interface ProductRepo {
    fun getUserProduct(): Flow<ViewState<List<Product>>>
}