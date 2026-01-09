package com.since.taskwave.data.di

import android.content.Context
import androidx.room.Room
import com.since.taskwave.data.database.QuotesDatabase
import com.since.taskwave.data.repo_impl.QuotesRepoImpl
import com.since.taskwave.domain.repo.db.QuotesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : QuotesDatabase{
        return Room.databaseBuilder(context, QuotesDatabase::class.java,"Quotes")
            .build()
    }


    @Provides
    @Singleton
    fun provideQuotesRepoImpl(quotesDatabase: QuotesDatabase) : QuotesRepo{
        return QuotesRepoImpl(quotesDao = quotesDatabase.quotesDao())
    }


}