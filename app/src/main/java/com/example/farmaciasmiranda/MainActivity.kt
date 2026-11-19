package com.example.farmaciasmiranda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.ComposeView
import androidx.activity.enableEdgeToEdge
import com.example.farmaciasmiranda.navigation.FarmaciaNavigation
import com.example.farmaciasmiranda.ui.theme.FarmaciasMirandaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(ComposeView(this).apply {
            setContent {
                FarmaciasMirandaTheme { FarmaciaNavigation() }
            }
        })
    }
}
