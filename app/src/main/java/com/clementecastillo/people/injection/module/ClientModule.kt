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
import okhttp3.*
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
    fun connectionSpec(): ConnectionSpec {
        return ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
            .supportsTlsExtensions(true)
            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
            .cipherSuites(
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
            ).build()
    }

    @Provides
    @Singleton
    fun okHttpClient(logger: HttpLoggingInterceptor, cache: Cache, connectionSpec: ConnectionSpec): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .connectionSpecs(listOf(connectionSpec)) // Fix TLS error for Pre-Lollipop
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