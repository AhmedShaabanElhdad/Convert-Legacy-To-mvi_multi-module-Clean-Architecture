package com.example.productfeature


import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.productfeature.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

import com.example.domain.core.ViewState
import com.example.domain.usecase.GetProductUseCase
import com.example.domain.usecase.LogoutUseCase
import com.example.productfeature.productlist.GetProductContract
import com.example.productfeature.productlist.GetProductViewModel
import com.google.common.truth.Truth
import com.example.productfeature.utils.TestDataGenerator

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class ProductViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    @MockK
    private lateinit var getProductUseCase: GetProductUseCase

    @MockK
    private lateinit var logoutUseCase: LogoutUseCase

    private lateinit var viewModel : GetProductViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        viewModel = GetProductViewModel(
            getProductUseCase = getProductUseCase,
            logoutUseCase = logoutUseCase
        )
    }

    @Test
    fun test_fetch_product_success() = runBlockingTest {

        val products = TestDataGenerator.generateProducts()
        val productsFlow = flowOf(ViewState.Loading,ViewState.Success(products))

        // Given
        coEvery { getProductUseCase.invoke(any()) } returns productsFlow

        // When && Assertions
        viewModel.uiState.test {
            // Call method inside of this scope

            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                GetProductContract.State(
                    GetProductViewState = GetProductContract.GetProductViewState.Idle,
                )
            )

            viewModel.setEvent(GetProductContract.GetProductEvent.getProduct)


            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                GetProductContract.State(
                    GetProductViewState = GetProductContract.GetProductViewState.Loading,
                )
            )
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected.GetProductViewState as GetProductContract.GetProductViewState.Success).products
            Truth.assertThat(expected).isEqualTo(
                GetProductContract.State(
                    GetProductViewState = GetProductContract.GetProductViewState.Success(products = products)
                )
            )
            Truth.assertThat(expectedData).containsExactlyElementsIn(products)

            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        coVerify { getProductUseCase.invoke(any()) }

    }


    @Test
    fun test_fetch_product_fails() = runBlockingTest {

        val products = TestDataGenerator.generateProducts()
        val productsErrorFlow = flowOf(ViewState.Loading,ViewState.Error("Error"))

        // Given
        coEvery { getProductUseCase.invoke(any()) } returns productsErrorFlow

        // When && Assertions (UiState)
        viewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            viewModel.setEvent(GetProductContract.GetProductEvent.getProduct)
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                GetProductContract.State(
                    GetProductViewState = GetProductContract.GetProductViewState.Idle,
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                GetProductContract.State(
                    GetProductViewState = GetProductContract.GetProductViewState.Loading,
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // When && Assertions (UiEffect)
        viewModel.effect.test {
            // Expect ShowError Effect
            val expected = expectItem()
            val expectedData = (expected as GetProductContract.GetProductEffect.Error).message
            Truth.assertThat(expected).isEqualTo(
                GetProductContract.GetProductEffect.Error("Error")
            )
            Truth.assertThat(expectedData).isEqualTo("Error")
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        coVerify { getProductUseCase.invoke(any()) }

    }
}