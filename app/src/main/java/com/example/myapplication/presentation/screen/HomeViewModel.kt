package com.example.myapplication.presentation.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.common.Resource
import com.example.myapplication.domain.usecase.GetCourseUseCase
import com.example.myapplication.presentation.event.HomeEvent
import com.example.myapplication.presentation.state.CurrencyState
import com.example.myapplication.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCourseUseCase: GetCourseUseCase) : ViewModel() {

    private val _currencyFlow = MutableStateFlow(CurrencyState())
    val currencyFlow : StateFlow<CurrencyState> = _currencyFlow.asStateFlow()

    fun onEvent(event : HomeEvent) {
        viewModelScope.launch {
            when(event) {
                HomeEvent.GetCurrency -> getCurrency()
            }
        }
    }

    private suspend fun getCurrency() {
        getCourseUseCase().collect { resource ->
            when (resource) {
                is Resource.Error ->
                    _currencyFlow.value = CurrencyState(errorMessage = getErrorMessage(resource.errorType), isLoading = false)
                is Resource.Loading ->
                    _currencyFlow.value = CurrencyState(isLoading = true)
                is Resource.Success -> {
                    Log.d("ViewModel", resource.data.toString())
                    _currencyFlow.value = CurrencyState(currency = resource.data, isLoading = false)
                }
            }
        }
    }
}