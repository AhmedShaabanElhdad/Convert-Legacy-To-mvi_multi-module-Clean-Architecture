package com.example.domain.repository

import com.example.domain.usecase.LoginRequstParams
import com.example.domain.core.ViewState
import com.example.entity.Profile
import kotlinx.coroutines.flow.Flow

interface UserRepo {
    fun login(params: LoginRequstParams): Flow<ViewState<Profile>>
    fun refreshToken(): Flow<ViewState<Profile>>
    fun logout()
}