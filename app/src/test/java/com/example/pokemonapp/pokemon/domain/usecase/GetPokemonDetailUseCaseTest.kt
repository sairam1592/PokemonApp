package com.example.pokemonapp.pokemon.domain.usecase

import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.mapper.PokemonDetailDtoToDomainMapper
import com.example.pokemonapp.domain.model.PokemonDetail
import com.example.pokemonapp.domain.repository.PokemonRepository
import com.example.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetPokemonDetailUseCaseTest {

    private val repo = mockk<PokemonRepository>()
    private val mapper = mockk<PokemonDetailDtoToDomainMapper>()
    private val useCase = GetPokemonDetailUseCase(repo, mapper)

    @Test
    fun `invoke returns mapped PokemonDetail when repo succeeds`() = runTest {
        val dto = PokemonDetailResponseDto(
            1,
            "bulbasaur",
            7,
            sprites = com.example.pokemonapp.data.datasource.api.model.SpritesDto("img-url")
        )
        val expected = PokemonDetail(1, "Bulbasaur", 7, "img-url")

        coEvery { repo.getPokemonDetail("bulbasaur") } returns flow { emit(dto) }
        every { mapper(dto) } returns expected

        val result = useCase("bulbasaur").single()

        assertEquals(expected, result)
    }

    @Test
    fun `invoke throws when repo throws`() = runTest {
        val exception = RuntimeException("Something went wrong")

        coEvery { repo.getPokemonDetail("bulbasaur") } returns flow { throw exception }

        try {
            useCase("bulbasaur").single()
            assert(false) { "Expected exception not thrown" }
        } catch (e: Exception) {
            assertEquals("Something went wrong", e.message)
        }
    }
}
