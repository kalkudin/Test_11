package com.example.myapplication.data.repository

import com.example.myapplication.data.common.HandleResponse
import com.example.myapplication.data.common.Resource
import com.example.myapplication.data.mapper.mapToDomain
import com.example.myapplication.data.mapper.toDomain
import com.example.myapplication.data.service.BankAccountService
import com.example.myapplication.domain.model.BankAccount
import com.example.myapplication.domain.repository.UserAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAccountRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val bankAccountService: BankAccountService
) : UserAccountRepository {
    override suspend fun getUserAccounts(): Flow<Resource<List<BankAccount>>> {
        return handleResponse.safeApiCall {
            bankAccountService.getBankAccounts()
        }.mapToDomain { dtoList ->
            dtoList.map { it.toDomain() }
        }
    }
}