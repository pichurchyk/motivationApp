package com.pichurchyk.motivationapp.ui.common

sealed class ResponseStatus<T> {
    data class Success<T>(val value: T) : ResponseStatus<T>()
    data class Failure<T>(val throwable: Throwable) : ResponseStatus<T>()
}