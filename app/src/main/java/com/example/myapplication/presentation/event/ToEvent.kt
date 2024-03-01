package com.example.myapplication.presentation.event

sealed class ToEvent {
    data class GetUserAccount(val cardNumber : String, val id: String, val number : String) : ToEvent()
    data object ResetState : ToEvent()
}