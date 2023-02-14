package app.seals.weather.domain

import app.seals.weather.domain.models.ApiResult
import kotlinx.coroutines.flow.SharedFlow

interface WeatherApi {
    val result : SharedFlow<ApiResult>
    suspend fun getWeather(id: String)
    suspend fun getAutocomplete(query: String)
}