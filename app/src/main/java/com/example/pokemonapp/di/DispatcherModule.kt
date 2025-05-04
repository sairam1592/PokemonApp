package com.example.pokemonapp.di

import com.example.pokemonapp.core.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    fun provideDispatcher(): DispatcherProvider =
        object : DispatcherProvider {
            override val io get() = Dispatchers.IO
            override val default get() = Dispatchers.Default
            override val main get() = Dispatchers.Main
        }
}