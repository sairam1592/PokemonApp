package com.example.pokemonapp.pokemon.data.mapper

import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.SpritesDto
import com.example.pokemonapp.data.mapper.PokemonDetailDtoToDomainMapper
import com.example.pokemonapp.domain.model.PokemonDetail
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PokemonDetailDtoToDomainMapperTest {

    private val mapper = PokemonDetailDtoToDomainMapper()

    @Test
    fun `mapper capitalizes name and handles image URL`() {
        val dto = PokemonDetailResponseDto(
            id = 25,
            name = "pikachu",
            height = 4,
            sprites = SpritesDto(front_default = "https://image.url/pikachu.png")
        )

        val expected = PokemonDetail(
            id = 25,
            name = "Pikachu",
            height = 4,
            image = "https://image.url/pikachu.png"
        )

        val result = mapper(dto)

        assertEquals(expected, result)
    }

    @Test
    fun `mapper handles null image gracefully`() {
        val dto = PokemonDetailResponseDto(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = SpritesDto(front_default = null)
        )

        val expected = PokemonDetail(
            id = 1,
            name = "Bulbasaur",
            height = 7,
            image = ""
        )

        val result = mapper(dto)

        assertEquals(expected, result)
    }
}
