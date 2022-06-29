package com.bonifaciotech.coinsapi.di

import com.bonifaciotech.coinsapi.NameRepository
import com.bonifaciotech.coinsapi.data.NameApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideExchangeApi(moshi: Moshi) : NameApi {
        return Retrofit.Builder()
            .baseUrl("http://dbonifacio.somee.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(NameApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExchangeRepository(nameApi: NameApi): NameRepository {
        return NameRepository(nameApi)
    }
}