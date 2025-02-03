package com.sass.marshal.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.sass.marshal.R
import com.sass.marshal.ui.screen.listOfCrypto.ListOfCryptoScreenGraphNode
import com.sass.marshal.ui.screen.setting.SettingScreenGraphNode

@Composable
fun BottomNavigationBar(navController: NavController) {
    ListOfCryptoScreenGraphNode.route
    val items =
        listOf(
            NavigationButtons.ListOfCrypto,
            NavigationButtons.Setting,
        )
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute(navController) == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(painterResource(id = screen.icon), contentDescription = screen.title) },
                label = { Text(screen.title) },
            )
        }
    }
}

private sealed class NavigationButtons(
    val route: String,
    val title: String,
    val icon: Int,
) {
    data object ListOfCrypto : NavigationButtons(ListOfCryptoScreenGraphNode.route, "Home", R.drawable.ic_bitcoin)

    data object Setting : NavigationButtons(SettingScreenGraphNode.route, "Setting", R.drawable.ic_setting)
}
