package com.example.pokemonapp.core

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Abstraction over coroutine dispatchers to allow easy testing and swapping implementations.
 */
interface DispatcherProvider {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}