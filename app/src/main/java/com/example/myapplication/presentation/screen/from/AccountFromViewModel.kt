package com.example.myapplication.presentation.screen.from

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.usecase.GetBankAccountsUseCase
import com.example.myapplication.presentation.event.FromEvent
import com.example.myapplication.presentation.mapper.toPresentation
import com.example.myapplication.presentation.state.FromState
import com.example.myapplication.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountFromViewModel @Inject constructor(private val getBankAccountsUseCase: GetBankAccountsUseCase) : ViewModel(){

    private val _fromState = MutableStateFlow(FromState())
    val fromState : StateFlow<FromState> = _fromState.asStateFlow()

    fun onEvent(event : FromEvent) {
        viewModelScope.launch {
            when(event) {
                FromEvent.GetUserAccounts -> getBankAccounts()
            }
        }
    }

    private suspend fun getBankAccounts() {
        getBankAccountsUseCase().collect { resource ->
            when (resource) {
                is Resource.Error ->
                    _fromState.value = FromState(errorMessage = getErrorMessage(resource.errorType), isLoading = false)
                is Resource.Loading ->
                    _fromState.value = FromState(isLoading = true)
                is Resource.Success -> {
                    Log.d("ViewModel", resource.data.toString())
                    val accounts = resource.data.map { it.toPresentation() }
                    _fromState.value = FromState(accountList = accounts, isLoading = false)
                }
            }
        }
    }
}