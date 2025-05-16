package com.ninidze.framework.di

import com.ninidze.data.di.Token
import com.ninidze.framework.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Token
    fun provideToken(): String = BuildConfig.API_TOKEN
}
