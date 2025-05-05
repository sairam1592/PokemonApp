package com.example.pokemonapp.di

import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.data.datasource.api.ApiService
import com.example.pokemonapp.data.datasource.remote.RemoteDataSource
import com.example.pokemonapp.data.datasource.remote.RemoteDataSourceImpl
import com.example.pokemonapp.domain.repository.PokemonRepository
import com.example.pokemonapp.data.repository.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideRemoteDataSource(
        apiService: ApiService
    ): RemoteDataSource =
        RemoteDataSourceImpl(apiService)

    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        dispatchers: DispatcherProvider
    ): PokemonRepository =
        PokemonRepositoryImpl(remoteDataSource, dispatchers)
}