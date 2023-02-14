package app.seals.weather.domain.models

data class ForecastDomainModel(
    var forecastday : ArrayList<ForecastDayDomainModel> = arrayListOf()
)