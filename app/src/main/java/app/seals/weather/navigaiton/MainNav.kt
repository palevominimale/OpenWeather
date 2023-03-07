package app.seals.weather.navigaiton

import androidx.compose.foundation.layout.Column
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.seals.weather.main.MainViewModel
import app.seals.weather.ui.screens.LocationDialog
import app.seals.weather.ui.screens.MainScreen
import app.seals.weather.ui.screens.TopBar

@Composable
fun MainNav(
    modifier: Modifier = Modifier,
    vm: MainViewModel,
    navHostController: NavHostController
) {

    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = NavigationItem.Main.route
    ) {
        composable(NavigationItem.Main.route) {
            Column {
                TopBar()
                MainScreen()
            }
        }
        composable(NavigationItem.ManageCities.route) {
            LocationDialog()
        }
        composable(NavigationItem.Settings.route) {

        }
    }

}