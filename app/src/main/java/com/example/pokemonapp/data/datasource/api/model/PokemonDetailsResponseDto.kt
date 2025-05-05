package com.example.pokemonapp.data.datasource.api.model

data class PokemonDetailResponseDto(
    val id: Int?,
    val name: String?,
    val height: Int?,
    val sprites: SpritesDto?
)

data class SpritesDto(val front_default: String?)