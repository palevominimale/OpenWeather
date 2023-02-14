package app.seals.weather.domain

import app.seals.weather.domain.models.AutocompleteDomainModel
import app.seals.weather.domain.models.WeatherResponseDomainModel

interface LocalRepo {
    fun clearForecasts()
    fun loadForecast(id: String) : WeatherResponseDomainModel?
    fun loadLocations() : List<AutocompleteDomainModel>
    fun addLocation(location: AutocompleteDomainModel)
    fun deleteLocation(id: Int)
}