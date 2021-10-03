package com.example.productfeature.productlist

import com.example.common_ui.UiEffect
import com.example.common_ui.UiEvent
import com.example.common_ui.UiState
import com.example.entity.Product
import com.example.entity.Profile


class GetProductContract {

    data class State(val GetProductViewState: GetProductViewState) : UiState

    sealed class GetProductEvent : UiEvent {
        object getProduct : GetProductEvent()
        object Logout : GetProductEvent()
    }

    sealed class GetProductViewState {
        object Idle : GetProductViewState()
        object Loading : GetProductViewState()
        data class Success(val products: List<Product>) : GetProductViewState()
    }

    sealed class GetProductEffect : UiEffect {
        data class Error(val message:String) : GetProductEffect()
    }

}