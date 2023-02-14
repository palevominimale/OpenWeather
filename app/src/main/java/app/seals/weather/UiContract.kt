package app.seals.weather

import app.seals.weather.domain.models.AutocompleteDomainModel
import app.seals.weather.domain.models.WeatherResponseDomainModel

sealed class UiState {
//    object Splash                                           : UiState()
//    object NoInternet                                       : UiState()
    data class Error(val code: Int, val message: String)    : UiState()
    data class Ready(
        val weather: WeatherResponseDomainModel,
        val locations: List<AutocompleteDomainModel> = emptyList(),
        val autocomplete: List<AutocompleteDomainModel> = emptyList(),
        val isRefreshing: Boolean = false
    ) : UiState() {
        fun copy(
            autocomplete: List<AutocompleteDomainModel> = this.autocomplete,
            locations: List<AutocompleteDomainModel> = this.locations,
            isRefreshing: Boolean = this.isRefreshing) : UiState = copy(
                weather = weather,
                isRefreshing = isRefreshing,
                autocomplete = autocomplete,
                locations = locations
            )
    }
}

sealed interface UiIntent {
    object Refresh                                                          : UiIntent
    object Share                                                            : UiIntent
    data class Search           (val query: String)                         : UiIntent
    data class Autocomplete     (val query: String)                         : UiIntent
    data class SetLocation      (val location: String)                      : UiIntent
    data class GetForecast      (val id: String)                            : UiIntent
    sealed class Delete {
        data class Location(val id: Int)                              : UiIntent
    }
    sealed class Add {
        data class Location(val location: AutocompleteDomainModel)    : UiIntent
    }
}