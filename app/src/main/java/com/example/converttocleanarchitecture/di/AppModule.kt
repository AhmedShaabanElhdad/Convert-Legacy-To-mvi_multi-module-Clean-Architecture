package com.example.converttocleanarchitecture.di

import android.content.Context
import com.example.converttocleanarchitecture.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApp(@ApplicationContext app: Context): MyApp {
        return app as MyApp
    }



}