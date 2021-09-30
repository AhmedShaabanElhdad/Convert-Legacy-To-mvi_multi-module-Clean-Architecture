package com.example.authfeature.login

import androidx.lifecycle.viewModelScope
import com.example.authfeature.R
import com.example.common_ui.BaseViewModel
import com.example.domain.core.LoginRequstParams
import com.example.domain.core.LoginUseCase
import com.example.domain.core.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginContract.LoginEvent, LoginContract.State, LoginContract.LoginEffect>() {


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
