package com.example.pokemonapp.data.mapper

import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.domain.model.PokemonDetail
import javax.inject.Inject

/** Transforms the network DTO into our app’s domain model, normalizing field names,
 * formatting the Pokémon’s name, and providing a safe non-null image URL.
 * Keeps mapping logic centralized and makes the domain layer independent of Retrofit/Moshi types.
 */

class PokemonDetailDtoToDomainMapper @Inject constructor() {
    operator fun invoke(dto: PokemonDetailResponseDto?): PokemonDetail =
        PokemonDetail(
            id = dto?.id ?: -1,
            name = dto?.name?.replaceFirstChar(Char::titlecase) ?: "Unknown",
            height = dto?.height ?: 0,
            image = dto?.sprites?.front_default.orEmpty()
        )
}