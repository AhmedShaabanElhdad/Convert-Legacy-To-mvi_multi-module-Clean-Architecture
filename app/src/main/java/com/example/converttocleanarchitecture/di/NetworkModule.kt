package com.example.converttocleanarchitecture.di

import com.example.data.network.ChallangeService
import com.example.converttocleanarchitecture.MyApp
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


const val BASE_URL = "https://assessment-sn12.halan.io"


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): ChallangeService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(ChallangeService::class.java)
    }

//    @Singleton
//    @Provides
//    @Named("uncached")
//    fun provideClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        return OkHttpClient.Builder().addInterceptor(interceptor).build();
//    }


    @Provides
    @Singleton
//    @Named("cached")
    fun provideCachedOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build();
    }



    @Provides
    @Singleton
    fun provideOkHttpCache(application: MyApp): Cache {
        val cacheSize: Long = 10 * 1024 * 1024; // 10 MiB
        return Cache(application.cacheDir, cacheSize);
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

/*
    @ApiBearerTokenInterceptorOkHttpClient
    @Singleton
    @Provides
    fun providesBearerTokenInterceptor(): Interceptor = BearerTokenInterceptor()*/


}


//@Qualifier
//@Retention(AnnotationRetention.BINARY)
//annotation class ApiBearerTokenInterceptorOkHttpClient
