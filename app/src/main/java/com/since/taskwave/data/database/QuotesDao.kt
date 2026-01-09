package com.since.taskwave.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.since.taskwave.data.modal.database.Quotes
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Upsert
    suspend fun insertOrUpdate(quotes: Quotes)

    @Delete
    suspend fun deleteData(quotes: Quotes)


    @Query("SELECT * FROM quotes ORDER BY time DESC")
    fun getAllQuotes() : Flow<List<Quotes>>
}