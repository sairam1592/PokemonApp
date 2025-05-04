package com.example.pokemonapp.domain.usecase

import com.example.pokemonapp.data.mapper.PokemonSummaryListToDomainMapper
import com.example.pokemonapp.data.repository.PokemonRepository
import com.example.pokemonapp.domain.model.PokemonSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchPokemonListUseCase @Inject constructor(
    private val repo: PokemonRepository,
    private val mapper: PokemonSummaryListToDomainMapper
) {
    operator fun invoke(): Flow<List<PokemonSummary>> =
        repo.getAll()
            .map { dtoList -> mapper(dtoList) }
}