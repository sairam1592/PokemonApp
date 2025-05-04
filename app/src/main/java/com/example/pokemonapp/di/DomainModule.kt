package com.example.pokemonapp.di

import com.example.pokemonapp.data.mapper.PokemonDetailDtoToDomainMapper
import com.example.pokemonapp.data.mapper.PokemonSummaryListToDomainMapper
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.domain.usecase.FetchPokemonListUseCase
import com.example.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun providePokemonSummaryListToDomainMapper(): PokemonSummaryListToDomainMapper =
        PokemonSummaryListToDomainMapper()

    @Provides
    fun providePokemonDetailDtoToDomainMapper(): PokemonDetailDtoToDomainMapper =
        PokemonDetailDtoToDomainMapper()

    @Provides
    fun provideFetchPokemonListUseCase(
        repo: PokemonRepository,
        mapper: PokemonSummaryListToDomainMapper
    ): FetchPokemonListUseCase =
        FetchPokemonListUseCase(repo, mapper)

    @Provides
    fun provideGetPokemonDetailUseCase(
        repo: PokemonRepository,
        mapper: PokemonDetailDtoToDomainMapper
    ): GetPokemonDetailUseCase =
        GetPokemonDetailUseCase(repo, mapper)
}