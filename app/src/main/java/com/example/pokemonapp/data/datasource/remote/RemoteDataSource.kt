package com.example.pokemonapp.data.datasource.remote

import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonListResponseDto

interface RemoteDataSource {
    suspend fun fetchAll(): PokemonListResponseDto?
    suspend fun fetchDetail(idOrName: String): PokemonDetailResponseDto?
}