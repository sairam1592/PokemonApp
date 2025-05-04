package com.example.pokemonapp.data.datasource.api

import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(ApiConstants.POKEMON_LIST_PATH)
    suspend fun getPokemonList(
        @Query(ApiConstants.OFFSET_PARAM) offset: Int = ApiConstants.DEFAULT_OFFSET,
        @Query(ApiConstants.LIMIT_PARAM) limit: Int = ApiConstants.DEFAULT_LIMIT
    ): PokemonListResponseDto

    @GET(ApiConstants.POKEMON_DETAIL_PATH)
    suspend fun getPokemonDetail(
        @Path(ApiConstants.ID_PARAM) idOrName: String
    ): PokemonDetailResponseDto
}
