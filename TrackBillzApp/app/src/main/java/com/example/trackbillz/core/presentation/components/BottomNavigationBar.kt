package com.example.trackbillz.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.trackbillz.core.presentation.utils.BottomNavItem
import com.example.trackbillz.core.presentation.utils.BottomNavItemsList

@Composable
fun BottomNavigationBar(
    navController: NavController,
    items: List<BottomNavItem> = BottomNavItemsList,
    containerColor: Color = MaterialTheme.colorScheme.background
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        NavigationBar(
            containerColor = containerColor
        ) {
            items.forEachIndexed { index, bottomNavItem ->
                val ifIncluded = currentRoute?.hierarchy?.any { it.hasRoute(bottomNavItem.screen::class) }

                NavigationBarItem(
                    selected = ifIncluded == true,
                    label = {
                        Text(text = bottomNavItem.label)
                    },
                    onClick = {
                        navController.navigate(bottomNavItem.screen){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            launchSingleTop = true
//                        restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomNavItem.icon,
                            contentDescription = bottomNavItem.label
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = Color.Gray,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        }
    }
}

