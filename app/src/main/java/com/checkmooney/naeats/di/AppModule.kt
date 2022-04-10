package com.checkmooney.naeats.di

import android.content.Context
import com.checkmooney.naeats.data.*
import com.checkmooney.naeats.service.LoginApiService
import com.checkmooney.naeats.service.GoogleService
import com.checkmooney.naeats.service.MainApiService
import com.checkmooney.naeats.service.SharedPrefService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideUserService(@ApplicationContext context: Context): GoogleService {
        return GoogleService(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefService(@ApplicationContext context: Context): SharedPrefService {
        return SharedPrefService(context)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "http://3.34.99.162:4000/api/"

    @LoginInterceptorOkHttpClient
    @Provides
    fun provideLoginInterceptorOkHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @MainInterceptorOkHttpClient
    @Provides
    fun provideMainInterceptorOkHttpClient(loginLocalDataSource: LoginLocalDataSource): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addNetworkInterceptor(interceptor)
            .addInterceptor(invoke { chain ->
                val builder = chain.request().newBuilder()
                if (loginLocalDataSource.accessToken.isNotEmpty()) {
                    builder.addHeader(
                        "Authorization",
                        "Bearer ${loginLocalDataSource.accessToken}" //TODO: 로그아웃 하고 아예 다른 계정으로 로그인 할 때 갱신되는지 확인 필요.
                    )
                }
                chain.proceed(builder.build())
            })
            .build()
    }

}


@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideLoginApiService(
        @BaseUrl baseUrl: String,
        @LoginInterceptorOkHttpClient okHttpClient: OkHttpClient,
    ): LoginApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApiService::class.java)
    }

    @Provides
    fun provideMainApiService(
        @BaseUrl baseUrl: String,
        @MainInterceptorOkHttpClient okHttpClient: OkHttpClient,
    ): MainApiService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MainApiService::class.java)
    }

    @Provides
    fun provideRemoteMenuDataSource(apiService: MainApiService): MenuRemoteDataSource =
        MenuRemoteDataSource(apiService)

    @Provides
    fun provideMenuRepository(
        remoteDataSource: MenuRemoteDataSource
    ): MenuRepository {
        return MenuRepository(remoteDataSource)
    }
}
