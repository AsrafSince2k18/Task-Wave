package com.since.taskwave.data.repo_impl

import com.since.taskwave.data.modal.network.QuoteNetwork
import com.since.taskwave.data.network.api.NetworkApi
import com.since.taskwave.domain.error.DataError
import com.since.taskwave.domain.error.Result
import com.since.taskwave.domain.repo.network.NetworkApiRepo
import java.nio.channels.UnresolvedAddressException

class NetworkApiRepoImpl(val networkApi: NetworkApi) : NetworkApiRepo {
    override suspend fun getQuotes(): Result<QuoteNetwork?, DataError.NetworkError> {
        return try {
            val response = networkApi.getQuotes()
            when(response.code()) {
                in 200..299 -> {
                    Result.Success(response.body())
                }

                400 -> {
                    Result.Error(DataError.NetworkError.NOT_FOUND)
                }

                in 500..599 -> {
                    Result.Error(DataError.NetworkError.SERVER_ERROR)
                }

                else -> {
                    Result.Error(DataError.NetworkError.UN_KNOW)
                }
            }
        }
        catch (e: UnresolvedAddressException){
            e.printStackTrace()
            Result.Error(DataError.NetworkError.NO_INTERNET)
        }
        catch (e: Exception){
            e.printStackTrace()
            Result.Error(DataError.NetworkError.UN_KNOW)
        }
    }
}