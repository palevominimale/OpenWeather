package app.seals.weather.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.seals.weather.UiIntent
import app.seals.weather.UiState
import app.seals.weather.domain.LocalRepo
import app.seals.weather.domain.WeatherApi
import app.seals.weather.domain.models.ApiResult
import app.seals.weather.domain.models.PrefsProvider
import app.seals.weather.domain.models.WeatherResponseDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherApi: WeatherApi,
    private val localRepo: LocalRepo,
    private val prefs: PrefsProvider
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)
    private var lastWeather = WeatherResponseDomainModel()
    private val locations = localRepo.loadLocations() as MutableList
    private val _uiState = MutableStateFlow<UiState>(UiState.Ready(
        weather = lastWeather,
        locations = locations
    ))
    val uiState : StateFlow<UiState> get() = _uiState
    val a = viewModelScope.launch {
        prefs.getSelectedLocation()
    }

    init {
        viewModelScope.launch {
            lastWeather = localRepo.loadForecast(prefs.getSelectedLocation()) ?: WeatherResponseDomainModel()
            _uiState.emit((_uiState.value as UiState.Ready).copy(weather = lastWeather))
        }
        processApi()
        if(lastWeather.current == null) scope.launch { weatherApi.getWeather(prefs.getSelectedLocation()) }
    }

    private fun processApi() {
        viewModelScope.launch {
            weatherApi.result.collect {
                when(it) {
                    is ApiResult.Success.Weather -> {
                        if(it.data.forecast?.forecastday?.isEmpty() == false) _uiState.emit(UiState.Ready(
                            weather = it.data,
                            locations = localRepo.loadLocations()
                        ))
                    }
                    is ApiResult.Success.Autocomplete -> {
                        if(uiState.value is UiState.Ready) _uiState.emit((_uiState.value as UiState.Ready).copy(
                            autocomplete = it.data
                        ))
                    }
                    is ApiResult.Error -> _uiState.emit(UiState.Error(it.code ?: 0, it.message ?: "no msg"))
                    is ApiResult.Exception -> _uiState.emit(UiState.Error(0, it.e?.localizedMessage.toString()))
                }
            }
        }
    }

    fun process(intent: UiIntent) {
        when(intent) {
            is UiIntent.Refresh -> {
                scope.launch {
                    val l = prefs.getSelectedLocation()
                    _uiState.emit((uiState.value as UiState.Ready).copy(isRefreshing = true))
                    weatherApi.getWeather(l)
                }
            }
            is UiIntent.Autocomplete -> {
                scope.launch { weatherApi.getAutocomplete(intent.query) }
            }
            is UiIntent.GetForecast -> {
                viewModelScope.launch {
                    _uiState.emit((_uiState.value as UiState.Ready).copy(weather = localRepo.loadForecast(intent.id) ?: WeatherResponseDomainModel()))
                }
            }
            is UiIntent.Share -> { }
            is UiIntent.Search -> { }
            is UiIntent.SetLocation -> {
                viewModelScope.launch {
                    prefs.setSelectedLocation(intent.location)
                    _uiState.emit((_uiState.value as UiState.Ready).copy(weather = localRepo.loadForecast(intent.location) ?: WeatherResponseDomainModel()))
                }
            }
            is UiIntent.Delete.Location -> {
                localRepo.deleteLocation(intent.id)
                viewModelScope.launch {
                    _uiState.emit((_uiState.value as UiState.Ready).copy(locations = localRepo.loadLocations()))
                }
            }
            is UiIntent.Add.Location -> {
                localRepo.addLocation(intent.location)
                viewModelScope.launch {
                    _uiState.emit((_uiState.value as UiState.Ready).copy(locations = localRepo.loadLocations()))
                }
            }
        }
    }
}