package com.example.domain

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.domain.core.ViewState
import com.example.domain.di.IoDispatcher
import com.example.domain.repository.ProductRepo
import com.example.domain.repository.UserRepo
import com.example.domain.usecase.GetProductUseCase
import com.example.domain.usecase.LoginRequstParams
import com.example.domain.usecase.LoginUseCase
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
class LoginUseCaseTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: UserRepo

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create GetPostsUseCase before every test
        loginUseCase = LoginUseCase(
            ioDispatcher = mainCoroutineRule.dispatcher,
            userRepo = repository
        )
    }

    @Test
    fun test_login_success() = runBlockingTest {

        val username = ""
        val password = ""

        val profile = TestDataGenerator.generateProfile()
        val userFlow = flowOf(ViewState.Success(profile))

        // Given
        coEvery { repository.login(LoginRequstParams(username,password)) } returns userFlow

        // When & Assertions
        val result = loginUseCase.invoke(LoginRequstParams(username, password))
        result.test {
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected as ViewState.Success).data
            Truth.assertThat(expected).isInstanceOf(ViewState.Success::class.java)
            Truth.assertThat(expectedData).isSameInstanceAs(profile)
            expectComplete()
        }

        // Then
        coVerify { repository.login(LoginRequstParams(username,password)) }

    }

    @Test
    fun test_login_fail() = runBlockingTest {

        val errorFlow = flowOf(ViewState.Error("Error"))

        // Given
        val username = ""
        val password = ""
        coEvery { repository.login(LoginRequstParams(username,password)) } returns errorFlow

        // When & Assertions
        val result = loginUseCase.invoke(LoginRequstParams(username,password))
        result.test {
            // Expect Resource.Error
            Truth.assertThat(expectItem()).isInstanceOf(ViewState.Error::class.java)
            expectComplete()
        }

        // Then
        coVerify { repository.login(LoginRequstParams(username,password)) }

    }

}