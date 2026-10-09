package com.jruizdev.rickdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.jruizdev.rickdex.ui.characters.composables.CharactersScreen
import com.jruizdev.rickdex.ui.theme.RickDexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RickDexTheme {
                val navController = rememberNavController()
                CharactersScreen(navController = navController)
            }
        }
    }
}
