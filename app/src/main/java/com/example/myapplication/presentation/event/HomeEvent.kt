package com.example.myapplication.presentation.event

sealed class HomeEvent {
    data object GetCurrency : HomeEvent()
}