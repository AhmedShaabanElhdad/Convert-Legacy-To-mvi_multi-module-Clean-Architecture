package com.example.domain.usecase

import com.example.domain.core.FlowUseCase
import com.example.domain.core.LoginRequstParams
import com.example.domain.core.ViewState
import com.example.domain.di.IoDispatcher
import com.example.domain.repository.ProductRepo
import com.example.domain.repository.UserRepo
import com.example.entity.Product
import com.example.entity.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetProductUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val productRepo: ProductRepo
) : FlowUseCase<Any, List<Product>>(ioDispatcher) {

    override fun execute(parameters: Any): Flow<ViewState<List<Product>>> {
        return productRepo.getUserProduct()
    }

}