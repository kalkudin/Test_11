package com.example.myapplication.presentation.state

import com.example.myapplication.presentation.model.BankAccountView

data class CurrencyState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val currency : Double? = null
)