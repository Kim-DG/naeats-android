package com.checkmooney.naeats.di

import com.checkmooney.naeats.data.MenuDataSource
import com.checkmooney.naeats.data.MenuFakeDataSource
import com.checkmooney.naeats.data.MenuRepository
import com.checkmooney.naeats.service.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
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
            .baseUrl("http://TODO")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
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
