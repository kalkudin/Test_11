package com.example.myapplication.presentation.state

import com.example.myapplication.presentation.model.BankAccountView

data class FromState(
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val accountList : List<BankAccountView>? = null
)