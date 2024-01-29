package com.appweaver.ricknroll.di

import com.appweaver.ricknroll.remote.RickNRollApi
import com.appweaver.ricknroll.repository.HomeRepository
import com.appweaver.ricknroll.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Provides
    @Singleton
    fun providesRickNRollApi(): RickNRollApi {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }).build()
            )
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesHomeRepository(rickNRollApi: RickNRollApi): HomeRepository {
        return HomeRepository(rickNRollApi)
    }
}
