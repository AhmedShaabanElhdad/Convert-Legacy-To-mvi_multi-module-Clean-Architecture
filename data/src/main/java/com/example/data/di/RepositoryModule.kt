package com.example.data.di

import com.example.data.network.HalanService
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
        service: HalanService,
        pref: SharedPref
    ): UserRepo {
        return UserRepoImp(service, pref)
    }

    @Provides
    @Singleton
    fun provideProductRepo(
        service: HalanService,
        pref: SharedPref
    ): ProductRepo {
        return ProductRepoImp(service, pref)
    }

}
