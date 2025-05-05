package com.example.pokemonapp.pokemon.data.mapper

import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import com.example.pokemonapp.data.mapper.PokemonSummaryListToDomainMapper
import com.example.pokemonapp.domain.model.PokemonSummary
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PokemonSummaryListToDomainMapperTest {

    private val mapper = PokemonSummaryListToDomainMapper()

    @Test
    fun `mapper capitalizes first letter of each pokemon name`() {
        val input = listOf(
            PokemonSummaryDto(name = "pikachu", url = "url1"),
            PokemonSummaryDto(name = "bulbasaur", url = "url2")
        )

        val expected = listOf(
            PokemonSummary(name = "Pikachu"),
            PokemonSummary(name = "Bulbasaur")
        )

        val result = mapper(input)

        assertEquals(expected, result)
    }

    @Test
    fun `mapper handles empty list`() {
        val result = mapper(emptyList())
        assertEquals(emptyList(), result)
    }
}
