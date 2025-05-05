package com.example.pokemonapp.data.datasource.api.model

data class PokemonListResponseDto(
    val results: List<PokemonSummaryDto>?
)

data class PokemonSummaryDto(val name: String?, val url: String?)