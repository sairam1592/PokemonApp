package com.example.pokemonapp.pokemon.presentation.viewmodel

import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.domain.model.PokemonSummary
import com.example.pokemonapp.domain.usecase.FetchPokemonListUseCase
import com.example.pokemonapp.presentation.viewmodel.PokemonListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val dispatchers = object : DispatcherProvider {
        override val io = testDispatcher
        override val main = testDispatcher
        override val default = testDispatcher
    }

    private val fetchListUseCase = mockk<FetchPokemonListUseCase>()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given success response, emits Success`() = runTest {
        val pokemonList = listOf(PokemonSummary("Pikachu"), PokemonSummary("Bulbasaur"))
        coEvery { fetchListUseCase() } returns flowOf(pokemonList)

        val viewModel = PokemonListViewModel(fetchListUseCase, dispatchers)

        assertTrue(viewModel.uiState.value is UiState.Success)
        assertEquals(pokemonList, (viewModel.uiState.value as UiState.Success).data)
    }

    @Test
    fun `given IOException, emits Error with no-internet message`() = runTest {
        val exception = IOException("No internet connection. Please check your network.")
        coEvery { fetchListUseCase() } returns flow { throw exception }

        val viewModel = PokemonListViewModel(fetchListUseCase, dispatchers)

        assertTrue(viewModel.uiState.value is UiState.Error)
        assertEquals(exception.message, (viewModel.uiState.value as UiState.Error).message)
    }
}
