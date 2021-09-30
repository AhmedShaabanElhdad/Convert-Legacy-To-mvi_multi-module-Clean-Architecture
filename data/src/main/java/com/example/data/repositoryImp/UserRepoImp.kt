package com.example.data.repositoryImp

import com.example.data.network.HalanService
import com.example.data.pref.SharedPref
import com.example.data.pref.TOKEN
import com.example.domain.usecase.LoginRequstParams
import com.example.domain.core.ViewState
import com.example.domain.repository.UserRepo
import com.example.entity.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepoImp @Inject constructor(
    private val service: HalanService,
    private val pref: SharedPref
) : UserRepo {
    override fun login(params: LoginRequstParams): Flow<ViewState<Profile>> {
        return flow {
            try {

                emit(ViewState.Loading)
                val response = service.login(username = params.username, password = params.password)
                response.body()?.apply {
                    //todo add datasource factory and seperate it from repository
                    if (response.code() == 200 && this.status == "OK") {
                        saveToken(this.token)
                        emit(ViewState.Success(this.profile))
                    } else
                        emit(ViewState.Error(response.message()))
                }

            } catch (e: Exception) {
                emit(ViewState.Error(e.message ?: "Error"))
            }
        }
    }

    private fun saveToken(token: String?) {
        token?.let {
            pref.save(TOKEN, it)
        }
    }
}