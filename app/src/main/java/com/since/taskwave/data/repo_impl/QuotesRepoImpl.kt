package com.since.taskwave.data.repo_impl

import com.since.taskwave.data.database.QuotesDao
import com.since.taskwave.data.modal.database.Quotes
import com.since.taskwave.domain.repo.db.QuotesRepo
import kotlinx.coroutines.flow.Flow

class QuotesRepoImpl(
    private val quotesDao: QuotesDao
) : QuotesRepo {
    override suspend fun insertUpdate(quotes: Quotes) {
        quotesDao.insertOrUpdate(quotes=quotes)
    }

    override suspend fun deleteData(quotes: Quotes) {
        quotesDao.deleteData(quotes=quotes)
    }

    override fun getAllData(): Flow<List<Quotes>> {
        return quotesDao.getAllQuotes()
    }
}