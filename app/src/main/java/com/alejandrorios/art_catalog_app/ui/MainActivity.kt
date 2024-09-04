package com.alejandrorios.art_catalog_app.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.alejandrorios.art_catalog_app.ui.screens.artwork_main.ArtworkMainScreen
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import dagger.hilt.android.AndroidEntryPoint

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Art_catalog_appTheme {
                ArtworkMainScreen()
            }
        }
    }
}

