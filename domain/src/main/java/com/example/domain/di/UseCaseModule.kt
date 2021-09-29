package com.example.domain.di

import com.example.domain.core.LoginUseCase
import com.example.domain.di.IoDispatcher
import com.example.domain.repository.ProductRepo
import com.example.domain.repository.UserRepo
import com.example.domain.usecase.GetProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesGetProductUseCase(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        productRepo: ProductRepo
    ): GetProductUseCase = GetProductUseCase(ioDispatcher,productRepo )


    @Singleton
    @Provides
    fun providesLoginUseCase(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        userRepo: UserRepo
    ): LoginUseCase = LoginUseCase(ioDispatcher,userRepo )




}

