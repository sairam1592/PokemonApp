package com.example.pokemonapp.data.repository

import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getAll(): Flow<List<PokemonSummaryDto>>
    fun getDetail(idOrName: String): Flow<PokemonDetailResponseDto>
}