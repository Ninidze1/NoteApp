package com.ninidze.data.di

import com.ninidze.data.remote.AuthInterceptor
import com.ninidze.data.remote.NotesApi
import com.ninidze.data.repository.NotesRepositoryImpl
import com.ninidze.domain.repository.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindNotesRepository(notesRepositoryImpl: NotesRepositoryImpl): NotesRepository

    companion object {

        @Provides
        internal fun provideRemoteDataSource(
            authInterceptor: AuthInterceptor
        ): NotesApi {
            val okHttpClientBuilder = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)

            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(loggingInterceptor)

            val okHttpClient = okHttpClientBuilder.build()

            return Retrofit.Builder()
                .baseUrl("https://api.framework.ge/playground/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NotesApi::class.java)
        }
    }
}
