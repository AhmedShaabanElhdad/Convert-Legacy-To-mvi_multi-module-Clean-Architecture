package com.example.domain.usecase

import com.example.domain.core.FlowUseCase
import com.example.domain.core.ViewState
import com.example.domain.di.IoDispatcher
import com.example.domain.repository.UserRepo
import com.example.entity.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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