package com.example.myapplication.presentation.mapper

import com.example.myapplication.domain.model.BankAccount
import com.example.myapplication.presentation.model.BankAccountView

fun BankAccount.toPresentation(): BankAccountView {
    return BankAccountView(
        id = id.toString(),
        accountName = accountName,
        accountNumber = accountNumber,
        currency = currency,
        cardType = cardType,
        balance = formatBalance(balance),
        cardLogo = mapCardLogoToResource(cardLogo)
    )
}

private fun formatBalance(balance: Int): String {
    return "$balance"
}

private fun mapCardLogoToResource(cardLogo: String?): String? {
    return cardLogo
}