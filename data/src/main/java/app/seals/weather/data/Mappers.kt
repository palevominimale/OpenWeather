package app.seals.weather.data

import app.seals.weather.data.models.*
import app.seals.weather.domain.models.*

fun WeatherResponseDataModel.mapToDomain() : WeatherResponseDomainModel {
    return WeatherResponseDomainModel(
        location = this.location?.mapToDomain(),
        current = this.current?.mapToDomain(),
        forecast = this.forecast?.mapToDomain(),
        alerts = this.alerts?.mapToDomain()
    )
}

fun List<AutocompleteDataModel>.mapToDomain() : List<AutocompleteDomainModel> =
    mutableListOf<AutocompleteDomainModel>().apply {
        this@mapToDomain.forEach { add(it.mapToDomain()) }
}

private fun LocationDataModel.mapToDomain() =
    LocationDomainModel(name, region, country, lat, lon, tzId, localtimeEpoch, localtime)

private fun CurrentWeatherDataModel.mapToDomain() =
    CurrentWeatherDomainModel(
        lastUpdatedEpoch, lastUpdated, tempC, isDay,
        condition?.mapToDomain(),
        windKph, windDegree, windDir, pressureMb, precipMm, humidity, cloud, feelslikeC, visKm, uv, gustKph,
        airQuality?.mapToDomain()
    )

private fun ConditionDataModel.mapToDomain() = ConditionDomainModel(text, code)
private fun AqiDataModel.mapToDomain() = AqiDomainModel(co, no2, o3, so2, pm25, pm10, usEpaIndex, gbDefraIndex)
private fun ForecastDataModel.mapToDomain() = ForecastDomainModel(forecastday.mapToDomain())
private fun DayDataModel.mapToDomain() = DayDomainModel(maxtempC, mintempC, avgtempC, maxwindKph, totalprecipMm, totalsnowCm, avgvisKm, avghumidity, dailyWillItRain, dailyChanceOfRain, dailyWillItSnow, dailyChanceOfSnow, condition?.mapToDomain(), uv)
private fun ForecastDayDataModel.mapToDomain() = ForecastDayDomainModel(date, dateEpoch, day?.mapToDomain(), astro?.mapToDomain(), hour.mapToDomain())
private fun AstroDataModel.mapToDomain() = AstroDomainModel(sunrise, sunset)
private fun ArrayList<HourDataModel>.mapToDomain() = mutableListOf<HourDomainModel>().apply {
    this@mapToDomain.forEach { add(it.mapToDomain()) }
} as ArrayList
private fun HourDataModel.mapToDomain() = HourDomainModel(timeEpoch, time, tempC, isDay, condition?.mapToDomain(), windKph, windDegree, windDir, pressureMb, precipMm, humidity, cloud, feelslikeC, windchillC, heatindexC, dewpointC, willItRain, chanceOfRain, willItSnow, chanceOfSnow, visKm, gustKph, uv)
@JvmName("mapToDomainForecastDayDataModel")
private fun ArrayList<ForecastDayDataModel>.mapToDomain() = mutableListOf<ForecastDayDomainModel>().apply {
    this@mapToDomain.forEach { add(it.mapToDomain()) }
} as ArrayList
private fun AlertDataModel.mapToDomain() = AlertDomainModel(headline, msgtype, severity, urgency, areas, category, certainty, event, note, effective, expires, desc, instruction)
private fun AlertsDataModel.mapToDomain() : AlertsDomainModel {
    val alerts = mutableListOf<AlertDomainModel>().apply {
        this@mapToDomain.alert.forEach {
            add(it.mapToDomain())
        }
    }
    return AlertsDomainModel((alerts as ArrayList<AlertDomainModel>))
}
fun AutocompleteDataModel.mapToDomain() = AutocompleteDomainModel(id, name, region, country, lat, lon, url)
fun AutocompleteDomainModel.mapToData() = AutocompleteDataModel(id, name, region, country, lat, lon, url)