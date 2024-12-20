package com.tugbaolcer.clonex.di.module

import com.tugbaolcer.clonex.BuildConfig
import com.tugbaolcer.clonex.network.AppApi
import com.tugbaolcer.clonex.utils.BASE_URL
import com.tugbaolcer.clonex.utils.TYPE_CULTURE_TR
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()

            val url = request.url.newBuilder()
                .addQueryParameter("language", TYPE_CULTURE_TR)
                .build()

            val requestBuilder = request.newBuilder()
                .url(url)
                .addHeader("Authorization", BuildConfig.API_KEY)
                .addHeader("accept", "application/json")

            request = requestBuilder.build()

            return@Interceptor chain.proceed(request)
        }

    }

    @Singleton
    @Provides
    fun provideRetrofitInterface(
        authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): AppApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .readTimeout(40, TimeUnit.SECONDS)
                    .connectTimeout(40, TimeUnit.SECONDS)
                    .addInterceptor(authInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .build()
            )

            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AppApi::class.java)
    }
}