package app.seals.weather.domain.models

data class WeatherResponseDomainModel(
    val location : LocationDomainModel?          = LocationDomainModel(),
    val current  : CurrentWeatherDomainModel?    = CurrentWeatherDomainModel(),
    val forecast : ForecastDomainModel?          = ForecastDomainModel(),
    val alerts   : AlertsDomainModel?            = AlertsDomainModel(),
)
