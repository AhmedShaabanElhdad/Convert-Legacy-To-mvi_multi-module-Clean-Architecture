package com.example.domain

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.domain.core.ViewState
import com.example.domain.di.IoDispatcher
import com.example.domain.repository.ProductRepo
import com.example.domain.usecase.GetProductUseCase
import com.google.common.truth.Truth
import com.example.domain.utils.MainCoroutineRule
import com.example.domain.utils.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class GetProductsUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: ProductRepo

    private lateinit var getProductUseCase: GetProductUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create GetPostsUseCase before every test
        getProductUseCase = GetProductUseCase(
            ioDispatcher = mainCoroutineRule.dispatcher,
            productRepo = repository
        )
    }

    @Test
    fun test_get_post_comments_success() = runBlockingTest {

        val products = TestDataGenerator.generateProducts()
        val productsFlow = flowOf(ViewState.Success(products))

        // Given
        coEvery { repository.getUserProduct() } returns productsFlow

        // When & Assertions
        val result = getProductUseCase.invoke(Any())
        result.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as ViewState.Success).data
            Truth.assertThat(expected).isInstanceOf(ViewState.Success::class.java)
            Truth.assertThat(expectedData).containsExactlyElementsIn(products)
            expectComplete()
        }

        // Then
        coVerify { repository.getUserProduct() }

    }

    @Test
    fun test_get_post_comments_fail() = runBlockingTest {

        val errorFlow = flowOf(ViewState.Error("Error"))

        // Given
        coEvery { repository.getUserProduct() } returns errorFlow

        // When & Assertions
        val result = getProductUseCase.invoke(Any())
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(ViewState.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { repository.getUserProduct() }

    }

}