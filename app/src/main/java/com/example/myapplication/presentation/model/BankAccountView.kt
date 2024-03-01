package com.example.myapplication.presentation.model

data class BankAccountView(
    val id: String,
    val accountName: String,
    val accountNumber: String,
    val currency: String,
    val cardType: String,
    val balance: String,
    val cardLogo: String?
)