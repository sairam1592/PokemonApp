package com.example.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.core.toUiMessage
import com.example.pokemonapp.domain.model.PokemonSummary
import com.example.pokemonapp.domain.usecase.FetchPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    fetchListUseCase: FetchPokemonListUseCase
) : ViewModel() {

    /** We use a cold Flow + stateIn instead of manually launching coroutines and using
     * MutableStateFlow to keep the ViewModel purely declarative. The Flow itself handles
     * loading, success, error and cancellation, and stateIn gives us a StateFlow that
     * the UI can collect lifecycle-safely without extra boilerplate.
     * (Same approach i have tried in PokemonDetailsViewModel)
     */

    val uiState: StateFlow<UiState<List<PokemonSummary>>> =
        fetchListUseCase()
            .map<List<PokemonSummary>, UiState<List<PokemonSummary>>> { UiState.Success(it) }
            .onStart { emit(UiState.Loading) }
            .catch { e ->
                emit(UiState.Error(e.toUiMessage()))
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily,
                initialValue = UiState.Loading
            )
}
