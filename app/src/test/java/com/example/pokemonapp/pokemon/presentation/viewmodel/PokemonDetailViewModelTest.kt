package com.example.pokemonapp.pokemon.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.core.NavArgs
import com.example.pokemonapp.core.toUiMessage
import com.example.pokemonapp.domain.model.PokemonDetail
import com.example.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import com.example.pokemonapp.presentation.viewmodel.PokemonDetailViewModel
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
class PokemonDetailViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val dispatchers = object : DispatcherProvider {
        override val io = dispatcher
        override val default = dispatcher
        override val main = dispatcher
    }

    private val useCase = mockk<GetPokemonDetailUseCase>()
    private lateinit var savedStateHandle: SavedStateHandle

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        savedStateHandle = SavedStateHandle(mapOf(NavArgs.ID_OR_NAME to "bulbasaur"))
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given success response, emits Loading then Success`() = runTest {
        val detail = PokemonDetail(1, "Bulbasaur", 7, "image.png")
        coEvery { useCase("bulbasaur") } returns flowOf(detail)

        val viewModel = PokemonDetailViewModel(savedStateHandle, useCase, dispatchers)

        assertTrue(viewModel.uiState.value is UiState.Success)
        assertEquals(detail, (viewModel.uiState.value as UiState.Success).data)
    }

    @Test
    fun `given IOException, emits Loading then Error with proper message`() = runTest {
        val exception = IOException("no internet")
        coEvery { useCase("bulbasaur") } returns flow { throw exception }

        val viewModel = PokemonDetailViewModel(savedStateHandle, useCase, dispatchers)

        assertTrue(viewModel.uiState.value is UiState.Error)
        assertEquals(exception.toUiMessage(), (viewModel.uiState.value as UiState.Error).message)
    }
}


