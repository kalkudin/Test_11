package com.example.myapplication.data.service

import com.example.myapplication.data.model.BankAccountDto
import retrofit2.Response
import retrofit2.http.GET

interface BankAccountDetailsService {
    @GET("4253786f-3500-4148-9ebc-1fe7fb9dc8d5?account_number=EU67JG7744036080300903")
    suspend fun getAccountDetails() : Response<BankAccountDto>
}