package com.checkmooney.naeats.di

import android.content.Context
import com.checkmooney.naeats.data.MenuDataSource
import com.checkmooney.naeats.data.MenuFakeDataSource
import com.checkmooney.naeats.data.MenuRepository
import com.checkmooney.naeats.data.UserLocalDataSource
import com.checkmooney.naeats.service.ApiService
import com.checkmooney.naeats.service.GoogleService
import com.checkmooney.naeats.service.SharedPrefService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Interceptor.Companion.invoke
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(userLocalDataSource: UserLocalDataSource): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addInterceptor(invoke { chain ->
                chain.proceed(
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${userLocalDataSource.refreshToken}")
                        .build()
                )
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://13.209.72.236:4000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserService(@ApplicationContext context: Context): GoogleService {
        return GoogleService(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefService(@ApplicationContext context: Context): SharedPrefService {
        return SharedPrefService(context)
    }
}

@Module
@InstallIn(ActivityRetainedComponent::class)
object MainModule {
    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class FakeMenuDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteMenuDataSource


    @Provides
    @FakeMenuDataSource
    fun provideFakeMenuDataSource(): MenuDataSource = MenuFakeDataSource

    @Provides
    fun provideMenuRepository(
        @FakeMenuDataSource fakeMenuDataSource: MenuDataSource
    ): MenuRepository {
        return MenuRepository(fakeMenuDataSource)
    }
}
