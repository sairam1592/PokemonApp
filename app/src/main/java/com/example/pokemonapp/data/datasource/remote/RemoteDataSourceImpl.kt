package com.example.pokemonapp.data.datasource.remote

import com.example.pokemonapp.data.datasource.api.ApiService
import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonListResponseDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val api: ApiService
) : RemoteDataSource {

    override suspend fun fetchAll(): PokemonListResponseDto? =
        api.getPokemonList()

    override suspend fun fetchDetail(idOrName: String): PokemonDetailResponseDto? =
        api.getPokemonDetail(idOrName)
}