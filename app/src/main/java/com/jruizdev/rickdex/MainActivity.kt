package com.jruizdev.rickdex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jruizdev.rickdex.ui.composables.StatusBarProtection
import com.jruizdev.rickdex.ui.nav.AppNavigation
import com.jruizdev.rickdex.ui.theme.RickDexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RickDexTheme {
                AppNavigation()
                StatusBarProtection()
            }
        }
    }
}
