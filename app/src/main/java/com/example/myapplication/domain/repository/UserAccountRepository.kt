package com.example.myapplication.domain.repository

import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.model.BankAccount
import kotlinx.coroutines.flow.Flow

interface UserAccountRepository {
    suspend fun getUserAccounts() : Flow<Resource<List<BankAccount>>>
}