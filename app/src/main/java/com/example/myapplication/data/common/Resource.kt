package com.example.myapplication.data.common

sealed class Resource<out T> {
    data class Loading<Nothing>(val loading: Boolean) : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val errorType: ErrorType) : Resource<Nothing>()
}