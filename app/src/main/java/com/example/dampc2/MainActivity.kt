package com.example.dampc2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.dampc2.navigation.AppNavigation
import com.example.dampc2.ui.theme.Dampc2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dampc2Theme {
                AppNavigation()
            }
        }
    }
}
