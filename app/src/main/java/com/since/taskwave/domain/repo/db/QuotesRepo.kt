package com.since.taskwave.domain.repo.db

import com.since.taskwave.data.modal.database.Quotes
import kotlinx.coroutines.flow.Flow

interface QuotesRepo {

    suspend fun insertUpdate(quotes: Quotes)

    suspend fun deleteData(quotes: Quotes)

    fun getAllData() : Flow<List<Quotes>>

}