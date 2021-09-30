package com.example.authfeature.login

import com.example.common_ui.UiEffect
import com.example.common_ui.UiEvent
import com.example.common_ui.UiState
import com.example.entity.Profile


class LoginContract {

    data class State(val loginViewState: LoginViewState) : UiState

    sealed class LoginEvent : UiEvent {
        class login(val username:String,val password:String): LoginEvent()
    }

    sealed class LoginViewState {
        object Idle : LoginViewState()
        object Loading : LoginViewState()
        data class Success(val profile: Profile) : LoginViewState()
        data class LoginFormState (val usernameError: Int? = null, val passwordError: Int? = null):LoginViewState()
    }

    sealed class LoginEffect : UiEffect {
        object Error : LoginEffect()
    }

}