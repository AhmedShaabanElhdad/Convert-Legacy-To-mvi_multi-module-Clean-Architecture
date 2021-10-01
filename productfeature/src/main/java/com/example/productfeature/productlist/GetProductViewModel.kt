package com.example.productfeature.productlist

import androidx.lifecycle.viewModelScope
import com.example.common_ui.BaseViewModel
import com.example.domain.usecase.LoginRequstParams
import com.example.domain.usecase.LoginUseCase
import com.example.domain.core.ViewState
import com.example.domain.usecase.GetProductUseCase
import com.example.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class GetProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel<GetProductContract.GetProductEvent, GetProductContract.State, GetProductContract.GetProductEffect>() {

    init {
        setEvent(GetProductContract.GetProductEvent.getProduct)
    }


    /**
     * Create initial State of Views
     */
    override fun createInitialState(): GetProductContract.State {
        return GetProductContract.State(
            GetProductContract.GetProductViewState.Idle
        )
    }

    /**
     * Handle each event
     */
    override fun handleEvent(event: GetProductContract.GetProductEvent) {
        when (event) {
            is GetProductContract.GetProductEvent.getProduct -> {
                getProduct()
            }
            is GetProductContract.GetProductEvent.Logout -> {
                logoutUseCase.execute(Any())
            }
        }
    }


    private fun getProduct() {
        viewModelScope.launch {
            // Set Loading
            getProductUseCase.invoke(Any()).collect {
                when (it) {
                    is ViewState.Success -> setState {
                        copy(
                            GetProductViewState = GetProductContract.GetProductViewState.Success(
                                it.data
                            )
                        )
                    }
                    is ViewState.Loading -> setState { copy(GetProductViewState = GetProductContract.GetProductViewState.Loading) }
                    is ViewState.Error -> setEffect { GetProductContract.GetProductEffect.Error }
                }
            }
        }
    }
}
