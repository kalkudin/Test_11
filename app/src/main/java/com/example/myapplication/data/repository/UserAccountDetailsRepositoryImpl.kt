package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.common.Resource
import com.example.myapplication.data.mapper.mapToDomain
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.service.BankAccountDetailsService
import com.example.myapplication.domain.model.BankAccount
import com.example.myapplication.domain.repository.UserAccountDetailsRepository
import com.example.myapplication.domain.repository.UserAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAccountDetailsRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val bankAccountDetailsService: BankAccountDetailsService
) : UserAccountDetailsRepository{
    override suspend fun getUserAccountDetails(): Flow<Resource<BankAccount>> {
        return handleResponse.safeApiCall { bankAccountDetailsService.getAccountDetails() }.mapToDomain { it.toDomain() }
    }
}