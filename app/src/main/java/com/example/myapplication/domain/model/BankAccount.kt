package com.example.myapplication.domain.model

data class BankAccount(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val currency: String,
    val cardType: String,
    val balance: Int,
    val cardLogo: String?
)