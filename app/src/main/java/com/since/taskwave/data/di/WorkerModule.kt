package com.since.taskwave.data.di

import android.content.Context
import com.since.taskwave.data.repo_impl.WorkerRepoImpl
import com.since.taskwave.domain.repo.worker.WorkerRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WorkerModule {


    @Provides
    @Singleton
    fun provideWorkerRepo(@ApplicationContext context: Context) : WorkerRepo{
        return WorkerRepoImpl(context=context)
    }

}