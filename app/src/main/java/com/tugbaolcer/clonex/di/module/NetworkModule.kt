package com.tugbaolcer.clonex.di.module

import android.app.ActivityManager
import android.content.Context
import com.tugbaolcer.clonex.BuildConfig
import com.tugbaolcer.clonex.network.AppApi
import com.tugbaolcer.clonex.utils.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
    fun provideAuthInterceptor(context: Context, controllerName: String): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            context.apply {
                request = request.newBuilder().build()
            }
            val response = chain.proceed(request)
            return@Interceptor response
        }

    }

    @Singleton
    @Provides
    fun provideRetrofitInterface(
        context: Context,
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
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(AppApi::class.java)
    }


    @Provides
    fun provideControllerName(context: Context): String {
        val mgr = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val tasks = mgr.appTasks
        return if (tasks != null && tasks.isNotEmpty()) tasks.first().taskInfo.topActivity!!.className else context.javaClass.simpleName
    }

}