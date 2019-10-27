package com.clementecastillo.people.injection.module

import com.clementecastillo.core.client.ApiClient
import com.clementecastillo.core.client.transaction.TransactionRequest
import com.clementecastillo.core.domain.data.Person
import com.clementecastillo.people.client.TransactionRequestImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.Single
import javax.inject.Singleton


@Module
class ClientModule {

    @Provides
    @Singleton
    fun transactionRequest(): TransactionRequest = TransactionRequestImpl()

    @Provides
    @Singleton
    fun gsonBase(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun apiClient(): ApiClient {
        return object : ApiClient {
            override fun getPeople(): Single<List<Person>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }
    }
}