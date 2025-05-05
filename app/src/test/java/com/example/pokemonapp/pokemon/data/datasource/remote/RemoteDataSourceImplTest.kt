package com.example.pokemonapp.pokemon.data.datasource.remote

import com.example.pokemonapp.data.datasource.api.ApiService
import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonListResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import com.example.pokemonapp.data.datasource.remote.RemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoteDataSourceImplTest {

    private lateinit var apiService: ApiService
    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @BeforeEach
    fun setUp() {
        apiService = mockk()
        remoteDataSource = RemoteDataSourceImpl(apiService)
    }

    @Test
    fun `fetchAll should return list of Pokemon summaries`() = runTest {
        val expectedResponse = PokemonListResponseDto(
            results = listOf(PokemonSummaryDto("bulbasaur", "url"))
        )
        coEvery { apiService.getPokemonList(any(), any()) } returns expectedResponse

        val actual = remoteDataSource.fetchAll()

        assertEquals(expectedResponse, actual)
        coVerify { apiService.getPokemonList(any(), any()) }
    }

    @Test
    fun `fetchDetail should return Pokemon detail`() = runTest {
        val expectedDetail = PokemonDetailResponseDto(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = com.example.pokemonapp.data.datasource.api.model.SpritesDto("url")
        )
        coEvery { apiService.getPokemonDetail("bulbasaur") } returns expectedDetail

        val actual = remoteDataSource.fetchDetail("bulbasaur")

        assertEquals(expectedDetail, actual)
        coVerify { apiService.getPokemonDetail("bulbasaur") }
    }
}
