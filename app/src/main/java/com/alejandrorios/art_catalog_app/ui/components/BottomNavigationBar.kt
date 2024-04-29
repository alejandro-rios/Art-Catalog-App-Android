package com.alejandrorios.art_catalog_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alejandrorios.art_catalog_app.ui.navigation.NavigationItem

data class BottomNavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(
            BottomNavigationItem(
                title = "Home",
                route = NavigationItem.Home.route,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
            ),
            BottomNavigationItem(
                title = "Favorites",
                route = NavigationItem.ArtworkFavorites.route,
                selectedIcon = Icons.Filled.Favorite,
                unselectedIcon = Icons.Outlined.Favorite,
            ),
        )

        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                    label = {
                        Text(text = item.title)
                    },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            imageVector = if (currentRoute == item.route) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}
