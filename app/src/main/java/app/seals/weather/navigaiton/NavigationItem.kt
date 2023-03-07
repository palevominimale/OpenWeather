package app.seals.weather.navigaiton

sealed class NavigationItem(
    val route: String
) {
    object Main : NavigationItem("main")
    object ManageCities : NavigationItem("manage")
    object Settings : NavigationItem("settings")
}