package com.checkmooney.naeats.di

import android.app.Activity
import android.content.Context
import com.checkmooney.naeats.MainActivity
import com.checkmooney.naeats.data.MenuDataSource
import com.checkmooney.naeats.data.MenuFakeDataSource
import com.checkmooney.naeats.data.MenuRepository
import com.checkmooney.naeats.service.ApiService
import com.checkmooney.naeats.service.GoogleService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
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
