package com.example.pokemonapp.data.repository

import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import com.example.pokemonapp.data.datasource.remote.RemoteDataSource
import com.example.pokemonapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dispatchers: DispatcherProvider
) : PokemonRepository {
    override fun getPokemonList(): Flow<List<PokemonSummaryDto>> =
        flow {
            val dto = remoteDataSource.fetchAll()
            emit(dto?.results.orEmpty())
        }.flowOn(dispatchers.io)

    override fun getPokemonDetail(idOrName: String): Flow<PokemonDetailResponseDto?> =
        flow {
            emit(remoteDataSource.fetchDetail(idOrName))
        }.flowOn(dispatchers.io)
}