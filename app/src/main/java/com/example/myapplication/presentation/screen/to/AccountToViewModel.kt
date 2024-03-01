package com.example.myapplication.presentation.screen.to

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.usecase.GetBankAccountsDetailsUseCase
import com.example.myapplication.presentation.event.FromEvent
import com.example.myapplication.presentation.event.ToEvent
import com.example.myapplication.presentation.mapper.toPresentation
import com.example.myapplication.presentation.state.ToState
import com.example.myapplication.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountToViewModel @Inject constructor(private val getBankAccountsDetailsUseCase: GetBankAccountsDetailsUseCase) : ViewModel() {

    private val _toFlow = MutableStateFlow(ToState())
    val toFlow : StateFlow<ToState> = _toFlow.asStateFlow()

    fun onEvent(event: ToEvent) {
        viewModelScope.launch {
            when(event) {
                is ToEvent.GetUserAccount -> getBankAccountsDetails(event.cardNumber, event.id, event.number)
                is ToEvent.ResetState -> resetState()
            }
        }
    }

    private suspend fun getBankAccountsDetails(cardNum: String, id: String, number: String) {
        getBankAccountsDetailsUseCase(cardNum, id, number).collect { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _toFlow.value = ToState(isLoading = true)
                }
                is Resource.Error -> {
                    Log.d("HomeViewModel", resource.errorType.toString())
                    _toFlow.value = ToState(errorMessage = getErrorMessage(resource.errorType))
                }
                is Resource.Success -> { resource.data.let { data ->
                        val accountView = data.toPresentation()
                        _toFlow.value = ToState(accountList = accountView)
                    }
                }
            }
        }
    }

    private fun resetState() {
        _toFlow.value = ToState()
    }
}