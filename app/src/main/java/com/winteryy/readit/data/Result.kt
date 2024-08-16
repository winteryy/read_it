package com.winteryy.readit.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
}

fun Throwable.toException(): Exception {
    return when(this) {
        is Exception -> this
        else -> RuntimeException(this)
    }
}