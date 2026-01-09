package com.since.taskwave.domain.error

sealed interface DataError : Error {

    enum class NetworkError : DataError{
        NO_INTERNET,
        NOT_FOUND,
        UN_KNOW,
        SERVER_ERROR
    }

}