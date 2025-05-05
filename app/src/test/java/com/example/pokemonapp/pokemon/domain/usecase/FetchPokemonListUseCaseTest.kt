package com.example.pokemonapp.pokemon.domain.usecase

import com.example.pokemonapp.data.mapper.PokemonSummaryListToDomainMapper
import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import com.example.pokemonapp.domain.model.PokemonSummary
import com.example.pokemonapp.domain.repository.PokemonRepository
import com.example.pokemonapp.domain.usecase.FetchPokemonListUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FetchPokemonListUseCaseTest {

    private val repository = mockk<PokemonRepository>()
    private val mapper = mockk<PokemonSummaryListToDomainMapper>()
    private val useCase = FetchPokemonListUseCase(repository, mapper)

    @Test
    fun `invoke should return mapped PokemonSummary list when repository returns data`() = runTest {
        val dtoList = listOf(PokemonSummaryDto("bulbasaur", "url"))
        val domainList = listOf(PokemonSummary("Bulbasaur"))

        every { mapper(dtoList) } returns domainList
        coEvery { repository.getPokemonList() } returns flow { emit(dtoList) }

        val result = useCase().single()

        assertEquals(domainList, result)
    }

    @Test
    fun `invoke should throw exception when repository throws`() = runTest {
        val exception = RuntimeException("Failed to fetch")

        coEvery { repository.getPokemonList() } returns flow { throw exception }

        try {
            useCase().single()
            assert(false) { "Expected exception not thrown" }
        } catch (e: Exception) {
            assertEquals(exception.message, e.message)
        }
    }
}
