package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.BankAccountDto
import com.example.myapplication.domain.model.BankAccount

fun BankAccountDto.toDomain() : BankAccount {
    return BankAccount(
        id = id,
        accountName = accountName,
        accountNumber = accountNumber,
        currency = currencyType,
        cardType = cardType,
        balance = balance ?: 0,
        cardLogo = cardLogo
    )
}