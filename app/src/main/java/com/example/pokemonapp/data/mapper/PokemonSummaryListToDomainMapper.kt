package com.example.pokemonapp.data.mapper

import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import com.example.pokemonapp.domain.model.PokemonSummary
import javax.inject.Inject

/** Converts a list of network summary DTOs into domain models,
 * applying title-casing to each Pokémon’s name and keeping mapping logic
 *  out of the core business/domain modules for cleaner separation of concerns.
 */

class PokemonSummaryListToDomainMapper @Inject constructor() {
    operator fun invoke(dtos: List<PokemonSummaryDto>): List<PokemonSummary> =
        dtos.map { mapSummary(it) }

    private fun mapSummary(dto: PokemonSummaryDto): PokemonSummary =
        PokemonSummary(name = dto.name.replaceFirstChar(Char::titlecase))
}