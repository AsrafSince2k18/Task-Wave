package com.since.taskwave.domain.error

sealed interface Result<out D,out E: Error> {

    data class Success<out D>(val success:D) : Result<D, Nothing>

    data class Error<out E: com.since.taskwave.domain.error.Error>(val error:E) : Result<Nothing,E>

}