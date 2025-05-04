package com.example.pokemonapp.data.repository

import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import com.example.pokemonapp.data.datasource.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dispatchers: DispatcherProvider
) : PokemonRepository {
    override fun getAll(): Flow<List<PokemonSummaryDto>> =
        flow {
            val dto = remoteDataSource.fetchAll()
            emit(dto.results)
        }
            .flowOn(dispatchers.io)

    override fun getDetail(idOrName: String): Flow<PokemonDetailResponseDto> =
        flow {
            val detail = remoteDataSource.fetchDetail(idOrName)
            emit(detail)
        }
            .flowOn(dispatchers.io)
}