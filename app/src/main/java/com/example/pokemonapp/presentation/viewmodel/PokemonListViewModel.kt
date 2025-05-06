package com.example.pokemonapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.common.ui.Constants
import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.core.toUiMessage
import com.example.pokemonapp.domain.model.PokemonSummary
import com.example.pokemonapp.domain.usecase.FetchPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val fetchListUseCase: FetchPokemonListUseCase,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PokemonSummary>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PokemonSummary>>> = _uiState

    init {
        loadList()
    }

    fun loadList() {
        viewModelScope.launch(dispatchers.io) {
            fetchListUseCase()
                .onStart { _uiState.value = UiState.Loading }
                .catch { e -> _uiState.value = UiState.Error(e.toUiMessage()) }
                .collect { data ->
                    if (data.isEmpty()) {
                        _uiState.value = UiState.Error(Constants.LIST_EMPTY_MSG)
                    } else {
                        _uiState.value = UiState.Success(data)
                    }
                }
        }
    }
}
