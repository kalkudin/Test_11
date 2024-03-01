package com.example.myapplication.di

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.repository.CourseRepositoryImpl
import com.example.myapplication.data.repository.UserAccountDetailsRepositoryImpl
import com.example.myapplication.data.repository.UserAccountRepositoryImpl
import com.example.myapplication.data.service.BankAccountDetailsService
import com.example.myapplication.data.service.BankAccountService
import com.example.myapplication.data.service.CurrencyService
import com.example.myapplication.domain.repository.CourseRepository
import com.example.myapplication.domain.repository.UserAccountDetailsRepository
import com.example.myapplication.domain.repository.UserAccountRepository
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
    fun provideBankAccountRepository(handleResponse: HandleResponse, bankAccountService: BankAccountService) : UserAccountRepository {
        return UserAccountRepositoryImpl(handleResponse = handleResponse, bankAccountService = bankAccountService)
    }

    @Provides
    @Singleton
    fun provideBankAccountDetailsRepository(handleResponse: HandleResponse, bankAccountDetailsService: BankAccountDetailsService) : UserAccountDetailsRepository {
        return UserAccountDetailsRepositoryImpl(handleResponse = handleResponse, bankAccountDetailsService = bankAccountDetailsService)
    }

    @Provides
    @Singleton
    fun provideCourseRepository(handleResponse: HandleResponse, currencyService: CurrencyService) : CourseRepository {
        return CourseRepositoryImpl(handleResponse = handleResponse, currencyService = currencyService)
    }
}