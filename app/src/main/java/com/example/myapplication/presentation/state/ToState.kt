package com.example.myapplication.presentation.state

import com.example.myapplication.presentation.model.BankAccountView

data class ToState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val accountList: BankAccountView? = null
)