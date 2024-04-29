package com.alejandrorios.art_catalog_app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alejandrorios.art_catalog_app.ui.screens.artwork_main.ArtworkMainScreen
import com.alejandrorios.art_catalog_app.ui.theme.Art_catalog_appTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinAndroidContext {
                Art_catalog_appTheme {
                    ArtworkMainScreen()
                }
            }
        }
    }
}

