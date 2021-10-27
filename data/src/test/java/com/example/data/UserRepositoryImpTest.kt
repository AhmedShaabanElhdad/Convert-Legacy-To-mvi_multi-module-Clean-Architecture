package com.example.data

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.data.network.ChallangeService
import com.example.data.pref.PASSWORD
import com.example.data.pref.SharedPref
import com.example.data.pref.USERNAME
import com.example.data.repositoryImp.UserRepoImp
import com.example.data.response.LoginResponse
import com.example.data.utils.TestDataGenerator
import com.example.domain.core.ViewState
import com.example.domain.repository.UserRepo
import com.example.domain.usecase.LoginRequstParams
import com.google.common.truth.Truth
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
class UserRepositoryImpTest {

    @MockK
    private lateinit var apiService: ChallangeService

    @MockK
    private lateinit var pref: SharedPref

    private lateinit var repository: UserRepo


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        repository = UserRepoImp(
            service = apiService,
            pref = pref
        )
    }

    @Test
    fun test_login_with_correct_cradential_success() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        val username = ""
        val password = ""

        // Given
        coEvery { apiService.login(username, password) } returns Response.success(
            200,
            LoginResponse(status = "OK", profile = profile)
        )


        // When & Assertions
        val flow = repository.login(LoginRequstParams(username, password))
        flow.test {

            // Expect ViewState.Loading
            val expected1 = expectItem()
            Truth.assertThat(expected1).isInstanceOf(ViewState.Loading::class.java)

            // Expect ViewState.Success
            val expected = expectItem()

            Truth.assertThat(expected).isInstanceOf(ViewState.Success::class.java)
            val expectedData = (expected as ViewState.Success).data
            Truth.assertThat(expectedData).isSameInstanceAs(profile)
            expectComplete()
        }

        // Then
        coVerify { apiService.login("", "") }
    }


    @Test
    fun test_login_with_incorrect_cradential_success() = runBlockingTest {
        coEvery { apiService.login(any(), any()) } throws Exception()


        // When & Assertions
        val flow = repository.login(LoginRequstParams("", ""))
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
        coVerify { apiService.login("", "") }
    }


    @Test
    fun test_refresh_with_empty_cradential_success() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        coEvery { apiService.login("AhmedShaaban", "Ahmed5") } returns Response.success(
            200,
            LoginResponse(status = "OK", profile = profile)
        )
        coEvery { pref.load(USERNAME, "") } returns "AhmedShaaban"
        coEvery { pref.load(PASSWORD, "") } returns "Ahmed5"


        // When & Assertions
        val flow = repository.refreshToken()
        flow.test {
            // Expect ViewState.Success
            val expected = expectItem()
            Truth.assertThat(expected).isInstanceOf(ViewState.Success::class.java)
            val expectedData = (expected as ViewState.Success).data
            Truth.assertThat(expectedData).isSameInstanceAs(profile)
            expectComplete()
        }

        // Then
        coVerify {
            apiService.login(pref.load(USERNAME, ""), pref.load(PASSWORD, ""))
        }
//        coVerify { apiService.login("AhmedShaaban", "Ahmed5") }
    }

}