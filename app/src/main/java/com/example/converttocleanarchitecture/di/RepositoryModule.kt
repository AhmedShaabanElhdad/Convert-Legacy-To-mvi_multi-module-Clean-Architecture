package com.example.converttocleanarchitecture.di

import com.example.data.network.ChallangeService
import com.example.data.pref.SharedPref
import com.example.data.repositoryImp.ProductRepoImp
import com.example.data.repositoryImp.UserRepoImp
import com.example.domain.repository.ProductRepo
import com.example.domain.repository.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        service: ChallangeService,
        pref: SharedPref
    ): UserRepo {
        return UserRepoImp(service, pref)
    }

    @Provides
    @Singleton
    fun provideProductRepo(
        service: ChallangeService,
        pref: SharedPref
    ): ProductRepo {
        return ProductRepoImp(service, pref)
    }

}
