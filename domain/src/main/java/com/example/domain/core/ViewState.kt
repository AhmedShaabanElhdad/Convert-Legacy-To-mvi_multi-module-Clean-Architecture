package com.example.domain.core


sealed class ViewState<out R> {
    data class Success<out T>(val data:T): ViewState<T>()
    data class Error(val error: String): ViewState<Nothing>()
    object Loading: ViewState<Nothing>()
    object Empty: ViewState<Nothing>()
}





