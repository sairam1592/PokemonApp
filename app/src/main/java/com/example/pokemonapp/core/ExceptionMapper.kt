package com.example.pokemonapp.core

import com.example.pokemonapp.common.ui.Constants
import retrofit2.HttpException
import java.io.IOException

/**
 * Extension function to map a Throwable into a user-facing error message key.
 */
fun Throwable.toUiMessage(): String = when (this) {
    is IOException -> Constants.NO_INTERNET_ERROR
    is HttpException -> Constants.HTTP_ERROR
    else -> Constants.UNKNOWN_ERROR
}