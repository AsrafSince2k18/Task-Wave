package com.since.taskwave.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.since.taskwave.data.modal.database.Quotes

@Database(entities = [Quotes::class], version = 1)
abstract class QuotesDatabase : RoomDatabase(){

    abstract fun quotesDao() : QuotesDao

}