package com.jruizdev.rickdex.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jruizdev.rickdex.ui.character_detail.composables.CharacterDetailScreen
import com.jruizdev.rickdex.ui.characters.composables.CharactersScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        // Home Destination
        composable<Screen.Home> {
            CharactersScreen(
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.CharacterDetail(itemId = id.toString()))
                }
            )
        }

        // Detail Destination with Arguments
        composable<Screen.CharacterDetail> {
            CharacterDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}