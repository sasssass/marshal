package com.sass.marshal.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sass.marshal.ui.navigation.BottomNavigationBar
import com.sass.marshal.ui.screen.cryptoDetail.CryptoDetailScreen
import com.sass.marshal.ui.screen.cryptoDetail.CryptoDetailScreenGraphNode
import com.sass.marshal.ui.screen.listOfCrypto.ListOfCryptoScreen
import com.sass.marshal.ui.screen.listOfCrypto.ListOfCryptoScreenGraphNode
import com.sass.marshal.ui.screen.setting.SettingScreen
import com.sass.marshal.ui.screen.setting.SettingScreenGraphNode

@Composable
fun MainCompose(start: String = ListOfCryptoScreenGraphNode.route) {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) { innerPadding ->

        NavHost(
            navController,
            startDestination = start,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(ListOfCryptoScreenGraphNode.route) {
                ListOfCryptoScreen(navController, hiltViewModel())
            }

            composable(SettingScreenGraphNode.route) {
                SettingScreen(hiltViewModel())
            }

            composable(CryptoDetailScreenGraphNode.route) {
                CryptoDetailScreen(navController, hiltViewModel())
            }
        }
    }
}
