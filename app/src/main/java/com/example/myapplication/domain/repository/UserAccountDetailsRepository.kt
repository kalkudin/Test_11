package com.example.myapplication.domain.repository

import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.model.BankAccount
import kotlinx.coroutines.flow.Flow

interface UserAccountDetailsRepository {
    suspend fun getUserAccountDetails() : Flow<Resource<BankAccount>>
}