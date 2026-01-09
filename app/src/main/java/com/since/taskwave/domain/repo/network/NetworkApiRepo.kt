package com.since.taskwave.domain.repo.network

import com.since.taskwave.data.modal.network.QuoteNetwork
import com.since.taskwave.domain.error.DataError
import com.since.taskwave.domain.error.Result
import retrofit2.Response

interface NetworkApiRepo {

    suspend fun getQuotes(): Result<QuoteNetwork?, DataError.NetworkError>

}