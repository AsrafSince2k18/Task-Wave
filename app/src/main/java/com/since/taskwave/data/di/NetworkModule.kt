package com.since.taskwave.data.di

import android.content.Context
import com.since.taskwave.data.network.api.NetworkApi
import com.since.taskwave.data.repo_impl.NetworkApiRepoImpl
import com.since.taskwave.data.repo_impl.ObserveNetworkImpl
import com.since.taskwave.domain.repo.network.NetworkApiRepo
import com.since.taskwave.domain.repo.network.ObserveNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level= HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(30.seconds)
            .readTimeout(30.seconds)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkApi(okHttpClient: OkHttpClient): NetworkApi{
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NetworkApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkApiImpl(networkApi: NetworkApi) : NetworkApiRepo{
        return NetworkApiRepoImpl(networkApi=networkApi)
    }

    @Provides
    @Singleton
    fun provideObserveNetwork(@ApplicationContext context: Context) : ObserveNetwork{
        return ObserveNetworkImpl(context=context)
    }

}