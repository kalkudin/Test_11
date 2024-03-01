package com.example.myapplication.data.service

import com.example.myapplication.data.model.BankAccountDto
import retrofit2.Response
import retrofit2.http.GET

interface BankAccountService {
    @GET("5c74ec0e-5cc1-445e-b64b-b76b286b215f")
    suspend fun getBankAccounts() : Response<List<BankAccountDto>>
}