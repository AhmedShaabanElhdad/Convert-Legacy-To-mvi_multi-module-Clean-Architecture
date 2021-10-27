package com.example.data.repositoryImp

import com.example.data.network.ChallangeService
import com.example.data.pref.SharedPref
import com.example.data.pref.TOKEN
import com.example.domain.core.ViewState
import com.example.domain.repository.ProductRepo
import com.example.entity.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepoImp @Inject constructor(
    private val service: ChallangeService,
    private val pref: SharedPref
) : ProductRepo {


    override fun getUserProduct(): Flow<ViewState<List<Product>>> {
        return flow {
            try {

                emit(ViewState.Loading)
                val response = service.getProduct(getToken())
                response.body()?.apply {
                    if (response.code() == 200 && this.status == "OK") {
                        emit(ViewState.Success(this.products))
                    } else
                        emit(ViewState.Error(this.message))
                }

            } catch (e: Exception) {
                emit(ViewState.Error(e.message ?: "Error"))
            }
        }
    }

    private fun getToken(): String {
        return "Bearer ${pref.load(TOKEN,"")}"
    }
}