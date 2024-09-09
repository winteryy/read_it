package com.winteryy.readit.data

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: ReadItError): Result<Nothing>()
}

fun Throwable.toException(): Exception {
    return when(this) {
        is Exception -> this
        else -> RuntimeException(this)
    }
}

interface ReadItError {
    val message: String
}

sealed interface LocalError: ReadItError {
    data object NoMatchItemError: LocalError {
        override val message: String
            get() = "no matched item"
    }

    data class LocalDbError(val msg: String?): LocalError {
        override val message: String
            get() = msg ?: "Unknown Error"
    }
}

sealed interface RemoteError: ReadItError {

    data class NetworkError(val msg: String?): LocalError {
        override val message: String
            get() = msg ?: "Unknown Error"
    }
}