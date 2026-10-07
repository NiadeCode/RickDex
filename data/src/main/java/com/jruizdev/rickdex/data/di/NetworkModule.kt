package com.jruizdev.rickdex.data.di

import com.jruizdev.rickdex.data.datasource.CharactersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun provideKotlinxSerializationConverterFactory(): Converter.Factory {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        return json.asConverterFactory("application/json; charset=utf-8".toMediaType())
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, converterFactory: Converter.Factory): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json; charset=utf-8".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCharactersApi(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }
}
