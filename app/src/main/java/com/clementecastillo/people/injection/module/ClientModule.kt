package com.clementecastillo.people.injection.module

import android.content.Context
import com.clementecastillo.core.client.ApiClient
import com.clementecastillo.core.client.transaction.TransactionRequest
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.BuildConfig
import com.clementecastillo.people.client.TransactionRequestImpl
import com.clementecastillo.people.client.api.PeopleApiClient
import com.clementecastillo.people.client.api.RestApiClient
import com.clementecastillo.people.client.api.typeadapter.GenderTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ClientModule {

    @Provides
    @Singleton
    fun transactionRequest(): TransactionRequest = TransactionRequestImpl()

    @Provides
    @Singleton
    fun gsonBase(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Person.GENDER::class.java, GenderTypeAdapter())
            .create()
    }

    @Provides
    @Singleton
    fun cache(context: Context): Cache {
        val cacheDirName = "api_cache"
        val size = (10 * 1024 * 1024).toLong()
        val cacheDir = File(context.cacheDir, cacheDirName)
        return Cache(cacheDir, size)
    }

    @Provides
    @Singleton
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }
    }

    @Provides
    @Singleton
    fun okHttpClient(logger: HttpLoggingInterceptor, cache: Cache): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun restApiClient(okHttpClient: OkHttpClient, gson: Gson): RestApiClient {
        return Retrofit.Builder()
            .baseUrl(RestApiClient.HOST)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(RestApiClient::class.java)
    }

    @Provides
    @Singleton
    fun apiClient(
        restApiClient: RestApiClient
    ): ApiClient {
        return PeopleApiClient(restApiClient)
    }
}