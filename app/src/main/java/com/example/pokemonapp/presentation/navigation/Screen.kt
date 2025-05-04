package com.example.pokemonapp.presentation.navigation

import android.net.Uri
import com.example.pokemonapp.core.NavArgs

/** Defines all navigation destinations for the app, bit of a type-safe way.
 * Each object holds its route pattern, and Detail provides a helper
 * to build the actual route string with a properly encoded parameter.
 */
sealed class Screen(val route: String) {
    object List : Screen("list")
    object Detail : Screen("detail/{${NavArgs.ID_OR_NAME}}") {
        fun createRoute(idOrName: String) =
            "detail/${Uri.encode(idOrName)}"
    }
}