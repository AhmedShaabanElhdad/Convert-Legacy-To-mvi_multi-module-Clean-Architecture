package com.example.authfeature.login

import androidx.lifecycle.viewModelScope
import com.example.authfeature.R
import com.example.common_ui.BaseViewModel
import com.example.domain.usecase.LoginRequstParams
import com.example.domain.usecase.LoginUseCase
import com.example.domain.core.ViewState
import com.example.domain.usecase.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
) : BaseViewModel<LoginContract.LoginEvent, LoginContract.State, LoginContract.LoginEffect>() {

    init {
        setEvent(LoginContract.LoginEvent.refreshToken)
    }

    /**
     * Create initial State of Views
     */
    override fun createInitialState(): LoginContract.State {
        return LoginContract.State(
            LoginContract.LoginViewState.Idle
        )
    }

    /**
     * Handle each event
     */
    override fun handleEvent(event: LoginContract.LoginEvent) {
        when (event) {
            is LoginContract.LoginEvent.login -> {
                if (!isUserNameValid(event.username)) {
                    setState {
                        copy(
                            loginViewState = LoginContract.LoginViewState.LoginFormState(
                                usernameError = R.string.invalid_username
                            )
                        )
                    }
                } else if (!isPasswordValid(event.password)) {
                    setState {
                        copy(
                            loginViewState = LoginContract.LoginViewState.LoginFormState(
                                passwordError = R.string.invalid_password
                            )
                        )
                    }
                } else {
                    login(event.username, event.password)
                }
            }

            is LoginContract.LoginEvent.refreshToken -> {
                refreshToken()
            }
        }
    }


    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        val regex = "^[a-zA-Z]*\$"

        return username.matches("^[a-zA-Z]*$".toRegex()) && username.length in 6..15
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    private fun refreshToken(){
        viewModelScope.launch {
            // Set Loading
            refreshTokenUseCase.invoke(Any()).collect {
                when (it) {
                    is ViewState.Success -> setState {
                        copy(
                            loginViewState = LoginContract.LoginViewState.Success(
                                it.data
                            )
                        )
                    }
                    is ViewState.Error -> setState { copy(loginViewState = LoginContract.LoginViewState.showPage) }
                    else -> setState { copy(loginViewState = LoginContract.LoginViewState.showPage) }
                }
            }
        }
    }



    private fun login(username: String, password: String) {
        viewModelScope.launch {
            // Set Loading
            loginUseCase.invoke(LoginRequstParams(username, password)).collect {
                when (it) {
                    is ViewState.Success -> setState {
                        copy(
                            loginViewState = LoginContract.LoginViewState.Success(
                                it.data
                            )
                        )
                    }
                    is ViewState.Loading -> setState { copy(loginViewState = LoginContract.LoginViewState.Loading) }
                    is ViewState.Error -> setEffect { LoginContract.LoginEffect.Error }
                }
            }
        }
    }
}
