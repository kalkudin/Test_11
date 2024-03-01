package com.example.myapplication.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.service.BankAccountDetailsService
import com.example.myapplication.data.service.BankAccountService
import com.example.myapplication.data.service.CurrencyService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideResponseHandler(): HandleResponse {
        return HandleResponse()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideBankAccountService(retrofit: Retrofit): BankAccountService {
        return retrofit.create(BankAccountService::class.java)
    }

    @Provides
    @Singleton
    fun provideBankAccountDetailsService(retrofit: Retrofit): BankAccountDetailsService {
        return retrofit.create(BankAccountDetailsService::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }
}