package com.example.myapplication.data.common

sealed class ErrorType {
    data class UnknownError(val message: String) : ErrorType()
    data object SocketTimeout:ErrorType()
    data object UnknownHost:ErrorType()
    data object SSLHandshake:ErrorType()
    data object Http:ErrorType()
    data object IO:ErrorType()

    data object InvalidCardNumber : ErrorType()
    data object InvalidPhoneNumber : ErrorType()
    data object InvalidNumber : ErrorType()
}