package com.example.pokemonapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonapp.common.ui.UiState
import com.example.pokemonapp.core.DispatcherProvider
import com.example.pokemonapp.core.NavArgs
import com.example.pokemonapp.core.toUiMessage
import com.example.pokemonapp.domain.model.PokemonDetail
import com.example.pokemonapp.domain.usecase.GetPokemonDetailUseCase
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
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDetailUseCase: GetPokemonDetailUseCase,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val idOrName: String = checkNotNull(savedStateHandle[NavArgs.ID_OR_NAME])

    private val _uiState = MutableStateFlow<UiState<PokemonDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<PokemonDetail>> = _uiState

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch(dispatchers.io) {
            getDetailUseCase(idOrName)
                .onStart { _uiState.value = UiState.Loading }
                .catch { e ->
                    _uiState.value = UiState.Error(e.toUiMessage())
                }
                .collect { detail ->
                    _uiState.value = UiState.Success(detail)
                }
        }
    }
}
