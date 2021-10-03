package com.example.authfeature


import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.authfeature.login.LoginContract
import com.example.authfeature.login.LoginViewModel
import com.example.authfeature.utils.MainCoroutineRule
import com.example.authfeature.utils.TestDataGenerator
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
import com.example.domain.usecase.LoginUseCase
import com.example.domain.usecase.LogoutUseCase
import com.example.domain.usecase.RefreshTokenUseCase
import com.example.entity.Profile
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.emptyFlow
import org.mockito.ArgumentMatchers.any

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class ProductViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    @MockK
    private lateinit var refreshTokenUseCase: RefreshTokenUseCase

    @MockK
    private lateinit var loginUseCase: LoginUseCase

    private lateinit var viewModel : LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true) // turn relaxUnitFun on for all mocks
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            refreshTokenUseCase = refreshTokenUseCase
        )
    }

    @Test
    fun test_login_success() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        val profileFlow = flowOf(ViewState.Loading,ViewState.Success(profile))

        val username = "AhmedShaaban"
        val password = "Ahmed5"

        // Given
        coEvery { loginUseCase.invoke(any()) } returns profileFlow

        // When && Assertions
        viewModel.uiState.test {
            // Call method inside of this scope

            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Idle,
                )
            )

            viewModel.setEvent(LoginContract.LoginEvent.login(username, password))


            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Loading,
                )
            )
            // Expect Resource.Success
            val expected = expectItem()
            val expectedData = (expected.loginViewState as LoginContract.LoginViewState.Success).profile
            Truth.assertThat(expected).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Success(profile = profile)
                )
            )
            Truth.assertThat(expectedData).isSameInstanceAs(profile)

            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        coVerify { loginUseCase.invoke(any()) }

    }


    @Test
    fun test_login_fails() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        val profileErrorFlow = flowOf(ViewState.Loading,ViewState.Error("Error"))

        // Given
        coEvery { loginUseCase.invoke(any()) } returns profileErrorFlow

        val username = "AhmedShaaban"
        val password = "Ahmed5"

        // When && Assertions (UiState)
        viewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            viewModel.setEvent(LoginContract.LoginEvent.login(username,password))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Idle,
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Loading,
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // When && Assertions (UiEffect)
        viewModel.effect.test {
            // Expect ShowError Effect
            val expected = expectItem()
            val expectedData = (expected as LoginContract.LoginEffect.Error).message
            Truth.assertThat(expected).isEqualTo(
                LoginContract.LoginEffect.Error("Error")
            )
            Truth.assertThat(expectedData).isEqualTo("Error")
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // Then
        coVerify { loginUseCase.invoke(any()) }

    }


    @Test
    fun test_login_username_with_small_size_fails() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        val profileErrorFlow = emptyFlow<ViewState<Profile>>()

        // Given
        coEvery { loginUseCase.invoke(any()) } returns profileErrorFlow

        val username = "Ahmed"
        val password = "Ahmed5"

        // When && Assertions (UiState)
        viewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            viewModel.setEvent(LoginContract.LoginEvent.login(username,password))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Idle,
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.LoginFormState(usernameError = R.string.invalid_username),
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify(exactly = 0) { loginUseCase.invoke(any()) }

    }

    @Test
    fun test_login_username_with_big_size_more_15_fails() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        val profileErrorFlow = emptyFlow<ViewState<Profile>>()

        // Given
        coEvery { loginUseCase.invoke(any()) } returns profileErrorFlow

        val username = "AhmedAhmedAhmedAhmed"
        val password = "Ahmed5"

        // When && Assertions (UiState)
        viewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            viewModel.setEvent(LoginContract.LoginEvent.login(username,password))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Idle,
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.LoginFormState(usernameError = R.string.invalid_username),
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify(exactly = 0) { loginUseCase.invoke(any()) }

    }


    @Test
    fun test_login_username_contain_especial_character_size_fails() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        val profileErrorFlow = emptyFlow<ViewState<Profile>>()

        // Given
        coEvery { loginUseCase.invoke(any()) } returns profileErrorFlow

        val username = "Ahmed##"
        val password = "Ahmed5"

        // When && Assertions (UiState)
        viewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            viewModel.setEvent(LoginContract.LoginEvent.login(username,password))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Idle,
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.LoginFormState(usernameError = R.string.invalid_username),
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify(exactly = 0) { loginUseCase.invoke(any()) }

    }


    @Test
    fun test_login_empty_password_fails() = runBlockingTest {

        val profile = TestDataGenerator.generateProfile()
        val profileErrorFlow = emptyFlow<ViewState<Profile>>()

        // Given
        coEvery { loginUseCase.invoke(any()) } returns profileErrorFlow

        val username = "AhmedShaaban"
        val password = ""

        // When && Assertions (UiState)
        viewModel.uiState.test {
            // Call method inside of this scope
            // For more info, see https://github.com/cashapp/turbine/issues/19
            viewModel.setEvent(LoginContract.LoginEvent.login(username,password))
            // Expect Resource.Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.Idle,
                )
            )
            // Expect Resource.Loading
            Truth.assertThat(expectItem()).isEqualTo(
                LoginContract.State(
                    loginViewState = LoginContract.LoginViewState.LoginFormState(passwordError = R.string.invalid_password),
                )
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify(exactly = 0) { loginUseCase.invoke(any()) }

    }

}