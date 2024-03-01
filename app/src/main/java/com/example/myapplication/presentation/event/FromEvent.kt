package com.example.myapplication.presentation.event

sealed class FromEvent {
    data object GetUserAccounts : FromEvent()
}