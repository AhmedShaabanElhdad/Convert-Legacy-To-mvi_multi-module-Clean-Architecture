package com.example.data

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.data.network.HalanService
import com.example.data.pref.SharedPref
import com.example.data.pref.TOKEN
import com.example.data.repositoryImp.ProductRepoImp
import com.example.data.response.ProductsList
import com.google.common.truth.Truth
import com.example.data.utils.TestDataGenerator
import com.example.domain.core.ViewState
import com.example.domain.repository.ProductRepo
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class ProductRepositoryImpTest {

    @MockK
    private lateinit var apiService: HalanService

    @MockK
    private lateinit var pref: SharedPref

    private lateinit var repository: ProductRepo


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        repository = ProductRepoImp(
            service = apiService,
            pref = pref
        )
    }

    @Test
    fun test_get_product_from_network_with_real_token_success() = runBlockingTest {

        val products = TestDataGenerator.generateProducts()

        // Given
        coEvery { pref.load(TOKEN, "") } returns "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImhtZWRzaGFhYmFuIiwiaW1hZ2UiOiJodHRwczovL2kucGljc3VtLnBob3Rvcy9pZC8xMDYyLzUwOTIvMzM5NS5qcGc_aG1hYz1vOW03cWVVNTF1T0xmWHZlcFhjVHJrMlpQaVNCSkVraWlPcC1RdnhqYS1rIiwibmFtZSI6ItmE2LfZitmBINi52YTZiiIsImVtYWlsIjoiZXZ5c2RtaGF3b3JidzFtQGdtYWlsLmNvbSIsInBob25lIjoiMDE5MTA3ODYwMTUiLCJpYXQiOjE2MzMxODg0NTcsImV4cCI6MTYzMzE4OTM4N30._CBlrTc3ipmfTSdlEojHAMsUBb994iukcbJzSS_vEoA"
        coEvery { apiService.getProduct(any()) } returns Response.success(200, ProductsList("OK",products= products))


        // When & Assertions
        val flow = repository.getUserProduct()
        flow.test {

            // Expect ViewState.Loading
            val expected1 = expectItem()
            Truth.assertThat(expected1).isInstanceOf(ViewState.Loading::class.java)

            // Expect ViewState.Success
            val expected = expectItem()
            Truth.assertThat(expected).isInstanceOf(ViewState.Success::class.java)
            val expectedData = (expected as ViewState.Success).data
            Truth.assertThat(expectedData).containsExactlyElementsIn(products)
            expectComplete()
        }

        // Then
        coVerify { apiService.getProduct(any()) }
    }



    @Test
    fun test_get_product_from_network_with_expired_token_success() = runBlockingTest {

        val products = TestDataGenerator.generateProducts()

        // Given
        coEvery { pref.load(TOKEN, "") } returns "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImhtZWRzaGFhYmFuIiwiaW1hZ2UiOiJodHRwczovL2kucGljc3VtLnBob3Rvcy9pZC8xMDYyLzUwOTIvMzM5NS5qcGc_aG1hYz1vOW03cWVVNTF1T0xmWHZlcFhjVHJrMlpQaVNCSkVraWlPcC1RdnhqYS1rIiwibmFtZSI6ItmE2LfZitmBINi52YTZiiIsImVtYWlsIjoiZXZ5c2RtaGF3b3JidzFtQGdtYWlsLmNvbSIsInBob25lIjoiMDE5MTA3ODYwMTUiLCJpYXQiOjE2MzMxODg0NTcsImV4cCI6MTYzMzE4OTM4N30._CBlrTc3ipmfTSdlEojHAMsUBb994iukcbJzSS_vEoA"
        coEvery { apiService.getProduct(any()) } throws Exception()


        // When & Assertions
        val flow = repository.getUserProduct()
        flow.test {

            // Expect ViewState.Loading
            val expected1 = expectItem()
            Truth.assertThat(expected1).isInstanceOf(ViewState.Loading::class.java)

            // Expect ViewState.Error
            val expected = expectItem()
            Truth.assertThat(expected).isInstanceOf(ViewState.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { apiService.getProduct(any()) }
    }

}