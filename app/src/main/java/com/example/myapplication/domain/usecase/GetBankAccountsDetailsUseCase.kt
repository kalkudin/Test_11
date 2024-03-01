package com.example.myapplication.domain.usecase

import com.example.myapplication.data.common.ErrorType
import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.model.BankAccount
import com.example.myapplication.domain.repository.UserAccountDetailsRepository
import com.example.myapplication.domain.util.ValidationUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetBankAccountsDetailsUseCase @Inject constructor(private val userAccountDetailsRepository: UserAccountDetailsRepository,
    private val validationUtil: ValidationUtil){
    suspend operator fun invoke(accountNumber : String, userId : String, phoneNumber : String) : Flow<Resource<BankAccount>> {

        if (validationUtil.isAccountNumberValid(accountNumber)) {
            return flowOf(Resource.Error(ErrorType.InvalidCardNumber))
        }

        if (!validationUtil.isUserIdValid(userId)) {
            return flowOf(Resource.Error(ErrorType.InvalidNumber))
        }

        if (!validationUtil.isPhoneNumberValid(phoneNumber)) {
            return flowOf(Resource.Error(ErrorType.InvalidPhoneNumber))
        }

        return userAccountDetailsRepository.getUserAccountDetails()
    }
}