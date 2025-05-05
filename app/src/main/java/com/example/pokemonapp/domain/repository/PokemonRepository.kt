package com.example.pokemonapp.domain.repository

import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemonList(): Flow<List<PokemonSummaryDto>>
    fun getPokemonDetail(idOrName: String): Flow<PokemonDetailResponseDto>
}