package com.example.domain.core

import android.util.Patterns
import com.example.domain.R
import com.example.domain.di.IoDispatcher
import com.example.domain.repository.UserRepo
import com.example.entity.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class LoginRequstParams(
    val username:String,
    val password:String
)

class LoginUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userRepo: UserRepo
) : FlowUseCase<LoginRequstParams, Profile>(ioDispatcher) {

    override fun execute(parameters: LoginRequstParams): Flow<ViewState<Profile>> {
        return userRepo.login(parameters)
    }

}