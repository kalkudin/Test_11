package com.example.myapplication.domain.usecase

import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.model.BankAccount
import com.example.myapplication.domain.repository.UserAccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBankAccountsUseCase @Inject constructor(private val userAccountRepository: UserAccountRepository) {
    suspend operator fun invoke() : Flow<Resource<List<BankAccount>>> {
        return userAccountRepository.getUserAccounts()
    }
}