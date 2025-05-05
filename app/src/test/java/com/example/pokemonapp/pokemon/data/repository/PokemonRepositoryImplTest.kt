package com.example.pokemonapp.pokemon.data.repository

import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.data.datasource.api.model.PokemonDetailResponseDto
import com.example.pokemonapp.data.datasource.api.model.PokemonSummaryDto
import com.example.pokemonapp.data.datasource.remote.RemoteDataSource
import com.example.pokemonapp.data.repository.PokemonRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonRepositoryImplTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val dispatcherProvider = object : DispatcherProvider {
        override val io = dispatcher
        override val default = dispatcher
        override val main = dispatcher
    }

    private val remoteDataSource = mockk<RemoteDataSource>()
    private lateinit var repository: PokemonRepositoryImpl

    @BeforeEach
    fun setup() {
        repository = PokemonRepositoryImpl(remoteDataSource, dispatcherProvider)
    }

    @Test
    fun `getAll returns list of PokemonSummaryDto`() = runTest {
        val expected = listOf(
            PokemonSummaryDto(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/")
        )
        coEvery { remoteDataSource.fetchAll() } returns
                com.example.pokemonapp.data.datasource.api.model.PokemonListResponseDto(expected)

        val result = repository.getPokemonList().first()
        assertEquals(expected, result)
    }

    @Test
    fun `getDetail returns PokemonDetailResponseDto`() = runTest {
        val expected = PokemonDetailResponseDto(
            id = 1,
            name = "bulbasaur",
            height = 7,
            sprites = com.example.pokemonapp.data.datasource.api.model.SpritesDto("https://image.png")
        )
        coEvery { remoteDataSource.fetchDetail("bulbasaur") } returns expected

        val result = repository.getPokemonDetail("bulbasaur").first()
        assertEquals(expected, result)
    }
}
